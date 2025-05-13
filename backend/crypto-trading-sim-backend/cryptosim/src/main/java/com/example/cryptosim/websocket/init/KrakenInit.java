package com.example.cryptosim.websocket.init;

import com.example.cryptosim.websocket.KrakenWebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class KrakenInit implements CommandLineRunner {

    @Autowired
    private KrakenWebSocketClient krakenWebSocketClient;

    @Override
    public void run(String... args) {
        krakenWebSocketClient.connect("wss://ws.kraken.com/v2");
    }
}