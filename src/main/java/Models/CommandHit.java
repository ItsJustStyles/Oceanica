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
    boolean exito;
    public CommandHit(String p, String ataque, String attackerName, String row, String col) {
        super(CommandType.HIT,new String[]{p,ataque, attackerName, row, col});
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
       
    }

    @Override
    public void processInClient(Client client) {
        String[] params = this.getParameters();
        
        String p = params[0];
        String ataque = params[1];
        String attacker = params[2];
        String row = params[3];
        String col = params[4];
        
        
        int fila = Integer.parseInt(row);
        int columna = Integer.parseInt(col);
        
        String mensaje;
        if(ataque.equals("KrakenBreath")){
           mensaje = "El jugador: " + attacker + " te atacó con " + p + " usando: " + ataque + " en fila: " + row + " y columna: " + col; 
        }else{
           mensaje = "El jugador: " + attacker + " te atacó con " + p + " usando: " + ataque; 
        }
        
        if(client.getRefFrame().recibirAtaqueCliente(p, ataque, fila, columna)){
            client.getRefFrame().writeBitacora(mensaje);
            exito = true;
        }else{
            exito = false;
        }

    }

    public boolean isExito() {
        return exito;
    }
}
