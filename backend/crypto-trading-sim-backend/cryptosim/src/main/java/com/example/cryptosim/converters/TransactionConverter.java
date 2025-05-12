package com.example.cryptosim.converters;

import utills.model.Transaction;
import com.example.cryptosim.entity.TransactionEntity;


public class TransactionConverter implements ITransactionConverter {
    private IUUIDConverter uuidConverter;

    public TransactionConverter(IUUIDConverter uuidConverter) {
        this.uuidConverter = uuidConverter;
    }

    public Transaction convertToUser(TransactionEntity entity) {

        return new Transaction(
                entity.getId().toString(),
              entity.getType(),
              entity.getCryptoSymbol(),
              entity.getQuantity(),
              entity.getUnitPrice(),
              entity.getCreatedAt(),
                entity.getAccountId().toString()
        );
    }

    public TransactionEntity convertToEntity(Transaction user) {
        return new TransactionEntity(
                uuidConverter.convertFromString(user.getId()),
                user.getType(),
                user.getCrypto(),
                user.getQuantity(),
                user.getUnitPrice(),
                user.getTimestamp(),
                uuidConverter.convertFromString(user.getAccountId())
        );
    }

}
