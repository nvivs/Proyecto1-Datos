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
    private static SequencePartSound instance;

    public static SequencePartSound instance() throws UnsupportedAudioFileException, IOException {
        if (instance == null) instance = new SequencePartSound();
        return instance;
    }

    public SequencePartSound() throws UnsupportedAudioFileException, IOException {
         RED = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/Red.wav")));
        BLUE = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/Blue.wav")));
        GREEN = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/Green.wav")));
        YELLOW = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass()
                .getResourceAsStream("/Yellow.wav")));
    }

    public AudioInputStream getSound(int i) {
        return switch (i) {
            case 0 -> RED;
            case 1 -> GREEN;
            case 2 -> YELLOW;
            case 3 -> BLUE;
//            case 4 -> ORANGE;
//            case 5 -> PINK;
//            case 6 -> LIGHTBLUE;
            default -> null;
        };
    }

}
