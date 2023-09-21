package Proyecto.logic;

import javax.sound.sampled.AudioInputStream;
import javax.swing.*;
import java.awt.*;

public class SequencePart {
    private ImageIcon color;
    private AudioInputStream sound;

    public SequencePart(ImageIcon color, AudioInputStream sound) {
        this.color = color;
        this.sound = sound;
    }

    public SequencePart() {
        new SequencePart(null, null);
    }

    public ImageIcon getColor() {
        return color;
    }

    public void setColor(ImageIcon color) {
        this.color = color;
    }

    public AudioInputStream getSound() {
        return sound;
    }

    public void setSound(AudioInputStream sound) {
        this.sound = sound;
    }
}
