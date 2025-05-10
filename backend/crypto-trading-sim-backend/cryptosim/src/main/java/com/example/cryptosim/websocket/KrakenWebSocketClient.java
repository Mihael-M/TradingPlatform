package com.example.cryptosim.websocket;

import com.example.cryptosim.cache.PriceCache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import java.net.URI;

@ClientEndpoint
public class KrakenWebSocketClient {

    private Session session;

    public void connect(String uri) {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            container.connectToServer(this, new URI(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to Kraken WebSocket");

        String subscribeMessage = """
                {
                   "method": "subscribe",
                   "params": {
                     "channel": "ticker",
                     "symbol": ["BTC/USD", "ETH/USD"]
                   }
                 }
            """;

        sendMessage(subscribeMessage);
    }

    @OnMessage
    public void onMessage(String message) throws JsonProcessingException {
        System.out.println("Received message: " + message);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(message);

// Simplified check
        if (node.has("symbol") && node.has("price")) {
            String symbol = node.get("symbol").asText();
            double price = node.get("price").asDouble();
            System.out.println(symbol + ": " + price);
            PriceCache.updatePrice(symbol, price);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Connection closed: " + reason);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("Error: " + throwable.getMessage());
    }

    public void sendMessage(String message) {
        try {
            this.session.getAsyncRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}