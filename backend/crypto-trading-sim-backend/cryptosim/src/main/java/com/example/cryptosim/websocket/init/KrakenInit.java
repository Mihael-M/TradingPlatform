package com.example.cryptosim.websocket.init;

import com.example.cryptosim.websocket.KrakenWebSocketClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class KrakenInit implements CommandLineRunner {

    @Override
    public void run(String... args) {
        KrakenWebSocketClient client = new KrakenWebSocketClient();
        client.connect("wss://ws.kraken.com/v2");
    }
}