/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.oceanica;

import Cliente.Client;
import Models.Command;
import Models.CommandFactory;
import Models.CommandReady;
import Models.CommandUtil;
import Servidor.Server;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.Border;

public class Juego extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Juego.class.getName());
    
    private Tablero tablero;
    private String nombreCivilización;
    Client cliente;
    
    private CardLayout cardLayout = new CardLayout();
    
    private List<Personaje> todosLosPersonajes;
    private List<Personaje> heroesElegidos;
    
    private final Set<String> idsSeleccionados = new HashSet<>(); 
    private final Map<String, JComponent> componentesSeleccionados = new HashMap<>(); 

    private final Border BORDE_SELECCION = BorderFactory.createLineBorder(new Color(13, 35, 71), 3);
    private final Border BORDE_NORMAL = BorderFactory.createEmptyBorder();
    private JPanelImage miImagen5;
    
    
    public Juego() throws IOException {
        initComponents();
        setResizable(false);
        consola.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                handleCommandInput(evt);
            }
        });
        
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

        String casillasP2 = tablero.casillasDestruidasP2() + " de " + tablero.casillasP2.size() + " casillas";
        String porcentajeP2 = tablero.porcentajeP2() + "%";
        lblporcentajeP2.setText(porcentajeP2);
        lblnombreP2.setText(nombreP2);
        lblDestruidasP2.setText(casillasP2);

        String casillasP3 = tablero.casillasDestruidasP3() + " de " + tablero.casillasP3.size() + " casillas";
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
    
    public void writeMessage(String msg){
        jugadoresConectados.append(msg + "\n");
    }
    
    public void writeBitacora(String msg){
        bitacoraScroll.append(msg + "\n");
    }
    
    public void clearMessages() {
        // Establece el texto como una cadena vacía, borrando todo lo anterior.
        jugadoresConectados.setText(""); 
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
        miImagen5 = new JPanelImage(BuscarPartida, "/imagenes/fondoBuscarPartida.png");
        BuscarPartida.add(miImagen5).repaint();
        
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
    
    public void startGame(){
        cardLayout = (CardLayout) (getContentPane().getLayout());
        cardLayout.show(getContentPane(), "card5");
    }
    
    private void handleCommandInput(java.awt.event.ActionEvent evt) {
        String msg =  consola.getText().trim();
        consola.setText("");
        if (msg.length()>0){
            String args[] = CommandUtil.tokenizerArgs(msg);
            if (args.length > 0){
                Command comando = CommandFactory.getCommand(args);
                if (comando != null){
                    try {
                        cliente.objectSender.writeObject(comando);
                    } catch (IOException ex) {
                        
                    }
                }else{
                    System.out.println("Error: comando desconocido");
                }
            }
        }
    }
    
    public void recibirAtaqueCliente(){
        ReleaseTheKraken ataque1 = new ReleaseTheKraken(100, tablero);
        ataque1.release_the_kraken();
        vidaTropas();
        this.repaint();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Menu = new javax.swing.JPanel();
        Jugar = new javax.swing.JButton();
        Salir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        BuscarPartida = new javax.swing.JPanel();
        btnCrearPartida = new javax.swing.JButton();
        btnBuscarPartida = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        SeleccionLuchadores = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        luchadores = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jugadoresScroll = new javax.swing.JPanel();
        Seleccionar = new javax.swing.JButton();
        Juego = new javax.swing.JPanel();
        panelBitacora = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        bitacoraScroll = new javax.swing.JTextArea();
        panelResultados = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        resultadosScroll = new javax.swing.JScrollPane();
        resultadoAreaScroll = new javax.swing.JTextArea();
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
        consola = new javax.swing.JTextField();
        panelCoords = new javax.swing.JPanel();
        lobby = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jugadoresConectados = new javax.swing.JTextArea();
        listo = new javax.swing.JButton();
        jugadoresListos = new javax.swing.JLabel();

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
                .addContainerGap(463, Short.MAX_VALUE))
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

        BuscarPartida.setMinimumSize(new java.awt.Dimension(1022, 667));
        BuscarPartida.setPreferredSize(new java.awt.Dimension(1022, 667));

        btnCrearPartida.setBackground(new java.awt.Color(6, 71, 12));
        btnCrearPartida.setForeground(new java.awt.Color(255, 255, 255));
        btnCrearPartida.setText("Crear Partida");
        btnCrearPartida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCrearPartidaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCrearPartidaMouseExited(evt);
            }
        });
        btnCrearPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearPartidaActionPerformed(evt);
            }
        });

        btnBuscarPartida.setBackground(new java.awt.Color(6, 71, 12));
        btnBuscarPartida.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarPartida.setText("Buscar Partida");
        btnBuscarPartida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBuscarPartidaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBuscarPartidaMouseExited(evt);
            }
        });
        btnBuscarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPartidaActionPerformed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Impact", 0, 80)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(15, 68, 89));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("OCEANIA");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel1.setOpaque(false);
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 44, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout BuscarPartidaLayout = new javax.swing.GroupLayout(BuscarPartida);
        BuscarPartida.setLayout(BuscarPartidaLayout);
        BuscarPartidaLayout.setHorizontalGroup(
            BuscarPartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuscarPartidaLayout.createSequentialGroup()
                .addGap(450, 450, 450)
                .addGroup(BuscarPartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCrearPartida, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(450, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BuscarPartidaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
            .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscarPartidaLayout.setVerticalGroup(
            BuscarPartidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BuscarPartidaLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(btnCrearPartida)
                .addGap(61, 61, 61)
                .addComponent(btnBuscarPartida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 205, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
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

        bitacoraScroll.setColumns(20);
        bitacoraScroll.setRows(5);
        jScrollPane3.setViewportView(bitacoraScroll);

        javax.swing.GroupLayout panelBitacoraLayout = new javax.swing.GroupLayout(panelBitacora);
        panelBitacora.setLayout(panelBitacoraLayout);
        panelBitacoraLayout.setHorizontalGroup(
            panelBitacoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBitacoraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(128, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelBitacoraLayout.setVerticalGroup(
            panelBitacoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBitacoraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE))
        );

        panelResultados.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Resultado del ataque:");

        resultadoAreaScroll.setColumns(20);
        resultadoAreaScroll.setRows(5);
        resultadosScroll.setViewportView(resultadoAreaScroll);

        javax.swing.GroupLayout panelResultadosLayout = new javax.swing.GroupLayout(panelResultados);
        panelResultados.setLayout(panelResultadosLayout);
        panelResultadosLayout.setHorizontalGroup(
            panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addContainerGap(16, Short.MAX_VALUE))
            .addComponent(resultadosScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelResultadosLayout.setVerticalGroup(
            panelResultadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResultadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultadosScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE))
        );

        personajes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));
        personajes.setOpaque(false);
        personajes.setLayout(new java.awt.GridLayout(3, 2));

        TableroJuego.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));

        javax.swing.GroupLayout TableroJuegoLayout = new javax.swing.GroupLayout(TableroJuego);
        TableroJuego.setLayout(TableroJuegoLayout);
        TableroJuegoLayout.setHorizontalGroup(
            TableroJuegoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
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
                        .addGap(0, 335, Short.MAX_VALUE))))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelConsola.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));

        javax.swing.GroupLayout panelConsolaLayout = new javax.swing.GroupLayout(panelConsola);
        panelConsola.setLayout(panelConsolaLayout);
        panelConsolaLayout.setHorizontalGroup(
            panelConsolaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(consola)
        );
        panelConsolaLayout.setVerticalGroup(
            panelConsolaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(consola, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
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

        lobby.setMinimumSize(new java.awt.Dimension(1022, 667));
        lobby.setPreferredSize(new java.awt.Dimension(1022, 667));

        jugadoresConectados.setColumns(20);
        jugadoresConectados.setRows(5);
        jScrollPane2.setViewportView(jugadoresConectados);

        listo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        listo.setForeground(new java.awt.Color(0, 0, 0));
        listo.setText("Listo");
        listo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        listo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listoActionPerformed(evt);
            }
        });

        jugadoresListos.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jugadoresListos.setForeground(new java.awt.Color(0, 0, 0));
        jugadoresListos.setText("Aqui va la cantidad de jugadores listos xd");

        javax.swing.GroupLayout lobbyLayout = new javax.swing.GroupLayout(lobby);
        lobby.setLayout(lobbyLayout);
        lobbyLayout.setHorizontalGroup(
            lobbyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, lobbyLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(listo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(lobbyLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(lobbyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                    .addComponent(jugadoresListos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(387, Short.MAX_VALUE))
        );
        lobbyLayout.setVerticalGroup(
            lobbyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(lobbyLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jugadoresListos, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 261, Short.MAX_VALUE)
                .addComponent(listo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        getContentPane().add(lobby, "card6");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JugarActionPerformed
        // TODO add your handling code here:
        String mensaje = "Ingrese el nombre de su civilización:";
        String titulo = "Nombre de civilización";
        
        nombreCivilización = JOptionPane.showInputDialog((JFrame)null, mensaje, titulo, JOptionPane.QUESTION_MESSAGE);
        if (nombreCivilización != null && !nombreCivilización.trim().isEmpty()) {
            cardLayout = (CardLayout) (getContentPane().getLayout());
            cardLayout.show(getContentPane(), "card4");
        }
    }//GEN-LAST:event_JugarActionPerformed

    private void SalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_SalirActionPerformed

    private void btnCrearPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearPartidaActionPerformed
        // TODO add your handling code here:
        Server server;
        server = new Server(this);
        
        cliente = new Client(this, nombreCivilización);
        
        cardLayout = (CardLayout) (getContentPane().getLayout());
        cardLayout.show(getContentPane(), "card6");
    }//GEN-LAST:event_btnCrearPartidaActionPerformed

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
            cardLayout.show(getContentPane(), "card3");
            
            
        }
        
    }//GEN-LAST:event_SeleccionarActionPerformed

    private void btnBuscarPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPartidaActionPerformed
        // TODO add your handling code here:
        cliente = new Client(this, nombreCivilización);
        
        cardLayout = (CardLayout) (getContentPane().getLayout());
        cardLayout.show(getContentPane(), "card6");
    }//GEN-LAST:event_btnBuscarPartidaActionPerformed

    private void listoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listoActionPerformed
        // TODO add your handling code here:
        try {
            // Enviar al servidor que este jugador está listo
            CommandReady ready = new CommandReady(this.getTitle()); // el nombre del jugador es el título del frame
            cliente.objectSender.writeObject(ready);

            listo.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Marcado como listo. Esperando a otros jugadores...");

        } catch (IOException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error al enviar estado de 'listo' al servidor.");
        }
    }//GEN-LAST:event_listoActionPerformed

    private void btnCrearPartidaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearPartidaMouseEntered
       btnCrearPartida.setBackground(new Color(10, 110, 19));
    }//GEN-LAST:event_btnCrearPartidaMouseEntered

    private void btnCrearPartidaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCrearPartidaMouseExited
       btnCrearPartida.setBackground(new Color(6,71,12));
    }//GEN-LAST:event_btnCrearPartidaMouseExited

    private void btnBuscarPartidaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarPartidaMouseEntered
       btnBuscarPartida.setBackground(new Color(10, 110, 19));

    }//GEN-LAST:event_btnBuscarPartidaMouseEntered

    private void btnBuscarPartidaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarPartidaMouseExited
      btnBuscarPartida.setBackground(new Color(6,71,12));

    }//GEN-LAST:event_btnBuscarPartidaMouseExited

    public static void showRandomMessageDialog(String message, String title) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        JOptionPane pane = new JOptionPane(
            message,
            JOptionPane.PLAIN_MESSAGE 
        );

        pane.putClientProperty("JComponent.displayProperties", null);

        JDialog dialog = new JDialog((Frame) null, title, true); 

        dialog.setContentPane(pane);

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        dialog.pack(); 

        Random random = new Random();

        int dialogWidth = dialog.getWidth();
        int dialogHeight = dialog.getHeight();

        int randomX = random.nextInt(screenWidth - dialogWidth);
        int randomY = random.nextInt(screenHeight - dialogHeight);

        dialog.setLocation(randomX, randomY);
        dialog.setVisible(true); 
    }
    
    
    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
        lblTitulo.setForeground(new Color(204, 27, 133));
        lblTitulo.setText("MAICOL INUTIL");
        miImagen5.setPath("/imagenes/Maicol.jpeg");
        for(int i = 0; i < 20; i++){
           showRandomMessageDialog("Maicol más inútil", "Maicol tonto"); 
        }
    }//GEN-LAST:event_jPanel1MouseEntered

    private void jPanel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseExited
        lblTitulo.setForeground(new Color(15,68,89));
        lblTitulo.setText("OCEANIA");
        miImagen5.setPath("/imagenes/fondoBuscarPartida.png");
    }//GEN-LAST:event_jPanel1MouseExited

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
    private javax.swing.JTextArea bitacoraScroll;
    private javax.swing.JButton btnBuscarPartida;
    private javax.swing.JButton btnCrearPartida;
    private javax.swing.JTextField consola;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel jpanelP1;
    private javax.swing.JPanel jpanelP2;
    private javax.swing.JPanel jpanelP3;
    private javax.swing.JTextArea jugadoresConectados;
    private javax.swing.JLabel jugadoresListos;
    private javax.swing.JPanel jugadoresScroll;
    private javax.swing.JLabel lblDestruidasP1;
    private javax.swing.JLabel lblDestruidasP2;
    private javax.swing.JLabel lblDestruidasP3;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblVidaTotal;
    private javax.swing.JLabel lblcasillasDestruidas;
    private javax.swing.JLabel lblnombreP1;
    private javax.swing.JLabel lblnombreP2;
    private javax.swing.JLabel lblnombreP3;
    private javax.swing.JLabel lblporcentajeP1;
    private javax.swing.JLabel lblporcentajeP2;
    private javax.swing.JLabel lblporcentajeP3;
    private javax.swing.JButton listo;
    private javax.swing.JPanel lobby;
    private javax.swing.JPanel luchadores;
    private javax.swing.JPanel panelBitacora;
    private javax.swing.JPanel panelConsola;
    private javax.swing.JPanel panelCoords;
    private javax.swing.JPanel panelResultados;
    private javax.swing.JPanel panelVidaTropas;
    private javax.swing.JPanel personajes;
    private javax.swing.JTextArea resultadoAreaScroll;
    private javax.swing.JScrollPane resultadosScroll;
    // End of variables declaration//GEN-END:variables
}
