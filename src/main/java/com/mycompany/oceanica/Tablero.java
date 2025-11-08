/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.oceanica;

import java.awt.Color;
import java.awt.GridLayout;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    
    public JPanel crearTablero(Personaje p1, Personaje p2, Personaje p3){
        JPanel panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(FILAS + 1, COLUMNAS + 1, 1, 1));        
        panelTablero.add(crearEtiquetaVacia());

        int total = FILAS * COLUMNAS; // 600
        int max1 = (int) ((p1.getPorcentaje() * total) / 100.0);
        int max2 = (int) ((p2.getPorcentaje() * total) / 100.0);
        int max3 = total - max1 - max2; // asegura que sumen exacto

        // Encabezado columnas
        for (int c = 1; c <= COLUMNAS; c++) {
            panelTablero.add(crearEtiquetaNumeracion(String.valueOf(c)));
        }

        // Arma la bolsa y baraja
        List<Integer> bolsa = new ArrayList<>(total);
        for (int i = 0; i < max1; i++) bolsa.add(0);
        for (int i = 0; i < max2; i++) bolsa.add(1);
        for (int i = 0; i < max3; i++) bolsa.add(2);
        Collections.shuffle(bolsa, random);

        int idx = 0;
        for (int f = 1; f <= FILAS; f++) {
            panelTablero.add(crearEtiquetaNumeracion(String.valueOf(f)));
            for (int c = 1; c <= COLUMNAS; c++) {
                int seleccion = bolsa.get(idx++);
                JPanel celda;
                switch (seleccion) {
                    case 0:
                        celda = crearCeldaTablero(f, c, p1, Color.BLUE);
                        break;
                    case 1:
                        celda = crearCeldaTablero(f, c, p2, Color.RED);
                        break;
                    default:
                        celda = crearCeldaTablero(f, c, p3, Color.GREEN); // ojo: p3
                        break;
                }
                panelTablero.add(celda);
            }
        }
        return panelTablero;
    }
    
    private JPanel crearCeldaTablero(int fila, int columna,Personaje p, Color color) {
        JPanel celda = new JPanel();
        Casilla casilla = new Casilla(100,p,fila,columna,celda);
        celda.setBackground(color); 
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
