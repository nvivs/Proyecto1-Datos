package Proyecto;

import Proyecto.MVC.*;
import Proyecto.Util.QueueException;
import Proyecto.data.Data;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class Application {
    public static void main(String[] args) throws UnsupportedAudioFileException, QueueException, IOException, InterruptedException {
//        JFrame window = new JFrame();
//        Model model = new Model();
//        SimonGameView view = new SimonGameView();
//        Controller controller = new Controller(view, model, view.getColors());
//
//        window.setContentPane(view.getPanel());
//        window.setTitle("Simon Dice");
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setSize(585, 550);
//        window.setResizable(true);
//        window.setVisible(true);
//
//        // Agrega un MouseAdapter para manejar las interacciones del usuario
//        window.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                int x = evt.getX();
//                int y = evt.getY();
//                Color colorAtPosition = view.getColorAtPosition(x, y);
//
//                // Llama al controlador para manejar la selección de colores por parte del usuario
//                controller.handleColorSelection(colorAtPosition);
//            }
//        });
//
//        // Agrega un ActionListener para el botón "Iniciar"
//        view.addStartButtonListener(e -> {
//            // Inicia la secuencia del juego
//            controller.playSequence();
//        });

        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);

        view.setVisible(true);
    }
}
