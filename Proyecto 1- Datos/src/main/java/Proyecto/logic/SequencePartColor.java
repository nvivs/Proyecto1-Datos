package Proyecto.logic;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SequencePartColor {
    private final Color RED;
    private final Color BLUE;
    private final Color GREEN;
    private final Color YELLOW;
    private final Color ORANGE;
    private final Color PINK;
    private final Color LIGTHBLUE;
    private static SequencePartColor instance;
    Map<String, Color> codigos = new HashMap<>();

    public static SequencePartColor instance(){
        if (instance == null) instance = new SequencePartColor();
        return instance;
    }

    public SequencePartColor() {
        RED = new Color(148, 33, 33, 255);
        BLUE = new Color(16, 26, 82, 255);
        GREEN = new Color(30, 63, 15, 255);
        YELLOW = new Color(119, 89, 36, 255);
        ORANGE = new Color(148, 80, 42, 255);
        PINK = new Color(140, 26, 113, 255);
        LIGTHBLUE = new Color(2, 92, 98, 255);
        codigos.put("RED", RED);
        codigos.put("BLUE", BLUE);
        codigos.put("GREEN", GREEN);
        codigos.put("YELLOW", YELLOW);
        codigos.put("ORANGE", ORANGE);
        codigos.put("PINK", PINK);
        codigos.put("LIGTHBLUE", LIGTHBLUE);
    }

    public Color getColor(String cod) {
        if (codigos.containsKey(cod)) {
            return codigos.get(cod);
        } else {
            throw new IllegalArgumentException("Variable no encontrada: " + cod);
        }
    }

    public Color getColor(int i){
        return switch (i) {
            case 0 -> RED;
            case 1 -> GREEN;
            case 2 -> YELLOW;
            case 3 -> BLUE;
            case 4 -> ORANGE;
            case 5 -> PINK;
            case 6 -> LIGTHBLUE;
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

    public Color getLIGTHBLUE() {
        return LIGTHBLUE;
    }
}
