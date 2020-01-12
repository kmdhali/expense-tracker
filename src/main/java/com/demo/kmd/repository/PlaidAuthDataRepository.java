package com.demo.kmd.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.demo.kmd.models.PlaidAuthData;

@Repository 
public interface  PlaidAuthDataRepository extends CrudRepository<PlaidAuthData, Long>{
	PlaidAuthData findByStUserid(String stSuerid);
}
