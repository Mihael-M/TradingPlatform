package com.example.cryptosim.converters;

import com.example.cryptosim.entity.HoldingEntity;
import org.springframework.stereotype.Component;
import utills.model.Holding;

@Component
public class HoldingConverter implements IHoldingConverter {
    private final IUUIDConverter uuidConverter;

    public HoldingConverter(IUUIDConverter uuidConverter) {
        this.uuidConverter = uuidConverter;
    }

    @Override
    public Holding convertToUser(HoldingEntity entity) {
        return new Holding(
                entity.getId().toString(),
                entity.getCrypto(),
                entity.getQuantity(),
                entity.getTotalValue(),
                entity.getAccountId().toString()
        );
    }

    @Override
    public HoldingEntity convertToEntity(Holding user) {
        return new HoldingEntity(
                uuidConverter.convertFromString(user.getId()),
                user.getCrypto(),
                user.getQuantity(),
                user.getTotalValue(),
                uuidConverter.convertFromString(user.getAccountId())
        );
    }
}
