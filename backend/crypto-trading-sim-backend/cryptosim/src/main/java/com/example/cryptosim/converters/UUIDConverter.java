package com.example.cryptosim.converters;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class UUIDConverter implements IUUIDConverter {

    @Override
    public UUID convertFromString(String accountId) {
        return UUID.fromString(accountId);
    }
}
