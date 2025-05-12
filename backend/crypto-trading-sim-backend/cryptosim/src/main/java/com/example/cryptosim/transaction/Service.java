package com.example.cryptosim.transaction;

import utills.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Service {
    Transaction createTransaction(Transaction transaction);
    Transaction getTransactionById(String id);
    List<Transaction> getAllTransactions();
    boolean deleteAllTransactions();
}
