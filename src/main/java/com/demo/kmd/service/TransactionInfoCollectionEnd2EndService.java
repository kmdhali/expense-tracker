package com.demo.kmd.service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.kmd.models.PlaidAuthData;
import com.demo.kmd.models.TransactionInfo;
import com.demo.kmd.repository.TransactionJpaRepository;
import com.plaid.client.response.TransactionsGetResponse;
import com.plaid.client.response.TransactionsGetResponse.Transaction;

@Service 
public class TransactionInfoCollectionEnd2EndService {

    @Autowired
    FinancialTransactionService financialTransactionService;
    
    @Autowired 
    PersistanceService persistanceService;
    
    @Autowired
    TransactionJpaRepository transactionJpaRepository;
    
	
	public void collectTransaction33(String stUserid, Date startDate, Date endDate, String cardCompany, String account, String product) throws Exception {
		
	
		PlaidAuthData plaidAuthData = persistanceService.findAccessToken(stUserid, "publicToken "); 		
	
		TransactionsGetResponse transactions1 = financialTransactionService.getTransactions1(stUserid, plaidAuthData.getPlaidAccessToken(), startDate, endDate);
		
		List<Transaction> transactionsList = transactions1.getTransactions();
		List<TransactionInfo> transactionInfoList = new ArrayList(100);		
		TransactionInfo t2;		
		
		
		System.out.println("DELETING ALL trasaction info first ....:(((((");
		System.out.println("**********************************************");
		transactionJpaRepository.deleteAll();
		
		
		for(Transaction t:transactionsList) {               //@todo : Convert this to batch 
			t2 = TempUtil.copy(t);
			transactionInfoList.add(t2);			
			persistanceService.insert(t2);
			System.out.println("Inserted transaction in data store ");			
			System.out.println(t2.getTransactionId()+" : "+t2.getAmount()+" , "+t2.getName()+" , "+t2.getOriginalDescription());			
		}		
		
	}
}
