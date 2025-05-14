package com.example.cryptosim.crypto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CryptoController {

    @GetMapping("/cryptos")
    public List<String> getCryptoNames() {
        return Arrays.asList("BTC", "ETH", "USDT", "BNB", "SOL", "XRP", "DOGE", "TON", "ADA", "AVAX",
                "SHIB", "DOT", "PEPE", "LINK", "ICP", "MATIC", "NEAR", "BCH", "UNI", "LTC");
    }
}
