package Proyecto;

import Proyecto.MVC.Controller;
import Proyecto.MVC.View;

import javax.swing.*;

public class Application {
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception ignored) {};

        View view = new View();
        Controller controller = new Controller(view.getModel(), view);

        view.setVisible(true);
    }
}
