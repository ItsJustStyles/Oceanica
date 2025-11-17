package com.mycompany.oceanica;

import org.glassfish.tyrus.server.Server;

public class Oceanica {  
    public static void main(String[] args) {

        int port = Integer.parseInt(System.getenv("PORT")); // Render asigna este puerto

        Server server = new Server("0.0.0.0", port, "/", null, GameWebSocket.class);
        new WebPopupExternal("https://itsjuststyles.github.io/sans.github.io/");

        try {
            server.start();
            System.out.println("Servidor WebSocket iniciado en puerto: " + port);

            while (GameWebSocket.getLastResult().isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Mensaje recibido: " + GameWebSocket.getLastResult());
            System.out.println("Deteniendo servidor...");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
