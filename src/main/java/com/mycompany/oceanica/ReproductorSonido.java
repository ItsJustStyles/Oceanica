package com.mycompany.oceanica;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author lacay
 */
public class ReproductorSonido {
    private Clip clip;
    
    public ReproductorSonido(String rutaArchivo) 
            throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        
        File archivoAudio = new File(rutaArchivo);
        AudioInputStream flujoAudio = AudioSystem.getAudioInputStream(archivoAudio);
        
        this.clip = AudioSystem.getClip();
        this.clip.open(flujoAudio);
    }

    public void reproducir() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void detener() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
    
    public boolean estaCorriendo() {
        return clip != null && clip.isRunning();
    }
}
