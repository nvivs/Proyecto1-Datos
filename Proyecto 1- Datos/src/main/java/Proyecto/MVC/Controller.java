package Proyecto.MVC;

import Proyecto.Util.QueueException;
import Proyecto.data.Data;
import Proyecto.logic.Service;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Controller {
    private Model model;
    private View view;
    private int currentLevel;
    private boolean playerTurn;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        view.setPanel(model);
        this.currentLevel = 1;
        this.playerTurn = false;

        // Configura el controlador para el botón "Iniciar"
        view.setStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startGame();
                } catch (UnsupportedAudioFileException | QueueException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Configura el controlador para el temporizador
        view.setTimerListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerTurn) {
                    playerTurn = false;
                    view.stopTimer();
                } else {
                    showNextColor();
                }
            }
        });
    }

    private void startGame() throws UnsupportedAudioFileException, QueueException, IOException {
        // Reiniciar el modelo y la vista
        model.reset();
        Service.instance().getSequence().createSequence(currentLevel);
        model.setSecuencia(Service.instance().getSequence());

        Service.instance().getScore().resetScore();
        view.updateLevel(currentLevel);
        view.updateScore(Service.instance().getScore().getScore());

        // Comenzar a mostrar la secuencia
        playerTurn = false;
        view.startTimer();
        showNextColor();
    }

    private void showNextColor() {
        if (playerTurn) {
            // Turno del jugador
            // Configura la lógica para que el jugador seleccione colores y comprueba si son correctos
            // Luego, actualiza el modelo y la vista según el resultado
        } else {
            // Muestra el siguiente color en la secuencia
            if (!Service.instance().getSequence().getSequence().isEmpty()) {
                Service.instance().getSequence().getSequence().dequeue();
                view.startTimer(); // Reiniciar temporizador para mostrar el color
                view.updateScore(Service.instance().getScore().getScore());
            } else {
                // La secuencia ha sido mostrada, es el turno del jugador
                playerTurn = true;
                view.stopTimer();
            }
        }
    }

}
