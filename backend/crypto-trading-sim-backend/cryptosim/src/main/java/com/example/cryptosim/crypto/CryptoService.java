package com.example.cryptosim.crypto;

import com.example.cryptosim.websocket.KrakenWebSocketClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import utills.model.exceptions.FailedToLoadCrypto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component

public class CryptoService {
    private KrakenWebSocketClient krakenWebSocketClient;

    public CryptoService(KrakenWebSocketClient krakenWebSocketClient) {
        this.krakenWebSocketClient = krakenWebSocketClient;
    }

    private String formatUrl(String cryptoId) {
        return String.format("https://api.kraken.com/0/public/Ticker?pair=%sUSD", cryptoId);
    }
}
