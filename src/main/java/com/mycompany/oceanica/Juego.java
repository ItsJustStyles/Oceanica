/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.oceanica;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.Border;

public class Juego extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Juego.class.getName());
    
    private Tablero tablero;
    
    private CardLayout cardLayout = new CardLayout();
    
    private List<Personaje> todosLosPersonajes;
    private List<Personaje> heroesElegidos;
    
    private final Set<String> idsSeleccionados = new HashSet<>(); 
    private final Map<String, JComponent> componentesSeleccionados = new HashMap<>(); 

    private final Border BORDE_SELECCION = BorderFactory.createLineBorder(new Color(13, 35, 71), 3);
    private final Border BORDE_NORMAL = BorderFactory.createEmptyBorder();
    
    
    public Juego() throws IOException {
        initComponents();
        setResizable(false);
        
        //Para aumentar la sensibilidad del scroll en personajes xd
        JScrollBar verticalBar = jScrollPane1.getVerticalScrollBar();
        verticalBar.setUnitIncrement(25);
        
        colocarFondos();
        this.todosLosPersonajes = GestorJson.cargarPersonajes();
        cargarPersonajesEnScrollPanel();
        
    }

    private void cargarPersonajesEnScrollPanel(){
        try {
            for (Personaje p : this.todosLosPersonajes) {
                JPanel panelPersonaje = new JPanel();
                panelPersonaje.setOpaque(false);
                panelPersonaje.setBorder(null);
                panelPersonaje.setLayout(new javax.swing.BoxLayout(panelPersonaje, javax.swing.BoxLayout.Y_AXIS));

                
                JLabel lblHumanidad = new JLabel("Humanidad: " + p.getPorcentaje());
                lblHumanidad.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                
                ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(p.getRutaIcon()));
                
                JLabel lblIcono = new JLabel();
                ImageIcon iconoRedimensionado = new ImageIcon(
                    iconoOriginal.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)
                );
                lblIcono.setIcon(iconoRedimensionado);
                lblIcono.setAlignmentX(JComponent.CENTER_ALIGNMENT);


                JLabel lblNombre = new JLabel(p.getNombre());
                lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 12));
                lblNombre.setAlignmentX(JComponent.CENTER_ALIGNMENT); 

                JLabel lblAtaque = new JLabel(p.getAtaque());
                lblAtaque.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                
                JLabel lblPoder = new JLabel("Poder: " + p.getPoder());
                lblPoder.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                
                JLabel lblResistencia = new JLabel("Resistencia: " + p.getResistencia());
                lblResistencia.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                
                JLabel lblSanidad = new JLabel("Sanidad: " + p.getSanidad());
                lblSanidad.setAlignmentX(JComponent.CENTER_ALIGNMENT);

                panelPersonaje.add(lblHumanidad);
                panelPersonaje.add(lblIcono);
                panelPersonaje.add(lblNombre);
                panelPersonaje.add(lblAtaque);
                panelPersonaje.add(lblPoder);
                panelPersonaje.add(lblResistencia);
                panelPersonaje.add(lblSanidad);

                panelPersonaje.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        manejarSeleccion(panelPersonaje, p.getNombre());
                    }
                });

                jugadoresScroll.add(panelPersonaje);
            }

            jugadoresScroll.revalidate();
            jugadoresScroll.repaint();

        } catch (Exception e) {
            System.err.println("Error al cargar personajes en la interfaz: " + e.getMessage());
        }
    }
    
    private void manejarSeleccion(JComponent componenteActual, String idPersonaje) {
        if (componentesSeleccionados.containsKey(idPersonaje)) {
            componenteActual.setBorder(BORDE_NORMAL); 
            idsSeleccionados.remove(idPersonaje);
            componentesSeleccionados.remove(idPersonaje);

        } 
        else {
            if (idsSeleccionados.size() < 3) {

                componenteActual.setBorder(BORDE_SELECCION); 
                idsSeleccionados.add(idPersonaje);
                componentesSeleccionados.put(idPersonaje, componenteActual); 

            } else {
                JOptionPane.showMessageDialog(this, "Solo puedes seleccionar un máximo de 3 personajes.");
            }
        }
        System.out.println("Personajes seleccionados: " + idsSeleccionados.size());
    }
    
    
    public void vidaTropas(){

        String textoVida = "Vida: " + tablero.casillasVivas() + "%"; 
        String textoCasillasDestruidas = "Casillas destruidas: " + tablero.casillasDestruidas();

        String nombreP1 = heroesElegidos.get(0).getNombre();
        String nombreP2 = heroesElegidos.get(1).getNombre();
        String nombreP3 = heroesElegidos.get(2).getNombre();

        String casillasP1 = tablero.casillasDestruidasP1() + " de " + tablero.casillasP1.size() + " casillas";
        String porcentajeP1 = tablero.porcentajeP1() + "%";
        lblporcentajeP1.setText(porcentajeP1);
        lblnombreP1.setText(nombreP1);
        lblDestruidasP1.setText(casillasP1);

        String casillasP2 = tablero.casillasDestruidasP1() + " de " + tablero.casillasP2.size() + " casillas";
        String porcentajeP2 = tablero.porcentajeP2() + "%";
        lblporcentajeP2.setText(porcentajeP2);
        lblnombreP2.setText(nombreP2);
        lblDestruidasP2.setText(casillasP2);

        String casillasP3 = tablero.casillasDestruidasP1() + " de " + tablero.casillasP3.size() + " casillas";
        String porcentajeP3 = tablero.porcentajeP3() + "%";
        lblporcentajeP3.setText(porcentajeP3);
        lblnombreP3.setText(nombreP3);
        lblDestruidasP3.setText(casillasP3);

        lblVidaTotal.setText(textoVida);
        lblVidaTotal.setForeground(Color.WHITE);
        lblcasillasDestruidas.setText(textoCasillasDestruidas);
        lblcasillasDestruidas.setForeground(Color.WHITE);

        panelVidaTropas.repaint();
        
    }
    
    public List<Personaje> obtenerPersonajesSeleccionados() {
    
    List<Personaje> listaFinal = new ArrayList<>();
    
    for (Personaje p : this.todosLosPersonajes) {
        
        if (idsSeleccionados.contains(p.getNombre())) {
            listaFinal.add(p);
        }
    }
    
    return listaFinal;
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    private void colocarFondos(){
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
        
        JPanelImage miImagen = new JPanelImage(Menu,"/imagenes/fondoMenu.jpg");
        Menu.add(miImagen).repaint();
        JPanelImage miImagen2 = new JPanelImage(luchadores, "/imagenes/marcoPersonajes.png");
        luchadores.add(miImagen2).repaint();
        JPanelImage miImagen3 = new JPanelImage(Juego,"/imagenes/fondoBatalla.jpeg");
        Juego.add(miImagen3).repaint();
        JPanelImage miImagen4 = new JPanelImage(SeleccionLuchadores, "/imagenes/fondoSeleccion.jpeg");
        SeleccionLuchadores.add(miImagen4).repaint();
        
        Color fondoNegroTransparente = new Color(0, 0, 0, 100);
        Color fondoCuadrado = new Color(38, 84, 158, 100);
        jpanelP1.setBackground(fondoCuadrado);
        jpanelP2.setBackground(fondoCuadrado);
        jpanelP3.setBackground(fondoCuadrado);
        
        panelBitacora.setBackground(fondoNegroTransparente);
        panelResultados.setBackground(fondoNegroTransparente);
        panelVidaTropas.setBackground(fondoNegroTransparente);
        panelConsola.setBackground(fondoNegroTransparente);
        panelCoords.setBackground(fondoNegroTransparente);
        //jugadoresScroll.add(miImagen2).repaint(); No sirvio xd
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Menu = new javax.swing.JPanel();
        Jugar = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        BuscarPartida = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        SeleccionLuchadores = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        luchadores = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jugadoresScroll = new javax.swing.JPanel();
        Seleccionar = new javax.swing.JButton();
        Juego = new javax.swing.JPanel();
        panelBitacora = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        panelResultados = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        personajes = new javax.swing.JPanel();
        TableroJuego = new javax.swing.JPanel();
        panelVidaTropas = new javax.swing.JPanel();
        lblVidaTotal = new javax.swing.JLabel();
        lblcasillasDestruidas = new javax.swing.JLabel();
        jpanelP1 = new javax.swing.JPanel();
        lblnombreP1 = new javax.swing.JLabel();
        lblporcentajeP1 = new javax.swing.JLabel();
        lblDestruidasP1 = new javax.swing.JLabel();
        jpanelP2 = new javax.swing.JPanel();
        lblnombreP2 = new javax.swing.JLabel();
        lblporcentajeP2 = new javax.swing.JLabel();
        lblDestruidasP2 = new javax.swing.JLabel();
        jpanelP3 = new javax.swing.JPanel();
        lblnombreP3 = new javax.swing.JLabel();
        lblporcentajeP3 = new javax.swing.JLabel();
        lblDestruidasP3 = new javax.swing.JLabel();
        panelConsola = new javax.swing.JPanel();
        panelCoords = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setSize(new java.awt.Dimension(600, 400));
        getContentPane().setLayout(new java.awt.CardLayout());

        Menu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Menu.setPreferredSize(new java.awt.Dimension(1022, 667));

        Jugar.setText("Jugar");
        Jugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JugarActionPerformed(evt);
            }
        });

        Salir.setText("Salir");
        Salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Oceanica\n");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout MenuLayout = new javax.swing.GroupLayout(Menu);
        Menu.setLayout(MenuLayout);
        MenuLayout.setHorizontalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLayout.createSequentialGroup()
                .addGap(463, 463, 463)
                .addGroup(MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Salir, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Jugar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(425, Short.MAX_VALUE))
        );
        MenuLayout.setVerticalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(180, 180, 180)
                .addComponent(Jugar)
                .addGap(180, 180, 180)
                .addComponent(Salir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(Menu, "card2");

        jLabel2.setText("Crear partida");

        jLabel3.setText("Buscar partida");

        jButton1.setText("Crear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Buscar");

        javax.swing.GroupLayout BuscarPartidaLayout = new javax.swing.GroupLayout(BuscarPartida);
        BuscarPartida.setLayout(BuscarPartidaLayout);
        BuscarPartidaLayout.setHorizontalGroup(
            BuscarPartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuscarPartidaLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(BuscarPartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 706, Short.MAX_VALUE)
                .addGroup(BuscarPartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(66, 66, 66))
        );
        BuscarPartidaLayout.setVerticalGroup(
            BuscarPartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuscarPartidaLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(BuscarPartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(80, 80, 80)
                .addGroup(BuscarPartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(526, Short.MAX_VALUE))
        );

        getContentPane().add(BuscarPartida, "card3");

        SeleccionLuchadores.setPreferredSize(new java.awt.Dimension(1022, 667));

        jLabel4.setFont(new java.awt.Font("Unispace", 0, 36)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Selección de luchadores");

        luchadores.setOpaque(false);

        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setOpaque(false);

        jugadoresScroll.setForeground(new java.awt.Color(0, 0, 0));
        jugadoresScroll.setOpaque(false);
        jugadoresScroll.setLayout(new java.awt.GridLayout(0, 3, 0, 35));
        jScrollPane1.setViewportView(jugadoresScroll);

        javax.swing.GroupLayout luchadoresLayout = new javax.swing.GroupLayout(luchadores);
        luchadores.setLayout(luchadoresLayout);
        luchadoresLayout.setHorizontalGroup(
            luchadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(luchadoresLayout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        luchadoresLayout.setVerticalGroup(
            luchadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(luchadoresLayout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        Seleccionar.setText("Seleccionar luchadores");
        Seleccionar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Seleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeleccionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SeleccionLuchadoresLayout = new javax.swing.GroupLayout(SeleccionLuchadores);
        SeleccionLuchadores.setLayout(SeleccionLuchadoresLayout);
        SeleccionLuchadoresLayout.setHorizontalGroup(
            SeleccionLuchadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SeleccionLuchadoresLayout.createSequentialGroup()
                .addGroup(SeleccionLuchadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, SeleccionLuchadoresLayout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(luchadores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, SeleccionLuchadoresLayout.createSequentialGroup()
                        .addGap(404, 404, 404)
                        .addComponent(Seleccionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(216, 216, 216)))
                .addGap(212, 212, 212))
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        SeleccionLuchadoresLayout.setVerticalGroup(
            SeleccionLuchadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SeleccionLuchadoresLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel4)
                .addGap(40, 40, 40)
                .addComponent(luchadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(Seleccionar)
                .addContainerGap())
        );

        getContentPane().add(SeleccionLuchadores, "card4");

        Juego.setBackground(new java.awt.Color(0, 0, 0));
        Juego.setPreferredSize(new java.awt.Dimension(1022, 667));

        panelBitacora.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));
        panelBitacora.setForeground(new java.awt.Color(255, 255, 255));
        panelBitacora.setPreferredSize(new java.awt.Dimension(200, 150));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Bitácora");

        javax.swing.GroupLayout panelBitacoraLayout = new javax.swing.GroupLayout(panelBitacora);
        panelBitacora.setLayout(panelBitacoraLayout);
        panelBitacoraLayout.setHorizontalGroup(
            panelBitacoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBitacoraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelBitacoraLayout.setVerticalGroup(
            panelBitacoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBitacoraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(226, Short.MAX_VALUE))
        );

        panelResultados.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Resultado del ataque:");

        javax.swing.GroupLayout panelResultadosLayout = new javax.swing.GroupLayout(panelResultados);
        panelResultados.setLayout(panelResultadosLayout);
        panelResultadosLayout.setHorizontalGroup(
            panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        panelResultadosLayout.setVerticalGroup(
            panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(248, Short.MAX_VALUE))
        );

        personajes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));
        personajes.setOpaque(false);
        personajes.setLayout(new java.awt.GridLayout(3, 2));

        TableroJuego.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));

        javax.swing.GroupLayout TableroJuegoLayout = new javax.swing.GroupLayout(TableroJuego);
        TableroJuego.setLayout(TableroJuegoLayout);
        TableroJuegoLayout.setHorizontalGroup(
            TableroJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 630, Short.MAX_VALUE)
        );
        TableroJuegoLayout.setVerticalGroup(
            TableroJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 267, Short.MAX_VALUE)
        );

        panelVidaTropas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));

        lblVidaTotal.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblVidaTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblVidaTotal.setText("Vida: 100%");

        lblcasillasDestruidas.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblcasillasDestruidas.setForeground(new java.awt.Color(255, 255, 255));
        lblcasillasDestruidas.setText("Casillas destruidas: 0");

        jpanelP1.setForeground(new java.awt.Color(255, 0, 204));

        lblnombreP1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblnombreP1.setForeground(new java.awt.Color(255, 255, 255));
        lblnombreP1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblnombreP1.setText("Nombre");
        lblnombreP1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblporcentajeP1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblporcentajeP1.setForeground(new java.awt.Color(255, 255, 255));
        lblporcentajeP1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblporcentajeP1.setText("100%");
        lblporcentajeP1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblDestruidasP1.setForeground(new java.awt.Color(255, 255, 255));
        lblDestruidasP1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDestruidasP1.setText("100 de 100 casillas");
        lblDestruidasP1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jpanelP1Layout = new javax.swing.GroupLayout(jpanelP1);
        jpanelP1.setLayout(jpanelP1Layout);
        jpanelP1Layout.setHorizontalGroup(
            jpanelP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblnombreP1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblDestruidasP1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelP1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblporcentajeP1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpanelP1Layout.setVerticalGroup(
            jpanelP1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelP1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblnombreP1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblporcentajeP1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDestruidasP1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpanelP2.setForeground(new java.awt.Color(255, 0, 204));

        lblnombreP2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblnombreP2.setForeground(new java.awt.Color(255, 255, 255));
        lblnombreP2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblnombreP2.setText("Nombre");
        lblnombreP2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblporcentajeP2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblporcentajeP2.setForeground(new java.awt.Color(255, 255, 255));
        lblporcentajeP2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblporcentajeP2.setText("100%");
        lblporcentajeP2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblDestruidasP2.setForeground(new java.awt.Color(255, 255, 255));
        lblDestruidasP2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDestruidasP2.setText("100 de 100 casillas");
        lblDestruidasP2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jpanelP2Layout = new javax.swing.GroupLayout(jpanelP2);
        jpanelP2.setLayout(jpanelP2Layout);
        jpanelP2Layout.setHorizontalGroup(
            jpanelP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblnombreP2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelP2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblporcentajeP2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addComponent(lblDestruidasP2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        jpanelP2Layout.setVerticalGroup(
            jpanelP2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelP2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblnombreP2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblporcentajeP2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDestruidasP2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpanelP3.setForeground(new java.awt.Color(255, 0, 204));

        lblnombreP3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblnombreP3.setForeground(new java.awt.Color(255, 255, 255));
        lblnombreP3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblnombreP3.setText("Nombre");
        lblnombreP3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblporcentajeP3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblporcentajeP3.setForeground(new java.awt.Color(255, 255, 255));
        lblporcentajeP3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblporcentajeP3.setText("100%");
        lblporcentajeP3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lblDestruidasP3.setForeground(new java.awt.Color(255, 255, 255));
        lblDestruidasP3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDestruidasP3.setText("100 de 100 casillas");
        lblDestruidasP3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jpanelP3Layout = new javax.swing.GroupLayout(jpanelP3);
        jpanelP3.setLayout(jpanelP3Layout);
        jpanelP3Layout.setHorizontalGroup(
            jpanelP3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblnombreP3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelP3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblporcentajeP3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addComponent(lblDestruidasP3, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        jpanelP3Layout.setVerticalGroup(
            jpanelP3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelP3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblnombreP3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblporcentajeP3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDestruidasP3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelVidaTropasLayout = new javax.swing.GroupLayout(panelVidaTropas);
        panelVidaTropas.setLayout(panelVidaTropasLayout);
        panelVidaTropasLayout.setHorizontalGroup(
            panelVidaTropasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVidaTropasLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(panelVidaTropasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jpanelP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVidaTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelVidaTropasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVidaTropasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblcasillasDestruidas, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelVidaTropasLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jpanelP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jpanelP3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 297, Short.MAX_VALUE))))
        );
        panelVidaTropasLayout.setVerticalGroup(
            panelVidaTropasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVidaTropasLayout.createSequentialGroup()
                .addGroup(panelVidaTropasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVidaTotal)
                    .addComponent(lblcasillasDestruidas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelVidaTropasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpanelP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpanelP2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpanelP3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelConsola.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));

        javax.swing.GroupLayout panelConsolaLayout = new javax.swing.GroupLayout(panelConsola);
        panelConsola.setLayout(panelConsolaLayout);
        panelConsolaLayout.setHorizontalGroup(
            panelConsolaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelConsolaLayout.setVerticalGroup(
            panelConsolaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 68, Short.MAX_VALUE)
        );

        panelCoords.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));

        javax.swing.GroupLayout panelCoordsLayout = new javax.swing.GroupLayout(panelCoords);
        panelCoords.setLayout(panelCoordsLayout);
        panelCoordsLayout.setHorizontalGroup(
            panelCoordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelCoordsLayout.setVerticalGroup(
            panelCoordsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout JuegoLayout = new javax.swing.GroupLayout(Juego);
        Juego.setLayout(JuegoLayout);
        JuegoLayout.setHorizontalGroup(
            JuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JuegoLayout.createSequentialGroup()
                .addGroup(JuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelBitacora, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(panelResultados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TableroJuego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelVidaTropas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(personajes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(panelCoords, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelConsola, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        JuegoLayout.setVerticalGroup(
            JuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JuegoLayout.createSequentialGroup()
                .addGroup(JuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, JuegoLayout.createSequentialGroup()
                        .addComponent(panelBitacora, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelResultados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(JuegoLayout.createSequentialGroup()
                        .addComponent(TableroJuego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelVidaTropas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(personajes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelConsola, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCoords, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(Juego, "card5");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JugarActionPerformed
        // TODO add your handling code here:
        cardLayout = (CardLayout) (getContentPane().getLayout());
        cardLayout.show(getContentPane(), "card4");
    }//GEN-LAST:event_JugarActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_SalirActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void SeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeleccionarActionPerformed
        // TODO add your handling code here:
        int porcentaje = 0;
        if (idsSeleccionados.size() == 3) {
            heroesElegidos = obtenerPersonajesSeleccionados();
            for (Personaje p : heroesElegidos) {
                System.out.println("Nombre: " + p.getNombre());
                porcentaje += p.getPorcentaje();
            }
            if(porcentaje !=  100){
                JOptionPane.showMessageDialog(this, "Los porcentajes de los personajes deben de sumar 100%");
                return;
                
            }
            tablero = new Tablero();
            JPanel jpanel = tablero.crearTablero(heroesElegidos.get(0),heroesElegidos.get(1),heroesElegidos.get(2));
            TableroJuego.setLayout(new java.awt.BorderLayout());
            TableroJuego.add(jpanel, java.awt.BorderLayout.CENTER);
            TableroJuego.revalidate();
            TableroJuego.repaint();
            
            
            
            for(int i = 0; i < 3; i++){
                 
                var iconoOriginal = new ImageIcon(getClass().getResource(heroesElegidos.get(i).getRutaIcon()));
                JLabel lblIcono = new JLabel();
                ImageIcon iconoRedimensionado = new ImageIcon(
                iconoOriginal.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH)
                );
                lblIcono.setIcon(iconoRedimensionado);
                lblIcono.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                
                java.awt.Color fondoNegroTransparente = new java.awt.Color(0, 0, 0, 100);
                panelBitacora.setBackground(fondoNegroTransparente);
                
                
                JPanel panelImagen = new JPanel();
                panelImagen.setOpaque(true);
                panelImagen.setBackground(fondoNegroTransparente);
                panelImagen.setLayout(new javax.swing.BoxLayout(panelImagen, javax.swing.BoxLayout.X_AXIS));
                
                JPanel panelPersonaje = new JPanel();
                panelPersonaje.setOpaque(false);
                panelPersonaje.setLayout(new javax.swing.BoxLayout(panelPersonaje, javax.swing.BoxLayout.Y_AXIS));
                panelPersonaje.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                
                
                JLabel lblHumanidad = new JLabel(heroesElegidos.get(i).getPorcentaje() + "%");
                lblHumanidad.setFont(new java.awt.Font("Segoe UI", 1, 18));
                lblHumanidad.setForeground(Color.WHITE);
                lblHumanidad.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                
                JLabel lblNombre = new JLabel(heroesElegidos.get(i).getNombre());
                lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 18));
                lblNombre.setForeground(new java.awt.Color(158, 110, 33));
                lblNombre.setAlignmentX(JComponent.CENTER_ALIGNMENT); 

                JLabel lblAtaque = new JLabel(heroesElegidos.get(i).getAtaque());
                lblAtaque.setFont(new java.awt.Font("Segoe UI", 1, 12));
                lblAtaque.setForeground(new java.awt.Color(235, 12, 12));
                lblAtaque.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                
                JLabel lblPoder = new JLabel("Poder: " + heroesElegidos.get(i).getPoder());
                lblPoder.setForeground(java.awt.Color.WHITE);
                lblPoder.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                
                JLabel lblResistencia = new JLabel("Resistencia: " + heroesElegidos.get(i).getResistencia());
                lblResistencia.setForeground(java.awt.Color.WHITE);
                lblResistencia.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                
                JLabel lblSanidad = new JLabel("Sanidad: " + heroesElegidos.get(i).getSanidad());
                lblSanidad.setForeground(java.awt.Color.WHITE);
                lblSanidad.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                                       
                panelImagen.add(Box.createHorizontalStrut(25));
                panelImagen.add(lblIcono);
                panelImagen.add(Box.createHorizontalStrut(50));
                panelPersonaje.add(lblHumanidad);
                panelPersonaje.add(lblNombre);
                panelPersonaje.add(lblAtaque);
                panelPersonaje.add(lblPoder);
                panelPersonaje.add(lblResistencia);
                panelPersonaje.add(lblSanidad);
                panelImagen.add(panelPersonaje);
                panelImagen.add(Box.createHorizontalStrut(25));
                
                
                //personajes.add(lblIcono);
                personajes.add(panelImagen);
                
                
            }
            
            vidaTropas();
            
            cardLayout = (CardLayout) (getContentPane().getLayout());
            cardLayout.show(getContentPane(), "card5");
            
            
        }
        
    }//GEN-LAST:event_SeleccionarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Juego().setVisible(true);
            } catch (IOException ex) {
                System.getLogger(Juego.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BuscarPartida;
    private javax.swing.JPanel Juego;
    private javax.swing.JButton Jugar;
    private javax.swing.JPanel Menu;
    private javax.swing.JButton Salir;
    private javax.swing.JPanel SeleccionLuchadores;
    private javax.swing.JButton Seleccionar;
    private javax.swing.JPanel TableroJuego;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jpanelP1;
    private javax.swing.JPanel jpanelP2;
    private javax.swing.JPanel jpanelP3;
    private javax.swing.JPanel jugadoresScroll;
    private javax.swing.JLabel lblDestruidasP1;
    private javax.swing.JLabel lblDestruidasP2;
    private javax.swing.JLabel lblDestruidasP3;
    private javax.swing.JLabel lblVidaTotal;
    private javax.swing.JLabel lblcasillasDestruidas;
    private javax.swing.JLabel lblnombreP1;
    private javax.swing.JLabel lblnombreP2;
    private javax.swing.JLabel lblnombreP3;
    private javax.swing.JLabel lblporcentajeP1;
    private javax.swing.JLabel lblporcentajeP2;
    private javax.swing.JLabel lblporcentajeP3;
    private javax.swing.JPanel luchadores;
    private javax.swing.JPanel panelBitacora;
    private javax.swing.JPanel panelConsola;
    private javax.swing.JPanel panelCoords;
    private javax.swing.JPanel panelResultados;
    private javax.swing.JPanel panelVidaTropas;
    private javax.swing.JPanel personajes;
    // End of variables declaration//GEN-END:variables
}
