/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oceanica;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author gabos
 */
public class Casilla {
    private int vida;
    private Personaje p;
    private int x;
    private int y;
    private JPanel panel;

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
        JLabel x = new JLabel("X");
        panel.setLayout(new BorderLayout());
        x.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        x.setForeground(java.awt.Color.WHITE);
        
        panel.add(x, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
        
        x.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        x.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        
    }
    
}
