package Proyecto.logic;

import javax.sound.sampled.Clip;
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
