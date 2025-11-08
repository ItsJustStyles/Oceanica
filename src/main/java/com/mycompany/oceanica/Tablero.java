/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oceanica;

import java.awt.Color;
import java.awt.GridLayout;
import static java.lang.Math.random;
import java.util.Random;
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
    Random random = new Random();
    
    public JPanel crearTablero(Personaje p1,Personaje p2,Personaje p3){
        JPanel panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(FILAS + 1, COLUMNAS + 1, 1, 1));        
        panelTablero.add(crearEtiquetaVacia());
        int max1 = p1.getPorcentaje()/100 * 600;
        int max2 = p2.getPorcentaje()/100 * 600;
        int max3 = p3.getPorcentaje()/100 * 600;
        
        for (int c = 1; c <= COLUMNAS; c++) {
            panelTablero.add(crearEtiquetaNumeracion(String.valueOf(c)));
        }

        for (int f = 1; f <= FILAS; f++) {
            
            panelTablero.add(crearEtiquetaNumeracion(String.valueOf(f))); 

            for (int c = 1; c <= COLUMNAS; c++) {
                JPanel celda;
                int seleccion = random.nextInt(3);
                switch (seleccion) {
                    case 0:
                        celda = crearCeldaTablero(f, c, p1);
                        break; 
                    case 1:
                        celda = crearCeldaTablero(f, c, p2);
                        break;
                    default:
                        celda = crearCeldaTablero(f, c, p2);
                        break; 
                }
                panelTablero.add(celda);
            }
        }
        
        return panelTablero;
    }
    
    private JPanel crearCeldaTablero(int fila, int columna,Personaje p) {
        JPanel celda = new JPanel();
        Casilla casilla = new Casilla(100,p,fila,columna,celda);
        celda.setBackground(); 
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
