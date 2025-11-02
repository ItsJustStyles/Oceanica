/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oceanica;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author lacay
 */
public class Tablero{
    private static final int FILAS = 20;
    private static final int COLUMNAS = 30;
    
    public JPanel crearTablero(){
        JPanel panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(FILAS + 1, COLUMNAS + 1, 1, 1));
        
        panelTablero.add(crearEtiquetaVacia());
        
        for (int c = 1; c <= COLUMNAS; c++) {
            panelTablero.add(crearEtiquetaNumeracion(String.valueOf(c)));
        }

        for (int f = 1; f <= FILAS; f++) {
            
            panelTablero.add(crearEtiquetaNumeracion(String.valueOf(f))); 

            for (int c = 1; c <= COLUMNAS; c++) {
                
                JPanel celda = crearCeldaTablero(f, c);
                panelTablero.add(celda);
            }
        }
        
        return panelTablero;
    }
    
    private JPanel crearCeldaTablero(int fila, int columna) {
        JPanel celda = new JPanel();
        celda.setBackground(Color.WHITE); 
        celda.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); 
        return celda;
    }
    
    private JLabel crearEtiquetaNumeracion(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
        etiqueta.setBackground(new Color(230, 230, 230));
        etiqueta.setOpaque(true);
        etiqueta.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        return etiqueta;
    }

    private JLabel crearEtiquetaVacia() {
        JLabel vacia = new JLabel("");
        vacia.setBackground(new Color(200, 200, 200));
        vacia.setOpaque(true);
        vacia.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        return vacia;
    }
}
