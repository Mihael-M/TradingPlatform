package com.example.cryptosim.converters;

import utills.model.UserTransaction;
import com.example.cryptosim.entity.TransactionEntity;


public class TransactionConverter {
    public static UserTransaction convertToUser(TransactionEntity entity) {

        return new UserTransaction(
              entity.getType(),
              entity.getCryptoSymbol(),
              entity.getQuantity(),
              entity.getUnitPrice(),
              entity.getTimestamp()
        );
    }

    public static TransactionEntity convertToEntity(UserTransaction user) {
        return new TransactionEntity(
                user.getType(),
                user.getCrypto(),
                user.getQuantity(),
                user.getUnitPrice(),
                user.getTimestamp()
        );
    }

}
