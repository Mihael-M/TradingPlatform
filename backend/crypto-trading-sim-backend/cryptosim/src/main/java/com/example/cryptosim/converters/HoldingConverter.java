package com.example.cryptosim.converters;

import com.example.cryptosim.entity.HoldingEntity;
import utills.model.UserHolding;

public class HoldingConverter {
    public static UserHolding convertToUser(HoldingEntity entity) {
        return new UserHolding(entity.getCrypto(), entity.getAmount());
    }
    public static HoldingEntity convertToEntity(UserHolding user) {
        return new HoldingEntity(user.getCrypto(), user.getAmount());
    }
}
