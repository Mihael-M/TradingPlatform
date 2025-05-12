package com.example.cryptosim.crypto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import utills.model.Crypto;
import utills.model.exceptions.FailedToLoadCrypto;

public class CryptoService {
    private final RestTemplate restTemplate;

    public CryptoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double getCoinPrice(String cryptoId) throws Exception {
        String url = formatUrl(cryptoId);
        ResponseEntity<Crypto> response = restTemplate.getForEntity(url, Crypto.class);
        Crypto coin = response.getBody();
        if (coin == null) {
            throw new FailedToLoadCrypto("Unavailable crypto token");
        }
        return coin.getCurrentPrice();
    }

    private String formatUrl(String cryptoId) {
        return String.format("https://api.binance.com/api/v3/ticker/price?symbol=%sUSDT", cryptoId);
    }
}
