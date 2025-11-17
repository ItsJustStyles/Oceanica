package com.mycompany.oceanica;

import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.OnClose;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/game")
public class GameWebSocket {

    // Variable estática para guardar el último resultado recibido
    private static String lastResult = "";

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Cliente conectado: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Mensaje recibido: " + message);

        // Guardamos el valor recibido en la variable
        lastResult = message;

        // Solo para debug, puedes imprimirlo
        System.out.println("Último resultado guardado: " + lastResult);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("Cliente desconectado: " + session.getId());
    }

    // Método para acceder al último resultado
    public static String getLastResult() {
        return lastResult;
    }
}
