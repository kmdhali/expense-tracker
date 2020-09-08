package com.demo.kmd.repository;


import com.demo.kmd.models.TransactionInfo;
import com.demo.kmd.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface  UserRepository extends CrudRepository<User, Long>{
}
