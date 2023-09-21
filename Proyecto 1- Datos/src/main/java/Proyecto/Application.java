package Proyecto;

import Proyecto.MVC.View;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        window = new JFrame();
        window.setContentPane(new JTabbedPane());
        View simulador1 = new View();
        simulador1.setVisible(true);
        //simulador1.iniciarSecuencia();
    }

    public static JFrame window;
}
