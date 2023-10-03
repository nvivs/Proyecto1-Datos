package Proyecto.logic;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Objects;

public class SequencePartSound {
    private final AudioInputStream RED;
    private final AudioInputStream BLUE;
    private final AudioInputStream GREEN;
    private final AudioInputStream YELLOW;
    private final AudioInputStream ORANGE;
    private final AudioInputStream PINK;
    private final AudioInputStream LIGHTBLUE;
    private final AudioInputStream WIN;
    private final AudioInputStream LOOSE;
    private static SequencePartSound instance;

    public static SequencePartSound instance() throws UnsupportedAudioFileException, IOException {
        if (instance == null) instance = new SequencePartSound();
        return instance;
    }

    public SequencePartSound() throws UnsupportedAudioFileException, IOException {
         RED = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/red.wav")));
        BLUE = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/blue.wav")));
        GREEN = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/green.wav")));
        YELLOW = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/yellow.wav")));
        ORANGE = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/orange.wav")));
        PINK = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/pink.wav")));
        LIGHTBLUE = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/lightblue.wav")));
        WIN = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/win.wav")));
        LOOSE = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/loose.wav")));
    }

    public AudioInputStream getSound(int i) {
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
