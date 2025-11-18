/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Servidor.Server;
import Servidor.ThreadServidor;

/**
 *
 * @author lacay
 */
public class CommandEliminarJugador extends Command{

    public CommandEliminarJugador(String objetivo) {
        super(CommandType.ELIMINARJUGADOR, new String[]{objetivo});
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
        String objetivo = getParameters()[0];
        Server server = threadServidor.getRefServer();
        
        ThreadServidor target = server.getClientByName(objetivo);
        
        server.eliminarjugador(target);
        
        System.out.println("Jugador eliminado de turnos correctamente: " + objetivo);
        for(ThreadServidor p : server.turnOrder){
            System.out.println(p.name);
        }
        
        this.setIsBroadcast(false);
    
    }
}
