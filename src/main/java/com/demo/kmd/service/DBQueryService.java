package com.demo.kmd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.demo.kmd.models.FinStatistics;
import com.demo.kmd.models.TransactionInfo;
import com.demo.kmd.repository.TransactionJpaRepository;

@Service 
public class DBQueryService {

	@Autowired
	TransactionJpaRepository transactionJpaRepository;
	
	
	@Cacheable
	public List<FinStatistics> getDailyExpenses(){
		return transactionJpaRepository.getDailyExpenses();
	}
	
	
	@Cacheable("findAll")
	public Iterable<TransactionInfo> findAll(){
		System.out.print("Service layer findAll hit");
		return transactionJpaRepository.findAll();
	}
	

	
	
	
	
	
}
