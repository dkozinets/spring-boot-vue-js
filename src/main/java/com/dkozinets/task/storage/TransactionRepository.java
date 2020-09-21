package com.dkozinets.task.storage;

import com.dkozinets.task.storage.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
}
