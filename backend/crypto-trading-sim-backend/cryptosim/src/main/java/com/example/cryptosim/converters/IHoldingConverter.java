package com.example.cryptosim.converters;

import com.example.cryptosim.entity.HoldingEntity;
import utills.model.Holding;

public interface IHoldingConverter {
    Holding convertToUser(HoldingEntity entity);
    HoldingEntity convertToEntity(Holding user);
}
