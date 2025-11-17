package com.mycompany.oceanica;

import org.glassfish.tyrus.server.Server;

public class Oceanica {
    public static void main(String[] args) {

        int port = Integer.parseInt(System.getenv("PORT"));

        Server server = new Server("0.0.0.0", port, "/", null, GameWebSocket.class);

        try {
            server.start();
            System.out.println("WebSocket abierto en: ws://0.0.0.0:" + port + "/game");

            // Mantener vivo el servidor PARA SIEMPRE
            Thread.currentThread().join();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
