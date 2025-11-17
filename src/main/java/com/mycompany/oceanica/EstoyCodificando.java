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
    Juego refFrame;
    private String registro;
    
    public EstoyCodificando(int dano, Tablero tablero, Juego refFrame, String registro) {
        this.refFrame = refFrame;
        this.registro = registro;
        super(dano, tablero);
    }

    @Override
    public void aplicarDano(Casilla celda) {
        
    }
    
    public void Microprocesador_x264(){
        ReproductorSonido sonido = new ReproductorSonido("/sonidos/Microprocesador.wav", refFrame);
        sonido.play();
    }
    
    public void Slower(){
        ReproductorSonido sonido = new ReproductorSonido("/sonidos/Slower.wav", refFrame);
        sonido.play();
    }
    
    public void NoSePuedeMas(){
        ReproductorSonido sonido = new ReproductorSonido("/sonidos/NoSePuedeMas.wav", refFrame);
        sonido.play();
    }
    
    public void EstoNoEsUnJuego(){
        for(Casilla c : tablero.casillas){
            tablero.recibirDanoLocacion(c.getX(), c.getY(), 10, registro);
        }
        ReproductorSonido sonido = new ReproductorSonido("/sonidos/EstoNoEsUnJuego.wav", refFrame);
        sonido.play();
    }
    
}
