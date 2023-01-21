package com.sabanciuniv.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sabanciuniv.model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
	List<Transaction> findByFrom(String accountId);
	List<Transaction> findByTo(String accountId);
}
