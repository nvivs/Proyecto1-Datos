package Proyecto.logic;

import java.awt.*;

public class SequencePartColor {
    private final Color RED;
    private final Color BLUE;
    private final Color GREEN;
    private final Color YELLOW;
    private static SequencePartColor instance;

    public static SequencePartColor instance(){
        if (instance == null) instance = new SequencePartColor();
        return instance;
    }

    public SequencePartColor() {
        RED = Color.RED;
        BLUE = Color.BLUE;
        GREEN = Color.GREEN;
        YELLOW = Color.YELLOW;
    }

    public Color getColor(int i){
        return switch (i) {
            case 1 -> RED;
            case 2 -> BLUE;
            case 3 -> GREEN;
            case 4 -> YELLOW;
            default -> null;
        };
    }
}
