package com.demo.controller;

import com.demo.kmd.models.FinStatistics;
import com.demo.kmd.models.TransactionInfo;
import com.demo.kmd.repository.TransactionJpaRepository;
import com.demo.kmd.scheduler.DBQueryService;
import com.demo.kmd.scheduler.FinancialTransactionService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class MvcController {

	@Autowired
	TransactionJpaRepository transactionJpaRepository;
	
	@Autowired
	DBQueryService dbQueryService;

	/*
	 * for(int day=1; day < 31 ; day++) {
	 * 
	 * daillyCostMap
	 * 
	 * temp = rand.nextInt(100); total+=temp; daillyCostMap.put(String.valueOf(day)
	 * , temp*3); progressiveTotal.put(String.valueOf(day) , total); }
	 * 
	 */

	@GetMapping("/displayBarGraph2")
	public String spendGraph(Model model) throws Exception {
		Map<String, Float> dailyCostMap = new LinkedHashMap<>();

		Map<String, Float> progressiveTotal = new LinkedHashMap<>();

		List<FinStatistics> allTrans = transactionJpaRepository.getDailyExpenses();

		Random rand = new Random();
		int temp = 0;
		int total = 0;

		String pattern = "yyyy-mm-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		for (int day = 1; day < 31; day++) {
			dailyCostMap.put(String.valueOf(day), 0.0f);
		}

		float pTotal = 0.0f;
		for (FinStatistics datas : allTrans) {

			double amount = datas.getAmount();

			if (amount < 0.0)
				continue;
			String dateStr = datas.getDate();

			Date dateOfTransaction = simpleDateFormat.parse(dateStr);
			int dateOfMonth = dateOfTransaction.getDate();

			float currentAmount = (float) dailyCostMap.getOrDefault(dateOfMonth, 0.0f);
			float addedAmount = (float) (currentAmount + amount);
			dailyCostMap.put(String.valueOf(dateOfMonth), addedAmount);
			pTotal += amount;

		}

		float progTotal = 0.0f;
		for (int i = 1; i <= 31; i++) {

			float c = dailyCostMap.getOrDefault(String.valueOf(i), 0.0f);
			progTotal += c;
			progressiveTotal.put(String.valueOf(i), progTotal);

		}

		System.out.println(dailyCostMap);
		System.out.println(progressiveTotal);

		model.addAttribute("surveyMap", dailyCostMap);
		model.addAttribute("progressiveTotal", progressiveTotal);

		return "barGraph";

	}

	@GetMapping("/displayBarGraph")
	public String barGraph(Model model) {
		Map<String, Integer> surveyMap = new LinkedHashMap<>();

		Map<String, Integer> progressiveTotal = new LinkedHashMap<>();

		/*
		 * surveyMap.put("Java", 40); surveyMap.put("Dev oops", 25);
		 * surveyMap.put("Python", 20); surveyMap.put(".Net", 15);
		 * model.addAttribute("surveyMap", surveyMap);
		 */

		Random rand = new Random();
		int temp = 0;
		int total = 0;
		for (int day = 1; day < 31; day++) {

			temp = rand.nextInt(100);
			total += temp;
			surveyMap.put(String.valueOf(day), temp * 3);
			progressiveTotal.put(String.valueOf(day), total);
		}

		model.addAttribute("surveyMap", surveyMap);
		model.addAttribute("progressiveTotal", progressiveTotal);

		return "barGraph";

	}

	@GetMapping("/testAjax")
	public String ajaxTest1(Model model) throws Exception {

		return "ajaxCall";

	}
	
	@GetMapping("/daillyCost")
	public String getTransactions1(Model model) throws Exception {

		List<FinStatistics> allTrans = transactionJpaRepository.getDailyExpenses();

		model.addAttribute("transactions", allTrans);

		return "daillyCost";
	}
	
	@GetMapping("/transactions")
	public String getTransactions(Model model) throws Exception {
	
				
		Iterable<TransactionInfo> findAll = transactionJpaRepository.findAll();
		model.addAttribute("transactions", findAll);

		return "transactions";
	}
	
	
	
	@GetMapping("/transactionsCache")
	public String getTransactionsCache(Model model) throws Exception {
	
				
		Iterable<TransactionInfo> findAll = dbQueryService.findAll();
		model.addAttribute("transactions", findAll);

		return "transactions";
	}
	
	
	
	
	
	
	
	

}
