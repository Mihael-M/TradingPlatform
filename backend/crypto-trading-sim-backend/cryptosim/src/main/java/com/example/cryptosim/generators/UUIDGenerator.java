package com.example.cryptosim.generators;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class UUIDGenerator implements IUUIDGenerator {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }
}
