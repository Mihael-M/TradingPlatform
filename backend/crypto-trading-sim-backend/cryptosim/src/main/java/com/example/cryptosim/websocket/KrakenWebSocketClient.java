package com.example.cryptosim.websocket;

import com.example.cryptosim.cache.PriceCache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import utills.model.Crypto;

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
                     "symbol": [
                       "BTC/USD", "ETH/USD", "USDT/USD", "BNB/USD", "SOL/USD", "XRP/USD", "DOGE/USD", "TON/USD", "ADA/USD", "AVAX/USD",
                       "SHIB/USD", "DOT/USD", "WTRX/USD", "LINK/USD", "ICP/USD", "MATIC/USD", "NEAR/USD", "BCH/USD", "UNI/USD", "LTC/USD"
                     ]
                   }
                 }
            """;

        sendMessage(subscribeMessage);
    }

    @OnMessage
    public void onMessage(String message) throws JsonProcessingException {
        //System.out.println("Received message: " + message);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(message);

        if (node.has("channel") && "ticker".equals(node.get("channel").asText()) && node.has("data")) {
            JsonNode dataArray = node.get("data");
            for (JsonNode data : dataArray) {
                String symbol = data.get("symbol").asText();
                double price = data.get("last").asDouble();

                Crypto crypto = new Crypto(symbol, price);
                //System.out.println(crypto);
                PriceCache.updatePrice(symbol, price);
            }
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