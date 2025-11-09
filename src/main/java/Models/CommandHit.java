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

    public CommandHit() {
        super(CommandType.HIT, new String[]{});
    }

    @Override
    public void processForServer(ThreadServidor threadServidor) {
       
    }

    @Override
    public void processInClient(Client client) {
        client.getRefFrame().recibirAtaqueCliente();
    }
    
    
    
}
