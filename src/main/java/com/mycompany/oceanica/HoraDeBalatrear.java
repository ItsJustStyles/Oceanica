package com.mycompany.oceanica;

import java.util.Random;

/**
 *
 * @author lacay
 */
public class HoraDeBalatrear extends Ataque {
    Juego refFrame;
    private String registro;

    public HoraDeBalatrear(int dano, Tablero tablero, Juego refFrame, String registro) {
        this.refFrame = refFrame;
        this.registro = registro;
        super(dano, tablero);
    }

    @Override
    public void aplicarDano(Casilla celda) { }

    public void BluePrint() {
        ReproductorSonido sonido = new ReproductorSonido("/sonidos/explosion1.wav", refFrame);
        sonido.play();
        int x = 1;
        int y = 1;

        while (x <= 20 && y <= 30) {
            tablero.recibirDanoLocacion(x, y, 100,registro);
            x++;
            y++;
        }
    }

    public void Balatrito() {
        ReproductorSonido sonido = new ReproductorSonido("/sonidos/win.wav", refFrame);
        sonido.play();
        Random random = new Random();
        for (int i = 0; i < 40; i++) {
            Casilla c = tablero.casillas.get(random.nextInt(tablero.casillas.size()));
            tablero.recibirDanoLocacion(c.getX(), c.getY(), 100,registro);
        }
    }

    public void Cavendish() {
        ReproductorSonido sonido = new ReproductorSonido("/sonidos/multihit2.wav", refFrame); //hay q cambiarlo o hacerle un loop abajo
        sonido.play();
        int[] filas = {5, 10, 15};

        for (int f : filas) {
            for (int y = 1; y <= 30; y++) {
                tablero.recibirDanoLocacion(f, y, 100,registro);
            }
        }
    }
}
