package com.example.cryptosim.generators;

import java.util.UUID;

public class UUIDGenerator implements IUUIDGenerator {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }
}
