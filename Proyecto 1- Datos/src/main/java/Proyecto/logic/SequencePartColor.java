package Proyecto.logic;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SequencePartColor<T>  {
    private final Color RED;
    private final Color BLUE;
    private final Color GREEN;
    private final Color YELLOW;
    private final Color ORANGE;
    private final Color PINK;
    private final Color LIGHTBLUE;
    private static SequencePartColor instance;
    Map<String, Color> codigos = new HashMap<>();

    public static SequencePartColor instance(){
        if (instance == null) instance = new SequencePartColor();
        return instance;
    }

    public SequencePartColor() {
        RED = new Color(148, 33, 33, 255);
        BLUE = new Color(27, 44, 143, 255);
        GREEN = new Color(63, 136, 29, 255);
        YELLOW = new Color(180, 172, 35);
        ORANGE = new Color(176, 89, 36, 255);
        PINK = new Color(168, 28, 138, 255);
        LIGHTBLUE = new Color(17, 132, 141, 255);
        codigos.put("RED", RED);
        codigos.put("BLUE", BLUE);
        codigos.put("GREEN", GREEN);
        codigos.put("YELLOW", YELLOW);
        codigos.put("ORANGE", ORANGE);
        codigos.put("PINK", PINK);
        codigos.put("LIGHTBLUE", LIGHTBLUE);
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
            case 6 -> LIGHTBLUE;
            default -> null;
        };
    }
}
