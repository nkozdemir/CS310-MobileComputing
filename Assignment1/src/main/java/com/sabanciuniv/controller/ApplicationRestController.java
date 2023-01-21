package com.sabanciuniv.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sabanciuniv.model.Account;
import com.sabanciuniv.model.AccountSummary;
import com.sabanciuniv.model.ResponseModel;
import com.sabanciuniv.model.Transaction;
import com.sabanciuniv.model.TransactionPayload;
import com.sabanciuniv.repo.AccountRepository;
import com.sabanciuniv.repo.TransactionRepository;

import jakarta.annotation.PostConstruct;

@RestController
public class ApplicationRestController {
	
	@Autowired private AccountRepository accountRepository;
	@Autowired private TransactionRepository transactionRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestController.class);
	
	@PostConstruct
	public void init() {
		if (accountRepository.count() == 0 || transactionRepository.count() == 0) {
			LOGGER.info("Database is empty, initializing...");
			
			Account acc1 = new Account("1111", "Jack Johns");
			LOGGER.info(acc1.toString());
			
			Account acc2 = new Account("2222", "Henry Williams");
			LOGGER.info(acc2.toString());
			
			accountRepository.save(acc1);
			accountRepository.save(acc2);
			
			Transaction t1 = new Transaction(acc1, acc2, 1500);
			LOGGER.info(t1.toString());
			
			Transaction t2 = new Transaction(acc2, acc1, 2500);
			LOGGER.info(t2.toString());
			
			transactionRepository.save(t1);
			transactionRepository.save(t2);
			
			LOGGER.info("All data saved");
		}
	}
	
	@PostMapping("/account/save")
	public ResponseModel<Account> saveAccount(@RequestBody Account account) {
		ResponseModel<Account> responseModel = new ResponseModel<>();
		
		if ((account.getId() != null && account.getOwner() != null) && (!account.getId().isBlank() && !account.getOwner().isBlank())) {
			accountRepository.save(account);
			responseModel.setMessage("SUCCESS");
			responseModel.setData(account);	
		}
		else {
			responseModel.setMessage("ERROR: missing fields");
			responseModel.setData(null);
		}
		
		return responseModel;
	}
	
	@PostMapping("/transaction/save")
	public ResponseModel<Transaction> saveTransaction(@RequestBody TransactionPayload transactionPayload) {
		ResponseModel<Transaction> responseModel = new ResponseModel<>();
		
		if (transactionPayload.getFromAccountId() == null || transactionPayload.getToAccountId() == null || transactionPayload.getAmount() == null) {
			responseModel.setMessage("ERROR: missing fields");
			responseModel.setData(null);
		}
		else {
			Optional<Account> fromAccount = accountRepository.findById(transactionPayload.getFromAccountId());
			Optional<Account> toAccount = accountRepository.findById(transactionPayload.getToAccountId());
			
			if (fromAccount.isEmpty() || toAccount.isEmpty()) {	
				responseModel.setMessage("ERROR: account id");
				responseModel.setData(null);		
			}
			else {
				Transaction transactionToSave = new Transaction(fromAccount.get(), toAccount.get(), transactionPayload.getAmount().doubleValue());
				transactionRepository.save(transactionToSave);
				
				responseModel.setMessage("SUCCESS");
				responseModel.setData(transactionToSave);
			}	
		}
		
		return responseModel;
	}
	
	@GetMapping("/account/{accountId}")
	public ResponseModel<AccountSummary> accountSummary(@PathVariable String accountId) {
		ResponseModel<AccountSummary> responseModel = new ResponseModel<>();

		Optional<Account> account = accountRepository.findById(accountId);
		
		if (account.isEmpty()) {
			responseModel.setMessage("ERROR: account doesn't exist");
			responseModel.setData(null);
			
			return responseModel;
		}
		
		List<Transaction> fromTransactions = transactionRepository.findByFrom(accountId);
		List<Transaction> toTransactions = transactionRepository.findByTo(accountId);
		
		// Outgoing transactions
		double sumfrom = 0;
		for (Transaction t : fromTransactions) {
			sumfrom += t.getAmount();
		}
		
		// Incoming transactions
		double sumto = 0;
		for (Transaction t : toTransactions) {
			sumto += t.getAmount();
		}
		
		double balance = sumto - sumfrom;
		
		AccountSummary accountSummary = new AccountSummary(account.get(), fromTransactions, toTransactions, balance);
		responseModel.setMessage("SUCCESS");
		responseModel.setData(accountSummary);
		
		return responseModel;
	}
	
	@GetMapping("/transaction/to/{accountId}")
	public ResponseModel<List<Transaction>> incomingTransactions(@PathVariable String accountId) {
		ResponseModel<List<Transaction>> responseModel = new ResponseModel<>();
		List<Transaction> incomingTransactions = transactionRepository.findByTo(accountId);
		
		if (incomingTransactions.isEmpty()) {
			responseModel.setMessage("ERROR: account doesn't exist");
			responseModel.setData(null);
		}
		else {
			responseModel.setMessage("SUCCESS");
			responseModel.setData(incomingTransactions);
		}
		
		return responseModel;
	}
	
	@GetMapping("/transaction/from/{accountId}")
	public ResponseModel<List<Transaction>> outgoingTransactions(@PathVariable String accountId) {
		ResponseModel<List<Transaction>> responseModel = new ResponseModel<>();
		List<Transaction> outgoingTransactions = transactionRepository.findByFrom(accountId);

		if (outgoingTransactions.isEmpty()) {
			responseModel.setMessage("ERROR: account doesn't exist");
			responseModel.setData(null);
		}
		else {
			responseModel.setMessage("SUCCESS");
			responseModel.setData(outgoingTransactions);
		}
		
		return responseModel;
	}

}
