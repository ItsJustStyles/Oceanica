package com.mycompany.oceanica;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



public class Oceanica {
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // ESTABLECER EL LOOK AND FEEL AQUÍ, ANTES DE CREAR LA VENTANA
                    // Esto asegura que SIEMPRE se use el mismo LAF,
                    // ya sea al ejecutar desde el IDE o el JAR.
                    
                    // Opción 1: Usar el LAF nativo del sistema operativo (recomendado para apps de escritorio)
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    
                    // Opción 2: Usar un LAF específico como Nimbus (si te gusta su estilo uniforme)
                    // for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    //     if ("Nimbus".equals(info.getName())) {
                    //         UIManager.setLookAndFeel(info.getClassName());
                    //         break;
                    //     }
                    // }
                    
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    System.err.println("Error al establecer el Look and Feel: " + ex.getMessage());
                    // Si falla, la aplicación continuará con el LAF por defecto de la JVM.
                }
                
                // Ahora, crea y muestra tu ventana:
                Juego miJuego = null;
                try {
                    miJuego = new Juego(); // Asumo que VentanaJuego es tu JFrame principal
                } catch (IOException ex) {
                    System.getLogger(Oceanica.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                miJuego.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                miJuego.pack();
                miJuego.setVisible(true);
                miJuego.setLocationRelativeTo(null);
            }
        });
    }
}
