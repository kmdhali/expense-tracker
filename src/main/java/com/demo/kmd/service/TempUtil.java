package com.demo.kmd.service;

import com.demo.kmd.models.TransactionInfo;

public class TempUtil {

	static public String getPublicToken(String stUserId, String cardCompany) {
		
		return "XXXXX";
	}

	static public com.demo.kmd.models.TransactionInfo copy(com.plaid.client.response.TransactionsGetResponse.Transaction transaction) {
		com.demo.kmd.models.TransactionInfo  transactionInfo=new TransactionInfo();
		
		transactionInfo.setAccountId(transaction.getAccountId());
		transactionInfo.setAccountOwner(transaction.getAccountOwner());
		transactionInfo.setAmount(transaction.getAmount());
		transactionInfo.setDate(transaction.getDate());
		
		transactionInfo.setName(transaction.getName());
		transactionInfo.setOriginalDescription(transaction.getOriginalDescription());
		transactionInfo.setPending(transaction.getPending());
		transactionInfo.setPendingTransactionId(transaction.getPendingTransactionId());
		transactionInfo.setTransactionId(transaction.getTransactionId());
		transactionInfo.setTransactionType(transaction.getTransactionType());
		//@todo more to mapp here  , donnt miss the fileds 
		
		return transactionInfo;
	}
}
