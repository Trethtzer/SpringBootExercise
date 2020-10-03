package com.silesbonilla.springbootexercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.silesbonilla.springbootexercise.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

}
