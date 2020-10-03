package com.silesbonilla.springbootexercise.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.silesbonilla.springbootexercise.model.Account;
import com.silesbonilla.springbootexercise.repository.AccountRepository;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;
	
	@GetMapping("accounts")
	public List<Account> getAllAccounts(){
		return accountRepository.findAll();
	}
	
	@GetMapping("account/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable(value = "id") Long accountId){
		
		Optional<Account> account = accountRepository.findById(accountId);
		
		if (account.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok().body(account.get());
		}        
    }
	
}