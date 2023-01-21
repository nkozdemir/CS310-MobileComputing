package com.sabanciuniv.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class AccountSummary {
	
	private String id;
	private String owner;
	private LocalDateTime createDate;
	
	@DBRef
	private List<Transaction> transactionsOut;
	
	@DBRef
	private List<Transaction> transactionsIn;
	
	private double balance;
	
	public AccountSummary() {
		// TODO Auto-generated constructor stub
	}

	public AccountSummary(Account account, List<Transaction> transactionsOut,
			List<Transaction> transactionsIn, double balance) {
		super();
		this.id = account.getId();
		this.owner = account.getOwner();
		this.createDate = account.getCreateDate();
		this.transactionsOut = transactionsOut;
		this.transactionsIn = transactionsIn;
		this.balance = balance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public List<Transaction> getTransactionsOut() {
		return transactionsOut;
	}

	public void setTransactionsOut(List<Transaction> transactionsOut) {
		this.transactionsOut = transactionsOut;
	}

	public List<Transaction> getTransactionsIn() {
		return transactionsIn;
	}

	public void setTransactionsIn(List<Transaction> transactionsIn) {
		this.transactionsIn = transactionsIn;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}	

}
