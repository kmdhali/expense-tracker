package com.demo.kmd.scheduler;

import java.util.Calendar;
import java.util.Date;

import com.demo.kmd.service.TransactionInfoCollectionEnd2EndService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component 
public class TransactionInfoDownloadScheduler {
	
	   @Autowired
	   TransactionInfoCollectionEnd2EndService transactionInfoCollectionEnd2EndService;
	   int runNo=0; 
	
	  @Scheduled(cron="30 30 5,10,12,13,16,22  * * ?")
	  public void initiateDownload() throws Exception {
		  
		    runNo++;
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DATE, -30);
	        Date startDate = cal.getTime();
	        Date endDate = new Date();
	        
	        
	    System.out.println("Running scheduler tasks ..."+new Date()+" runNo : "+runNo); 
	    System.out.println("========================================================"); 
	    
	    transactionInfoCollectionEnd2EndService.collectTransaction33("KironHardcoaded", startDate, endDate, "cardCompany", "account1", "product");
	    
	    
	    System.out.println("Scheduler Task ended successfully ..."+new Date()+" runNo : "+runNo); 
	    System.out.println("=========================================================="); 
	    
	  }
	  
}
