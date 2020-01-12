package com.demo.controller;

import com.demo.kmd.models.FinStatistics;
import com.demo.kmd.models.PlaidAuthData;
import com.demo.kmd.models.TransactionInfo;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import retrofit2.Response;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.kmd.repository.TransactionJpaRepository;
import com.demo.kmd.scheduler.FinancialTransactionService;
import com.demo.kmd.scheduler.PersistanceService;
import com.demo.kmd.scheduler.TransactionInfoCollectionEnd2EndService;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class TestController {

	@Autowired
	PersistanceService persistanceService;

	@Autowired
	FinancialTransactionService financialTransactionService;

	@Autowired
	TransactionInfoCollectionEnd2EndService transactionInfoCollectionEnd2EndService;

	@Autowired
	TransactionJpaRepository transactionJpaRepository;

	@RequestMapping(value = "/testCustomeQuery1", method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getCustomQuery1() throws Exception {
		System.out.print("test  hit");
		List<FinStatistics> list = transactionJpaRepository.getDailyExpenses();
		return ResponseEntity.ok(list);

	}

	@RequestMapping(value = "/test1", method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getTransactions3(@RequestBody TransactionInfo transactionInfo)
			throws Exception {
		System.out.print("test  hit");
		transactionInfo = persistanceService.insert(transactionInfo);
		return ResponseEntity.ok(transactionInfo);

	}

	@RequestMapping(value = "/testPlaidAuthData", method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getTransactions3(@RequestBody PlaidAuthData plaidAuthData) throws Exception {
		System.out.print("test  hit");
		plaidAuthData = persistanceService.insert(plaidAuthData);
		return ResponseEntity.ok(plaidAuthData);

	}

	@RequestMapping(value = "/transactions44", method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getTransactions2() throws Exception {

		try {

			System.out.print("getTransactions2 hit");
			TransactionsGetResponse trans = financialTransactionService.getTransactions();

			List<Transaction> transactions = trans.getTransactions();

			for (Transaction transaction : transactions) {
				System.out.println(transaction.getTransactionId() + " : " + transaction.getAmount() + " , "
						+ transaction.getName() + " , " + transaction.getOriginalDescription());

			}

			return ResponseEntity.ok(trans);
		} catch (Exception exp) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errorrrrrr");
		}
	}

	@RequestMapping(value = "/testDataCollection1", method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity getTransactions44() throws Exception {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		Date startDate = cal.getTime();
		Date endDate = new Date();

		try {

			System.out.print("testDataCollection1 hit");

			transactionInfoCollectionEnd2EndService.collectTransaction33("KironHardcoaded", startDate, endDate,
					"cardCompany", "account1", "product");
			return ResponseEntity.ok("Ok");

		} catch (Exception exp) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errorrrrrr");
		}
	}

}
