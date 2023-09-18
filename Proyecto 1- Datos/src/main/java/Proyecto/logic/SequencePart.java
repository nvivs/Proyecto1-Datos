package Proyecto.logic;

import javax.sound.sampled.AudioInputStream;
import java.awt.*;

public class SequencePart {
    private Color color;
    private AudioInputStream sound;

    public SequencePart(Color color, AudioInputStream sound) {
        this.color = color;
        this.sound = sound;
    }

    public SequencePart() {
        new SequencePart(null, null);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public AudioInputStream getSound() {
        return sound;
    }

    public void setSound(AudioInputStream sound) {
        this.sound = sound;
    }
}
