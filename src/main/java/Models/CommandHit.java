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
    public CommandHit(String p, String ataque, String attackerName, String row, String col, String row2, String col2, String row3, String col3) {
        super(CommandType.HIT,new String[]{p,ataque, attackerName, row, col, row2, col2, row3, col3});
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
        String row2 = params[5];
        String col2 = params[6];
        String row3 = params[7];
        String col3 = params[8];
        
        
        int fila = Integer.parseInt(row);
        int columna = Integer.parseInt(col);
        int fila2 = Integer.parseInt(row2);
        int columna2 = Integer.parseInt(col2);
        int fila3 = Integer.parseInt(row3);
        int columna3 = Integer.parseInt(col3);
        
        String mensaje;
        if(ataque.equals("KrakenBreath")){
           mensaje = "El jugador: " + attacker + " te atacó con " + p + " usando: " + ataque + " en fila: " + row + " y columna: " + col; 
        }else if(ataque.equals("ThreeLines")){
            mensaje = "El jugador: " + attacker + " te atacó con " + p + " usando: " + ataque + " en los puntos: " +
    "(" + fila + ", " + columna + "), " + 
    "(" + fila2 + ", " + columna2 + "), " + 
    "(" + fila3 + ", " + columna3 + ")";;
        }else{
           mensaje = "El jugador: " + attacker + " te atacó con " + p + " usando: " + ataque; 
        }
        
        if(client.getRefFrame().recibirAtaqueCliente(p, ataque, fila, columna, fila2, columna2, fila3, columna3)){
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
