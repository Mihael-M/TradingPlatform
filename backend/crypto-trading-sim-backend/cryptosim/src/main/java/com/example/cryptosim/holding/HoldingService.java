package com.example.cryptosim.holding;

import com.example.cryptosim.converters.IHoldingConverter;
import com.example.cryptosim.entity.HoldingEntity;
import utills.model.Holding;

import java.util.List;
import java.util.UUID;

public class HoldingService implements Service {
    private IHoldingConverter holdingConverter;
    private HodlingRepository hodlingRepository;

    public HoldingService(HodlingRepository hodlingRepository,IHoldingConverter holdingConverter) {
        this.holdingConverter = holdingConverter;
        this.hodlingRepository = hodlingRepository;
    }

    @Override
    public Holding createHolding(HoldingEntity holdingEntity) {
        return  holdingConverter.convertToUser(hodlingRepository.createHolding(holdingEntity));
    }

    @Override
    public List<Holding> getAllHoldings() {
        List<HoldingEntity> holdingEntities = hodlingRepository.getAllHoldings();
        return holdingEntities.stream()
                .map(holdingConverter::convertToUser)
                .toList();
    }

    @Override
    public Holding getHolding(UUID id) {
        return holdingConverter.convertToUser(hodlingRepository.getHolding(id));
    }

    @Override
    public Holding getHolding(String crypto) {
        return holdingConverter.convertToUser(hodlingRepository.getHolding(crypto));
    }

    @Override
    public boolean deleteAllHoldings() {
        return hodlingRepository.deleteAllHoldings();
    }

    @Override
    public boolean deleteHolding(UUID id) {
        return hodlingRepository.deleteHolding(id);
    }

    @Override
    public Holding updateHolding(HoldingEntity holdingEntity) {
        return holdingConverter.convertToUser(hodlingRepository.updateHolding(holdingEntity));
    }
}
