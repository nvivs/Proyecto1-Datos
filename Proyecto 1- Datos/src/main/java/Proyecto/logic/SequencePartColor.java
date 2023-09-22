package Proyecto.logic;

import javax.swing.*;
import java.awt.*;

public class SequencePartColor {
    private final Color RED;
    private final Color BLUE;
    private final Color GREEN;
    private final Color YELLOW;
    private final Color ORANGE;
    private final Color PINK;
    private final Color WHITE;
    private static SequencePartColor instance;

    public static SequencePartColor instance(){
        if (instance == null) instance = new SequencePartColor();
        return instance;
    }

    public SequencePartColor() {
        RED = new Color(103, 21, 21, 255);
        BLUE = new Color(16, 26, 82, 255);
        GREEN = new Color(30, 63, 15, 255);
        YELLOW = new Color(119, 89, 36, 255);
        ORANGE = new Color(119, 65, 36, 255);
        PINK = new Color(107, 31, 89, 255);
        WHITE = new Color(129, 129, 129, 255);
    }

    public Color getColor(int i){
        return switch (i) {
            case 1 -> RED;
            case 2 -> BLUE;
            case 3 -> GREEN;
            case 4 -> YELLOW;
            case 5 -> ORANGE;
            case 6 -> PINK;
            case 7 -> WHITE;
            default -> null;
        };
    }

    public Color getRED() {
        return RED;
    }

    public Color getBLUE() {
        return BLUE;
    }

    public Color getGREEN() {
        return GREEN;
    }

    public Color getYELLOW() {
        return YELLOW;
    }

    public Color getORANGE() {
        return ORANGE;
    }

    public Color getPINK() {
        return PINK;
    }

    public Color getWHITE() {
        return WHITE;
    }
}
