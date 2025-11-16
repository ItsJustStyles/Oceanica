/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oceanica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 *
 * @author gabos
 */
public class Casilla {
    private int vida;
    private boolean muerta;
    private Personaje p;
    private int x;
    private int y;
    private JPanel panel;
    private Timer temporizador;
    
    private JLabel pX;
    private JLabel vidaC;

    public Casilla(int vida, Personaje p, int x, int y, JPanel panel) {
        this.vida = vida;
        this.p = p;
        this.x = x;
        this.y = y;
        this.panel = panel;
    }

    
    public boolean esta_vivo(){
        return vida > 0;
    }
    
    public void recibirAtaque(int fuerza){
        this.vida -= fuerza;
    }
    
    public void curar(int sanidad){
        this.vida += sanidad;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void ponerX(){
        muerta = true;
        pX = new JLabel("X");
        panel.setLayout(new BorderLayout());
        pX.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        pX.setForeground(java.awt.Color.WHITE);
        
        pX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pX.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        
        panel.add(pX, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint(); 
    }
    
    private void quitarVida(){
        if (vidaC != null) {
            panel.remove(vidaC); 
            vidaC = null;        
            panel.revalidate();      
            panel.repaint();        
        }
    }
    
    public void ponerVida(){
        if(pX != null){
            panel.remove(pX);
            pX = null;
        }
        if(vidaC != null){
            panel.remove(vidaC);
            vidaC = null;
        }
        if (temporizador != null && temporizador.isRunning()) {
            temporizador.stop();
        }
        
        vidaC = new JLabel(vida + "");
        vidaC.setFont(new java.awt.Font("Arial", Font.BOLD, 8));
        vidaC.setForeground(java.awt.Color.WHITE);
        
        vidaC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vidaC.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        //vidaC.setPreferredSize(new Dimension(15, 15));
        
        panel.add(vidaC, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint(); 
        
        int tiempoAnimacion = 1500;
        
        temporizador = new Timer(tiempoAnimacion, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                quitarVida();
                if(muerta){
                    ponerX();
                }
                temporizador.stop();
            }
            
        });
        temporizador.setRepeats(false);
        temporizador.start();
    }
    
}
