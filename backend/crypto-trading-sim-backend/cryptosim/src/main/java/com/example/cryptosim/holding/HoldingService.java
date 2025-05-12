package com.example.cryptosim.holding;

import com.example.cryptosim.converters.IHoldingConverter;
import com.example.cryptosim.entity.HoldingEntity;
import org.springframework.stereotype.Component;
import utills.model.Holding;

import java.util.List;
import java.util.UUID;

@Component
public class HoldingService implements Service {
    private IHoldingConverter holdingConverter;
    private HoldingRepository holdingRepository;

    public HoldingService(HoldingRepository holdingRepository, IHoldingConverter holdingConverter) {
        this.holdingConverter = holdingConverter;
        this.holdingRepository = holdingRepository;
    }

    @Override
    public Holding createHolding(HoldingEntity holdingEntity) {
        return  holdingConverter.convertToUser(holdingRepository.createHolding(holdingEntity));
    }

    @Override
    public List<Holding> getAllHoldings() {
        List<HoldingEntity> holdingEntities = holdingRepository.getAllHoldings();
        return holdingEntities.stream()
                .map(holdingConverter::convertToUser)
                .toList();
    }

    @Override
    public Holding getHolding(UUID id) {
        return holdingConverter.convertToUser(holdingRepository.getHolding(id));
    }

    @Override
    public Holding getHolding(String crypto) {
        return holdingConverter.convertToUser(holdingRepository.getHolding(crypto));
    }

    @Override
    public boolean deleteAllHoldings() {
        return holdingRepository.deleteAllHoldings();
    }

    @Override
    public boolean deleteHolding(UUID id) {
        return holdingRepository.deleteHolding(id);
    }

    @Override
    public Holding updateHolding(HoldingEntity holdingEntity) {
        return holdingConverter.convertToUser(holdingRepository.updateHolding(holdingEntity));
    }
}
