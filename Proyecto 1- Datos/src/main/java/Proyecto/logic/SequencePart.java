package Proyecto.logic;

import javax.sound.sampled.AudioInputStream;
import javax.swing.*;
import java.awt.*;

public class SequencePart {
    private Color color;
    private AudioInputStream sound;

    public SequencePart(Color color, AudioInputStream sound) {
        this.color = color;
        this.sound = sound;
    }

    public Color getColor() {
        return color;
    }

    public AudioInputStream getSound() {
        return sound;
    }
}
