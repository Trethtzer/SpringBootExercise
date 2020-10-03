package com.silesbonilla.springbootexercise.controller;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping("accounts/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable(value = "id") Long accountId){
		
		Optional<Account> account = accountRepository.findById(accountId);
		
		if (account.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok().body(account.get());
		}        
    }
	
	@PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@Validated @RequestBody Account account) {   
		
		// To avoid an account to be created with treasury = false and negative balance.
		if (!account.isTreasury()) {
			if (account.getMoney() < 0) {
				return ResponseEntity.badRequest().build();
			}
		}
		
		accountRepository.save(account);
		return ResponseEntity.ok().body(account);
    }
	
	@PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable(value = "id") Long accountId,
        @Validated @RequestBody Account accountDetails){
		
        Optional<Account> account = accountRepository.findById(accountId);            

        ResponseEntity<Account> re;
        
        if (account.isEmpty()) {
			re = ResponseEntity.notFound().build();
		}else {		
			// If treasury is false and trying to set a negative balance.
			if ((accountDetails.getMoney() < 0) && !account.get().isTreasury()) {
				re = ResponseEntity.badRequest().build();
			}else {
				account.get().setCurrency(accountDetails.getCurrency());			
				account.get().setMoney(accountDetails.getMoney());		
				account.get().setName(accountDetails.getName());
				
		        final Account updatedAccount = accountRepository.save(account.get());
				re = ResponseEntity.ok().body(updatedAccount);
			}				
		}
        
        return re;
    }
	
	@DeleteMapping("/accounts/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable(value = "id") Long accountId){
        Optional<Account> account = accountRepository.findById(accountId);
        
        if (account.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {	
			accountRepository.delete(account.get());	        	        
	        return ResponseEntity.ok().body("Account deleted successfully");
		}        
    }
	
	@PostMapping("/transfer/")
	public ResponseEntity<String> transfer(@RequestBody String jsonString){
		JSONObject jsonObj = new JSONObject(jsonString);
		float moneyToBeTransfered = jsonObj.getFloat("money");
		long idAccountToGiveMoney = jsonObj.getLong("accountToGiveMoney");
		long idAccountToReceiveMoney = jsonObj.getLong("accountToReceiveMoney");
		
		Optional<Account> accountToGiveMoney = accountRepository.findById(idAccountToGiveMoney);
		Optional<Account> accountToReceiveMoney = accountRepository.findById(idAccountToReceiveMoney);
		
		ResponseEntity<String> re;
		
		// If any of the accounts doesn't exist.
		if(accountToGiveMoney.isEmpty() || accountToReceiveMoney.isEmpty()) {
			re = ResponseEntity.notFound().build();
		}else {
			// If account which gives money isn't treasury and doesn't have enough money we cancel the transfer.
			if( !accountToGiveMoney.get().isTreasury() && ((accountToGiveMoney.get().getMoney() - moneyToBeTransfered) < 0) ) {
				re = ResponseEntity.badRequest().body("The account doesn't have enough money to make the transaction.");
			}else {
				accountToGiveMoney.get().setMoney(accountToGiveMoney.get().getMoney() - moneyToBeTransfered);
				accountToReceiveMoney.get().setMoney(accountToReceiveMoney.get().getMoney() + moneyToBeTransfered);
				
				accountRepository.save(accountToGiveMoney.get());
				accountRepository.save(accountToReceiveMoney.get());
				
				re = ResponseEntity.ok("Money transfered successfully");
			}
		}			
		
		return re;
	}
}
