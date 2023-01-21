package com.sabanciuniv.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sabanciuniv.model.Account;

public interface AccountRepository extends MongoRepository<Account, String> {
	public Optional<Account> findById(String id);
}
