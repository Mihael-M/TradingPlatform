package com.example.cryptosim.converters;

import org.springframework.stereotype.Component;
import utills.model.Transaction;
import com.example.cryptosim.entity.TransactionEntity;

@Component
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
              entity.getProfitLoss(),
              entity.getCreatedAt(),
                entity.getAccountId().toString()
        );
    }

    public TransactionEntity convertToEntity(Transaction user) {
        try {
            return new TransactionEntity(
                    uuidConverter.convertFromString(user.getId()),
                    user.getType(),
                    user.getCrypto(),
                    user.getQuantity(),
                    user.getUnitPrice(),
                    user.getProfitLoss(),
                    user.getTimestamp(),
                    uuidConverter.convertFromString(user.getAccountId())
            );
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid UUID format for accountId: " + user.getAccountId());
        }
    }

}
