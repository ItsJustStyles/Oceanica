/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oceanica;

/**
 *
 * @author lacay
 */
public class EstoyCodificando extends Ataque{

    public EstoyCodificando(int dano, Tablero tablero) {
        super(dano, tablero);
    }

    @Override
    public void aplicarDano(Casilla celda) {
        
    }
    
    public void Microprocesador_x264(){
        
    }
    
    public void Slower(){
        
    }
    
    public void NoSePuedeMas(){
        
    }
    
    public void EstoNoEsUnJuego(){
        for(Casilla c : tablero.casillas){
            tablero.recibirDanoLocacion(c.getX(), c.getY(), 10);
        }
        String rutaSonido = "/sonidos/EstoNoEsUnJuego.wav";
        try{
        
            ReproductorSonido reproductor = new ReproductorSonido(rutaSonido);
            reproductor.reproducir();
            reproductor.detener();
            
        }catch(Exception e){
            
            System.err.println("Ocurrió un error al reproducir el sonido:");
            e.printStackTrace();
            
        }
    }
    
}
