/*
 *  Derek Rojas Mendoza
 *  604740973
 *  Nicole
 *
 *  Julian
 *
 *  Universidad Nacional de Costa Rica
 *  Escuela de informatica
 *
 *  Proyecto 1
 *  Fecha de entrega: 08/10/2023
 */
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
