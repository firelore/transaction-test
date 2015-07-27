package com.github.firelore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.firelore.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
