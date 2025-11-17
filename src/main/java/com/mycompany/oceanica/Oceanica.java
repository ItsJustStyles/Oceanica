/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oceanica;

import org.glassfish.tyrus.server.Server;


/**
 *
 * @author lacay
 */
public class Oceanica {  
public static void main(String[] args) {
    
        Server server = new Server("localhost", 8080, "/", null, GameWebSocket.class);
        new WebPopupExternal("https://itsjuststyles.github.io/sans.github.io/");

        try {
            server.start();
            System.out.println("Servidor WebSocket iniciado en ws://localhost:8080/game");
            
            while (GameWebSocket.getLastResult().isEmpty()) {
                try {
                    Thread.sleep(100); // espera 100ms antes de revisar otra vez
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

