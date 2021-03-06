package com.demo.controller;

import com.demo.PlaidAuthService;
import com.demo.kmd.models.PlaidAuthData;
import com.demo.kmd.service.FinancialTransactionService;
import com.demo.kmd.service.PersistanceService;
import com.plaid.client.PlaidClient;
import com.plaid.client.request.AuthGetRequest;
import com.plaid.client.request.InstitutionsGetByIdRequest;
import com.plaid.client.request.ItemGetRequest;
import com.plaid.client.request.ItemPublicTokenExchangeRequest;
import com.plaid.client.request.TransactionsGetRequest;
import com.plaid.client.response.AuthGetResponse;
import com.plaid.client.response.ErrorResponse;
import com.plaid.client.response.InstitutionsGetByIdResponse;
import com.plaid.client.response.ItemGetResponse;
import com.plaid.client.response.ItemPublicTokenExchangeResponse;
import com.plaid.client.response.ItemStatus;
import com.plaid.client.response.TransactionsGetResponse;
import com.plaid.client.response.TransactionsGetResponse.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import retrofit2.Response;

import javax.swing.filechooser.FileFilter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@Controller
public class HomeController {
    FileFilter

    private final Environment env;
    private final PlaidClient plaidClient;
    private final PlaidAuthService authService;

    @Autowired
    FinancialTransactionService financialTransactionService;
    
    @Autowired 
    PersistanceService persistanceService;

    @Autowired
    public HomeController(Environment env, PlaidClient plaidClient, PlaidAuthService authService) {
        this.env = env;
        this.plaidClient = plaidClient;
        this.authService = authService;
    }


    /**
     * Home page.
     */
    @RequestMapping(value="/", method=GET)
    public String index(Model model) {
        model.addAttribute("PLAID_PUBLIC_KEY", env.getProperty("PLAID_PUBLIC_KEY"));
        model.addAttribute("PLAID_ENV", env.getProperty("PLAID_ENV"));
        return "index";
    }

    
//    @RequestMapping(value="/home1", method=GET)
//    public String home(Model model) {
//        model.addAttribute("PLAID_PUBLIC_KEY", env.getProperty("PLAID_PUBLIC_KEY"));
//        model.addAttribute("PLAID_ENV", env.getProperty("PLAID_ENV"));
//        return "home1";
//    }

    
    
    
    /**
     * Exchange link public token for access token.
     */
    @RequestMapping(value="/get_access_token", method=POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ResponseEntity getAccessToken(@RequestParam("public_token") String publicToken) throws Exception {
        
    	System.out.println("****   /get_access_token called from the dropin model of plaid.**** ");
    	System.out.println("/Exchanging the public-token for access-token..");
    	
    	Response<ItemPublicTokenExchangeResponse> response = this.plaidClient.service()
                .itemPublicTokenExchange(new ItemPublicTokenExchangeRequest(publicToken))
                .execute();

        if (response.isSuccessful()) {
        	System.out.println("Successfully exhanged the token with plaid service  ..");
        	
            this.authService.setAccessToken(response.body().getAccessToken());
            this.authService.setItemId(response.body().getItemId());

            Map<String, Object> data = new HashMap<>();
            data.put("error", false);
            
            //kmd
            PlaidAuthData plaidAuthData=new PlaidAuthData("KironHardcoaded", response.body().getItemId(), response.body().getAccessToken(), publicToken);
            
            System.out.println("Saving the accessToken on the datastore ..");
            persistanceService.insert(plaidAuthData);

            return ResponseEntity.ok(data);
            
            
        } else {
            return ResponseEntity.status(500).body(getErrorResponseData(response.errorBody().string()));
        }
    }

    /**
     * Retrieve high-level account information and account and routing numbers
     * for each account associated with the Item.
     */
    
    @RequestMapping(value="/accounts", method=GET, produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getAccount() throws Exception {
        if (authService.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(getErrorResponseData("Not authorized"));
        }

        Response<AuthGetResponse> response = this.plaidClient.service()
                .authGet(new AuthGetRequest(this.authService.getAccessToken())).execute();

        if (response.isSuccessful()) {
            Map<String, Object> data = new HashMap<>();
            data.put("error", false);
            data.put("accounts", response.body().getAccounts());
            data.put("numbers", response.body().getNumbers());

            return ResponseEntity.ok(data);
        } else {
            Map<String, Object> data = new HashMap<>();
            data.put("error", "Unable to pull accounts from the Plaid API.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
        }
    }

    /**
     * Pull the Item - this includes information about available products,
     * billed products, webhook information, and more.
     */
    @RequestMapping(value="/item", method=POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getItem() throws Exception {
        if (authService.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(getErrorResponseData("Not authorized"));
        }

        Response<ItemGetResponse> itemResponse = this.plaidClient.service()
                .itemGet(new ItemGetRequest(this.authService.getAccessToken()))
                .execute();

        if (!itemResponse.isSuccessful()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(getErrorResponseData("Unable to pull item information from the Plaid API."));
        } else {
            ItemStatus item = itemResponse.body().getItem();

            Response<InstitutionsGetByIdResponse> institutionsResponse = this.plaidClient.service()
                    .institutionsGetById(new InstitutionsGetByIdRequest(item.getInstitutionId()))
                    .execute();

            if (!institutionsResponse.isSuccessful()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(getErrorResponseData("Unable to pull institution information from the Plaid API."));
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("error", false);
                data.put("item", item);
                data.put("institution", institutionsResponse.body().getInstitution());
                return ResponseEntity.ok(data);
            }
        }
    }

    /**
     * Pull transactions for the Item for the last 30 days.
     */
    @RequestMapping(value="/transactions", method=POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getTransactions() throws Exception {
        if (authService.getAccessToken() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(getErrorResponseData("Not authorized"));
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);
        Date startDate = cal.getTime();
        Date endDate = new Date();

        Response<TransactionsGetResponse> response = this.plaidClient.service()
                .transactionsGet(new TransactionsGetRequest(this.authService.getAccessToken(), startDate, endDate)
                        .withCount(250)
                        .withOffset(0))
                .execute();
        if (response.isSuccessful()) {
            return ResponseEntity.ok(response.body());
        } else {

            ErrorResponse error = this.plaidClient.parseError(response);
            Map<String, Object> data = new HashMap<>();
            data.put("error", error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
        }
    }
    
    @RequestMapping(value="/transactions2", method=POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getTransactions2() throws Exception {
    	
    	try {
//        if (authService.getAccessToken() == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(getErrorResponseData("Not authorized"));
//        }
        
        System.out.print("getTransactions2 hit");
        TransactionsGetResponse trans= financialTransactionService.getTransactions();

        
        List<Transaction> transactions = trans.getTransactions();
        
		for (Transaction  transaction : transactions) {
			System.out.println(transaction.getTransactionId()+" : "+transaction.getAmount()+" , "+transaction.getName()+" , "+transaction.getOriginalDescription());		
			
		}
        
        
/*        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);
        Date startDate = cal.getTime();
        Date endDate = new Date();

        Response<TransactionsGetResponse> response = this.plaidClient.service()
                .transactionsGet(new TransactionsGetRequest(this.authService.getAccessToken(), startDate, endDate)
                        .withCount(250)
                        .withOffset(0))
                .execute();
                */

            return ResponseEntity.ok(trans);            
    	}catch(Exception exp) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errorrrrrr");
        }
    }
    

    
    
    private Map<String, Object> getErrorResponseData(String message) {
        Map<String, Object> data = new HashMap<>();
        data.put("error", false);
        data.put("message", message);
        return data;
    }
}
