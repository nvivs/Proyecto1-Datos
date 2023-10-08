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
import Proyecto.Util.Configuration;
import javax.swing.*;

public class Application {

    private static Configuration configuration;

    public static void main(String[] args){
        configuration = Configuration.getInstance();
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception ignored) {};

        View view = new View();
        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller(view.getModel(), view, configuration);
        });

        view.setVisible(true);
    }
}
