package com.example.cryptosim.create;

import com.example.cryptosim.entity.HoldingEntity;
import com.example.cryptosim.entity.TransactionEntity;

public interface Create {
    HoldingEntity createHolding(TransactionEntity transactionEntity);
}
