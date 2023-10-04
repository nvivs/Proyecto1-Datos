package Proyecto.logic;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

public class SequencePart {
    private Color color;
    private Clip sound;

    public SequencePart(Color color, Clip sound) {
        this.color = color;
        this.sound = sound;
    }

    public Color getColor() {
        return color;
    }

    public Clip getSound() {
        return sound;
    }
}
