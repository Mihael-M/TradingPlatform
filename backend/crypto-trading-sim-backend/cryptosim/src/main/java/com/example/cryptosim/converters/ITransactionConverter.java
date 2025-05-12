package com.example.cryptosim.converters;

import com.example.cryptosim.entity.TransactionEntity;
import utills.model.Transaction;

public interface ITransactionConverter {
    Transaction convertToUser(TransactionEntity entity);
    TransactionEntity convertToEntity(Transaction user);
}
