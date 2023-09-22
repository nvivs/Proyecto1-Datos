package Proyecto;

import Proyecto.MVC.Controller;
import Proyecto.MVC.Model;
import Proyecto.MVC.View;
import Proyecto.Util.QueueException;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Application {
    public static void main(String[] args) throws UnsupportedAudioFileException, QueueException, IOException, InterruptedException {
        window = new JFrame();
        window.setContentPane(new JTabbedPane());

        Model model = new Model();
        View view = new View();
        controller = new Controller(view, model, view.getColors());

        window.getContentPane().add("Simon dice", view.getPanel());
        window.setTitle("Simon Dice");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(503, 530);
        window.setResizable(true);
        window.setLayout(new GridLayout(2, 2));
        window.setVisible(true);
    }

    public static JFrame window;
    public static Controller controller;
}
