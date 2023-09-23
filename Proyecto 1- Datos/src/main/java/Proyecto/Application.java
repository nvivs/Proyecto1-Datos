package Proyecto;

import Proyecto.MVC.Controller;
import Proyecto.MVC.Model;
import Proyecto.MVC.View;
import Proyecto.Util.QueueException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Application {
    public static void main(String[] args) throws UnsupportedAudioFileException, QueueException, IOException, InterruptedException {
        window = new JFrame();

        Model model = new Model();
        View view = new View();
        controller = new Controller(view, model, view.getColors());

        window.setContentPane(view.getPanel());
        window.setTitle("Simon Dice");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(503, 530);
        window.setResizable(true);
        window.setVisible(true);

//        window.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                int x = e.getX();
//                int y = e.getY();
//                Color colorEnPosicion = view.obtenerColorEnPosicion(x, y);
//
//                for (int i = 0; i < view.COLORS.length; i++) {
//                    if (colorEnPosicion.equals(view.COLORS[i])) {
//                        System.out.println(view.COLORS[i]);
//                       //view.check(view.COLORS[i]);
//                    }
//                }
//            }
//        });
    }

    public static JFrame window;
    public static Controller controller;
}
