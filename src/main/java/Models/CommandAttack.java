/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Cliente.Client;
import Models.CommandType;
import Servidor.ThreadServidor;

/**
 *
 * @author diego
 */
public class CommandAttack extends Command{

    public CommandAttack(String[] args) { //ATTACK Andres 5 7
        super(CommandType.ATTACK, args);
    }
    
    @Override
    public void processForServer(ThreadServidor threadServidor) {
        String[] params = this.getParameters();
        
        String objetivo = params[1];
        String row = params[2];
        String columna = params[3];
        System.out.println(objetivo);
        ThreadServidor targetThread = threadServidor.getRefServer().getClientByName(objetivo);
        if(targetThread != null){
            Command hitCommand = new CommandHit();
            // 3. UNICAST: Enviar el comando SÓLO al cliente objetivo
            try {
                targetThread.objectSender.writeObject(hitCommand);
                targetThread.objectSender.flush();
            } catch (java.io.IOException ex) {
                // Manejar la desconexión del objetivo
                threadServidor.getRefServer().getRefFrame().writeMessage("Error al enviar ataque a " + objetivo);
            }
            //this.setIsBroadcast(true);
        }else{
            this.setIsBroadcast(false);
        }
        
    }    
    
}
