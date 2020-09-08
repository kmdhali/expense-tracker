package com.demo.kmd.repository;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.demo.kmd.models.FinStatistics;
import com.demo.kmd.models.TransactionInfo;

@Repository
public interface TransactionJpaRepository extends  CrudRepository<TransactionInfo, Long>{	
		
//	
//	@Query(value = "select count(v) as cnt, v.answer from Survey v group by v.answer")
	
//	@Query( value = "select sum(amount), date from devschema.transaction_info where  date > '2019-12-07' group by date")
//	public List<?> findData();
	
	
    @Query("SELECT new com.demo.kmd.models.FinStatistics(t.date, SUM(t.amount)) FROM TransactionInfo t where  t.date >= '2019-12-01' group by t.date")

    List<FinStatistics> getDailyExpenses();

    @Query("SELECT new com.demo.kmd.models.FinStatistics(t.date, SUM(t.amount)) FROM TransactionInfo t where  t.date >= '2019-12-01' group by t.date")

    List<FinStatistics> getDailyExpensesForMonth();


}