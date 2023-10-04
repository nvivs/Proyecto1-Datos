package Proyecto.logic;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class SequencePartSound {
    private final Clip RED;
    private final Clip BLUE;
    private final Clip GREEN;
    private final Clip YELLOW;
    private final Clip ORANGE;
    private final Clip PINK;
    private final Clip LIGHTBLUE;
    private final Clip WIN;
    private final Clip LOOSE;
    private static SequencePartSound instance;

    public static SequencePartSound instance() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (instance == null) instance = new SequencePartSound();
        return instance;
    }

    public SequencePartSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        RED = AudioSystem.getClip();
        BLUE = AudioSystem.getClip();
        GREEN = AudioSystem.getClip();
        YELLOW = AudioSystem.getClip();
        ORANGE = AudioSystem.getClip();
        PINK = AudioSystem.getClip();
        LIGHTBLUE = AudioSystem.getClip();
        WIN = AudioSystem.getClip();
        LOOSE = AudioSystem.getClip();

        RED.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/red.wav"))));
        BLUE.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/blue.wav"))));
        GREEN.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/green.wav"))));
        YELLOW.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/yellow.wav"))));
        ORANGE.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/orange.wav"))));
        PINK.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/pink.wav"))));
        LIGHTBLUE.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/lightblue.wav"))));
        WIN.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/win.wav"))));
        LOOSE.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/loose.wav"))));
    }

    public Clip getSound(int i) {
        return switch (i) {
            case 0 -> RED;
            case 1 -> GREEN;
            case 2 -> YELLOW;
            case 3 -> BLUE;
            case 4 -> ORANGE;
            case 5 -> PINK;
            case 6 -> LIGHTBLUE;
            case 7 -> WIN;
            case 8 -> LOOSE;
            default -> null;
        };
    }

}
