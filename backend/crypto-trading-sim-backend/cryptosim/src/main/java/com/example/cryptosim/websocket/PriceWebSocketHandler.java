package com.example.cryptosim.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PriceWebSocketHandler extends TextWebSocketHandler {

    // This list can store all connected WebSocket sessions
    private final List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.add(session); // Add the new session to the list
        System.out.println("New WebSocket connection established.");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle incoming messages, if needed (optional)
        super.handleTextMessage(session, message);
    }

    // Method to send price updates to all connected clients
    public void sendPriceUpdate(String priceUpdate) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(priceUpdate));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
