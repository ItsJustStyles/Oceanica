/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;
import Models.CommandStartGame;
import Models.Command;
import Models.lobbyUpdateCommand;
import com.mycompany.oceanica.Juego;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Set;


/**
 *
 * @author diego
 */
public class Server {
    private final int PORT = 35500;
    private final int maxConections = 4;
    private ServerSocket serverSocket;
    private ArrayList<ThreadServidor> connectedClients; // arreglo de hilos por cada cliente conectado
    //referencia a la pantalla
    Juego refFrame;
    private ThreadConnections connectionsThread;
    private final Set<String> readyPlayers = new HashSet<>();


    public Server(Juego refFrame) {
        connectedClients = new ArrayList<ThreadServidor>();
        this.refFrame = refFrame;
        this.init();
        connectionsThread = new ThreadConnections(this);
        connectionsThread.start();
    }
    
    //método que inicializa el server
    private void init(){
        try {
            serverSocket = new ServerSocket(PORT);
            //refFrame.writeMessage("Server running!!!");
        } catch (IOException ex) {
            refFrame.writeMessage("Error: " + ex.getMessage());
        }
    }
    public synchronized void markPlayerReady(String playerName) {
        readyPlayers.add(playerName);
        System.out.println("Jugador listo: " + playerName + " (" + readyPlayers.size() + "/" + connectedClients.size() + ")");

        if (readyPlayers.size() >= 1) { // mínimo 2 jugadores listos
            System.out.println("Mínimo de jugadores listos alcanzado. Iniciando partida...");
            broadcast(new CommandStartGame());
        }
}
    
    void executeCommand(Command comando) {
        if (comando.isIsBroadcast())
            this.broadcast(comando);
        else
            this.sendPrivate(comando);

    }
    
    public void broadcast(Command comando){
        for (ThreadServidor client : connectedClients) {
            try {
                client.objectSender.writeObject(comando);
            } catch (IOException ex) {
                
            }
        }

    }
    
    public void sendPrivate(Command comando){
        //asumo que el nombre del cliente viene en la posición 1 .  private_message Andres "Hola"
        if (comando.getParameters().length <= 1)
            return;
        
        String searchName =  comando.getParameters()[1];
        
        for (ThreadServidor client : connectedClients) {
            if (client.name.equals(searchName)){
                try {
                //simulo enviar solo al primero, pero debe buscarse por nombre
                    client.objectSender.writeObject(comando);
                    break;
                } catch (IOException ex) {
                
                }
            }
        }
    }
    
    
    public void showAllNames(){
        List<String> players = new ArrayList<>();
        //this.refFrame.clearMessages();
        //this.refFrame.writeMessage("Usuarios conectados:");
        for (ThreadServidor client : connectedClients) {
            players.add(client.name);
        }
        lobbyUpdateCommand sync = new lobbyUpdateCommand(players);
        broadcast(sync);
    }

    public int getMaxConections() {
        return maxConections;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public ArrayList<ThreadServidor> getConnectedClients() {
        return connectedClients;
    }

    public Juego getRefFrame() {
        return refFrame;
    }
    
    public ThreadServidor getClientByName(String targetName) {
        
        for (ThreadServidor clientThread : connectedClients) {
            if (clientThread.name != null && clientThread.name.equalsIgnoreCase(targetName)) {
                
                return clientThread; // ¡Encontrado!
            }
        }
        return null; 
    }
  
}
