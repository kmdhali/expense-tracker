package com.demo.kmd.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.PlaidAuthService;
import com.demo.kmd.models.PlaidAuthData;
import com.plaid.client.PlaidClient;
import com.plaid.client.request.TransactionsGetRequest;
import com.plaid.client.response.ErrorResponse;
import com.plaid.client.response.TransactionsGetResponse;

import retrofit2.Response;

@Service
public class FinancialTransactionService {
	
    private final Environment env;
    private final PlaidClient plaidClient;
    private final PlaidAuthService authService;
    
    
    @Autowired PersistanceService persistanceService;
    
    
    @Autowired 
    public FinancialTransactionService(Environment env, PlaidClient plaidClient, PlaidAuthService authService) {
        this.env = env;
        this.plaidClient = plaidClient;
        this.authService = authService;
    }
    
    
    public TransactionsGetResponse getTransactions1(String stUserid, String accessToekn, Date startDate, Date endDate) throws Exception { 
    	
    	System.out.print("Retreiving transaction from the remote Plaid Services..");
    	System.out.print("stUserid : " +stUserid+" , accessToekn : "+accessToekn);
    	
    	Response<TransactionsGetResponse> response = this.plaidClient.service()
          .transactionsGet(new TransactionsGetRequest(accessToekn, startDate, endDate)       
        		  .withCount(500)
                  .withOffset(0))
          .execute();
  
  
  if (response.isSuccessful()) {        	
	  
	  
  	return response.body();
     // return ResponseEntity.ok(response.body());
      
  } else {

      ErrorResponse error = this.plaidClient.parseError(response);
      Map<String, Object> data = new HashMap<>();
      data.put("error", error);
      //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
      throw new Exception(error.getDisplayMessage());
  }
    	
    }
    
    
    

    public TransactionsGetResponse getTransactions() throws Exception {
//        if (authService.getAccessToken() == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(getErrorResponseData("Not authorized"));
//        }

    	 
    	PlaidAuthData plaidAuthData = persistanceService.findAccessToken("KironHardcoaded", "test");
    	
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);
        Date startDate = cal.getTime();
        Date endDate = new Date();

//        Response<TransactionsGetResponse> response = this.plaidClient.service()
//                .transactionsGet(new TransactionsGetRequest(this.authService.getAccessToken(), startDate, endDate)
//                        .withCount(250)
//                        .withOffset(0))
//                .execute();
//        
        
        System.out.print("Access token retreivined frm db "+plaidAuthData.getPlaidAccessToken());
        System.out.print("Using this access token to interact with PLAId remote service ...");
        
        Response<TransactionsGetResponse> response = this.plaidClient.service()
                .transactionsGet(new TransactionsGetRequest(plaidAuthData.getPlaidAccessToken(), startDate, endDate)
                        .withCount(250)
                        .withOffset(0))
                .execute();
        
        
        if (response.isSuccessful()) {        	
        	return response.body();
           // return ResponseEntity.ok(response.body());
            
        } else {

            ErrorResponse error = this.plaidClient.parseError(response);
            Map<String, Object> data = new HashMap<>();
            data.put("error", error);
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
            throw new Exception(error.getDisplayMessage());
        }
    }
    
    private Map<String, Object> getErrorResponseData(String message) {
        Map<String, Object> data = new HashMap<>();
        data.put("error", false);
        data.put("message", message);
        return data;
    }
    
}
