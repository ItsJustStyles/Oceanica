/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oceanica;

import java.util.List;

/**
 *
 * @author lacay
 */
public class realizarAtaquePorGrupo {
    
    Tablero tablero;
    List<Personaje> todosLosPersonajes;
    
    public realizarAtaquePorGrupo(Tablero tablero, List<Personaje> todosLosPersonajes) {
        this.tablero = tablero;
        this.todosLosPersonajes = todosLosPersonajes;
    }
    
    public boolean atacar(String p, String ataque, int fila, int columna, int fila2, int columna2, int fila3, int columna3){
        String GrupoAtaque = buscarPersonaje(p);
        if(GrupoAtaque == null){
            System.out.println("Personaje no valido");
            return false;
        }
        boolean exito = buscarAtaque(GrupoAtaque, ataque, fila, columna, fila2, columna2, fila3, columna3);
        if(exito){
            return true;
        }
        return false;
    }
    
    public boolean buscarAtaque(String ataque, String ataqueSolicitado, int fila, int columna, int fila2, int columna2, int fila3, int columna3){
        switch(ataque){
            case "Release the Kraken":
                ReleaseTheKraken attack = new ReleaseTheKraken(100, tablero);
                if(ataqueSolicitado.equals("Tentaculos")){
                    attack.tentaculos();
                    return true;
                }else if(ataqueSolicitado.equals("KrakenBreath")){
                    attack.kraken_breath(fila, columna);
                    return true;
                }else if(ataqueSolicitado.equals("ReleaseTheKraken")){
                    attack.release_the_kraken();
                    return true;
                }else{
                    return false;
                    //System.out.println("No hay ataque con ese nombre");
                }
            case "Poseidon Trident":
                PoseidonTrident attack2 = new PoseidonTrident(100, tablero);
                if(ataqueSolicitado.equals("ThreeLines")){
                    attack2.ThreeLines(fila, columna, fila2, columna2, fila3, columna3);
                    return true;
                }else{
                    return false;
                }
            case "Fish Telepathy":
                FishTelepathy attack3 = new FishTelepathy(50, tablero);
                if(ataqueSolicitado.equals("Cardumen")){
                    attack3.Cardumen();
                    return true;
                }else if(ataqueSolicitado.equals("SharkAttack")){
                    attack3.Shark_attack();
                    return true;
                }else if(ataqueSolicitado.equals("Pulp")){
                    attack3.Pulp();
                    return true;
                }else{
                    return false;
                }
            default:
                return false;
        }           
    }

    private String buscarPersonaje(String nombrePersonaje) {
        for(Personaje p : todosLosPersonajes){
            if(p.getNombre().equals(nombrePersonaje) || p.getNombreSecundario().equals(nombrePersonaje)){
                return p.getAtaque();
            }
        }
        return null;
    }
    
}
