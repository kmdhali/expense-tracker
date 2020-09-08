package com.demo.kmd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.kmd.models.PlaidAuthData;
import com.demo.kmd.models.TransactionInfo;
import com.demo.kmd.repository.TransactionJpaRepository;
import com.demo.kmd.repository.PlaidAuthDataRepository;

@Service 
public class PersistanceService {
	
	@Autowired
	TransactionJpaRepository transactionJpaRepository;	
	
	@Autowired
	PlaidAuthDataRepository plaidAuthDataRepository;
	
	public TransactionInfo findAll(TransactionInfo transactionInfo) {
		
		return transactionJpaRepository.save(transactionInfo);
		
	}	
	
	
	
	public TransactionInfo insert(TransactionInfo transactionInfo) {
		
		return transactionJpaRepository.save(transactionInfo);
		
	}
	
	public PlaidAuthData insert(PlaidAuthData plaidAuthData) {
		
		return plaidAuthDataRepository.save(plaidAuthData);
	}
	
	public PlaidAuthData findAccessToken(String stUsedir, String itemId) {
		
		  System.out.println("Retreiving the access token from data store - for stuserd " + stUsedir);
		  PlaidAuthData  plaidAuthData = plaidAuthDataRepository.findByStUserid(stUsedir); 
		  
		  if (plaidAuthData == null ) 
			  System.out.println(" did not get any access token for user "+stUsedir+ " ");
		  else {
			  System.out.println(" Get the access token for user "+stUsedir+ " from data store ..");
			  System.out.println(" Access token is "+stUsedir+ plaidAuthData.getPlaidAccessToken());			  
		  }
		  
           return plaidAuthData;
	}
	 

}
