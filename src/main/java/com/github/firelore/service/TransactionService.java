package com.github.firelore.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.github.firelore.domain.Transaction;
import com.github.firelore.repository.TransactionRepository;

@Service
public class TransactionService {
	private TransactionRepository transactionRepository;
	
	@Inject
	public TransactionService(TransactionRepository repository) {
		this.transactionRepository = repository;
	}
	
	public Transaction findOne(int id) {
		return transactionRepository.findOne(id);
	}

	public Transaction save(Transaction transaction) {
		return transactionRepository.saveAndFlush(transaction);
	}
}
