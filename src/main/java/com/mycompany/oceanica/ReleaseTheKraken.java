/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oceanica;

import java.util.Random;

/**
 *
 * @author lacay
 */
public class ReleaseTheKraken extends Ataque{
    private Random random = new Random();
    
    
    public ReleaseTheKraken(int dano, Tablero tablero) {
        super(dano, tablero);
    }

    
    public void tentaculos(){
        for(int i = 0; i < 3; i++){
            int indexRandom = random.nextInt(600);
            Casilla casillaRandom = tablero.casillas.get(indexRandom);
            danoArea(casillaRandom);
        }
    }
  
    public void kraken_breath(){
        
    }
    
    public void release_the_kraken(){
        
    }
    
    
    public void danoArea(Casilla celda){
        int filaInicial = celda.getX();
        int columnaInicial = celda.getY();
        final int MAX_FILAS = 20;
        final int MAX_COLUMNAS = 30;
        
        
        for(int fila = filaInicial - 1; fila <= filaInicial + 1; fila++){
            for(int columna = columnaInicial - 1; columna <= columnaInicial + 1; columna++){
                if(fila == filaInicial && columna == columnaInicial){
                    continue;
                }
                
                boolean filaValida = (fila >= 0 && fila <= MAX_FILAS);
                boolean columnaValida = (columna >= 0 && columna <= MAX_COLUMNAS);
                
                if(filaValida && columnaValida){
                    tablero.recibirDanoLocacion(fila, columna, dano);
                }
            }
        }
    }

    @Override
    public void aplicarDano(Casilla celda) {
        
    }
    
    
    
}
