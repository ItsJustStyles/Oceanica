/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Cliente.Client;
import Servidor.ThreadServidor;

/**
 *
 * @author lacay
 */
public class CommandHit extends Command{

    public CommandHit(String attackerName, String row, String col) {
        super(CommandType.HIT,new String[]{attackerName, row, col});
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
       
    }

    @Override
    public void processInClient(Client client) {
        String[] params = this.getParameters();
        String attacker = params[0];
        String row = params[1];
        String col = params[2];
        
        String mensaje = "El jugador: " + attacker + " te atacó en fila: " + row + " y columna: " + col;
        client.getRefFrame().writeBitacora(mensaje);
        client.getRefFrame().recibirAtaqueCliente();
    }
    
    
    
}
