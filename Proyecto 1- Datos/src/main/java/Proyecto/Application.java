package Proyecto;

import Proyecto.MVC.View;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        window = new JFrame();
        window.setContentPane(new JTabbedPane());

        View view = new View();
        window.getContentPane().add(view.getPanel());

        window.setSize(900,450);
        window.setResizable(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setTitle("Prueba");
        window.setVisible(true);
    }

    public static JFrame window;
}
