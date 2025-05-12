package com.example.cryptosim.create;

import com.example.cryptosim.entity.HoldingEntity;
import com.example.cryptosim.entity.TransactionEntity;
import com.example.cryptosim.generators.IUUIDGenerator;
import org.springframework.stereotype.Component;

@Component
public class HoldingFromTransaction implements Create {
    private final IUUIDGenerator uuidGenerator;

    public HoldingFromTransaction(IUUIDGenerator uuidGenerator) {
        this.uuidGenerator = uuidGenerator;
    }
    @Override
    public HoldingEntity createHolding(TransactionEntity transactionEntity) {
        return new HoldingEntity(
                uuidGenerator.generate(),
                transactionEntity.getCryptoSymbol(),
                transactionEntity.getQuantity(),
                transactionEntity.getUnitPrice() * transactionEntity.getQuantity(),
                transactionEntity.getAccountId()
        );
    }
}
