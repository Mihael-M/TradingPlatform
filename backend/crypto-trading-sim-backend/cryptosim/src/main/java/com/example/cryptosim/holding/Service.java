package com.example.cryptosim.holding;

import com.example.cryptosim.entity.HoldingEntity;
import utills.model.Holding;

import java.util.List;
import java.util.UUID;

public interface Service {
    Holding createHolding(HoldingEntity holdingEntity);
    List<Holding> getAllHoldings();
    Holding getHolding(UUID id);
    Holding getHolding(String crypto);
    boolean deleteAllHoldings();
    boolean deleteHolding(UUID id);
    Holding updateHolding(HoldingEntity holdingEntity);
}
