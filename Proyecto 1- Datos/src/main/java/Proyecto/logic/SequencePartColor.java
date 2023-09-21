package Proyecto.logic;

import javax.swing.*;
import java.awt.*;

public class SequencePartColor {
    private final ImageIcon RED;
    private final ImageIcon BLUE;
    private final ImageIcon GREEN;
    private final ImageIcon YELLOW;
    private static SequencePartColor instance;

    public static SequencePartColor instance(){
        if (instance == null) instance = new SequencePartColor();
        return instance;
    }

    public SequencePartColor() {
        RED = new ImageIcon("Proyecto 1- Datos/src/main/resources/rojo.png");
        BLUE = new ImageIcon("Proyecto 1- Datos/src/main/resources/azul.png");
        GREEN = new ImageIcon("Proyecto 1- Datos/src/main/resources/verde.png");
        YELLOW = new ImageIcon("Proyecto 1- Datos/src/main/resources/amarillo.png");
    }

    public ImageIcon getColor(int i){
        return switch (i) {
            case 1 -> RED;
            case 2 -> BLUE;
            case 3 -> GREEN;
            case 4 -> YELLOW;
            default -> null;
        };
    }

    public ImageIcon getRED() {
        return RED;
    }

    public ImageIcon getBLUE() {
        return BLUE;
    }

    public ImageIcon getGREEN() {
        return GREEN;
    }

    public ImageIcon getYELLOW() {
        return YELLOW;
    }
}
