package com.example.cryptosim.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class PriceCache {
    private static final Map<String, Double> prices = new ConcurrentHashMap<>();

    public static void updatePrice(String symbol, double price) {
        prices.put(symbol, price);
    }

    public static double getPrice(String symbol) {
        return prices.getOrDefault(symbol, 0.0);
    }
}
