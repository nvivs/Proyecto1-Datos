package Proyecto.MVC;

import Proyecto.Util.QueueException;
import Proyecto.logic.Level;
import Proyecto.logic.Score;
import Proyecto.logic.SequencePart;
import Proyecto.logic.Service;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Iterator;

import static java.lang.Thread.sleep;

public class Controller {
    private Model model;
    private View view;
    private Level level;
    private Score score;
    private boolean playerTurn;
    private Iterator<SequencePart> iterator;
    private int tiempoRestante;
    private int tiempoTotal;
    Thread thread;
    JOptionPane pane;
    Boolean running;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        view.setPanel(model);
        this.level = Service.instance().getLevel();
        score = Service.instance().getScore();
        this.playerTurn = false;
        pane = new JOptionPane();

        // Configura el controlador para el botón "Iniciar"
        view.setNewGameButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    reestart();
                    startGame();
                } catch (UnsupportedAudioFileException | QueueException | IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        view.setStartButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startGame();
                } catch (UnsupportedAudioFileException | QueueException | IOException | InterruptedException ex) {
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
                    try {
                        showNextColor();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        view.setCountListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getCount()) {
                    if(model.getCountIndex() > 1){
                        model.reduce();
                    }else{
                        model.setCount(false);
                        model.setCountIndex(3);
                    }
                } else {
                    view.stopCount();
                    model.initColors();
                    view.repaint();
                    running = true;
                    iniciaTemporizador();
                }
                view.repaint();
            }
        });

        view.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int x = evt.getX();
                int y = evt.getY();
                Color colorAtPosition;
                try {
                    colorAtPosition = view.obtenerColorEnPosicion(x, y);
                    check(colorAtPosition);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void startGame() throws UnsupportedAudioFileException, QueueException, IOException, InterruptedException {
        view.desactivaBotones();
        createSequence();

        // Comenzar a mostrar la secuencia
        playerTurn = false;
        view.startTimer();
        showNextColor();
    }

    private void createSequence() throws UnsupportedAudioFileException, QueueException, IOException {
        Service.instance().getSequence().createSequence(level.getLevel());
        iterator = Service.instance().getSequence().getSequence().iterator();
        view.updateLevel(level.getLevel());
        view.updateScore(score.getScore());
        setTiempoRestante();
    }

    private void showNextColor() throws InterruptedException {
//        if(model.getCount()) {
//            view.repaint();
//            if(model.getCountIndex() > 1){
//                model.reduce();
//            }else{
//                model.reset();
//            }
//        }else {
        // Muestra el siguiente color en la secuencia
            model.reset();
            if (iterator.hasNext()) {
                model.changeColor(iterator.next().getColor());
                view.repaint();

                view.updateScore(Service.instance().getScore().getScore());
            } else {
                // La secuencia ha sido mostrada, es el turno del jugador
                model.initColors();
                view.repaint();
                playerTurn = true;
                view.stopTimer();
                view.activaBotones();
                model.setCount(true);
                view.startCount();
            }
        //}
    }

    public void fail(){
        // Reiniciar el modelo y la vista
        detieneTemporizador();
        reestart();
    }

    public void reestart(){
        score.resetScore();
        level.resetLevel();
        model.reset();
    }

    public void win(){
        detieneTemporizador();
        score.updateScore(tiempoRestante, tiempoTotal);
        level.increase();
        model.updateLevel(level.getLevel());
    }

    public void setTiempoRestante(){
        if (level.getLevel() == 1) {
            tiempoRestante = 30;
        } else if (level.getLevel() <= 4) {
            tiempoRestante = 25;
        } else if (level.getLevel() <= 7) {
            tiempoRestante = 20;
        } else if (level.getLevel() <= 10) {
            tiempoRestante = 15;
        } else if (level.getLevel() <= 13) {
            tiempoRestante = 10;
        } else if (level.getLevel() <= 15) {
            tiempoRestante = 5;
        } else if (level.getLevel() > 15) {
            tiempoRestante = 2;
        }
    }

    public void iniciaTemporizador() {
//        thread = new Thread(() -> {
//            for (int i = tiempoRestante; i >= 0; i--) {
//                view.setTiempo(i);
//                tiempoTotal = tiempoRestante - i;
//                try {
//                    sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                if (i == 0) {
//                    JOptionPane.showMessageDialog(view, "TE HAZ QUEDADO SIN TIEMPO", "HAZ PERDIDO", JOptionPane.INFORMATION_MESSAGE);
//                    pane.addComponentListener(new ComponentAdapter() {
//                        @Override
//                        public void componentHidden(ComponentEvent e) {
//                            super.componentHidden(e);
//                            fail();
//                        }
//                    });
//                }
//            }
//        });
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = tiempoRestante; i >= 0; i--) {
                    if(!running){
                        break;
                    }
                    view.setTiempo(i);
                    tiempoTotal = tiempoRestante - i;
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (i == 0) {
                        JOptionPane.showMessageDialog(view, "TE HAZ QUEDADO SIN TIEMPO", "HAZ PERDIDO", JOptionPane.INFORMATION_MESSAGE);
                        pane.addComponentListener(new ComponentAdapter() {
                            @Override
                            public void componentHidden(ComponentEvent e) {
                                super.componentHidden(e);
                                fail();
                            }
                        });
                    }
                }
            }
        });
        thread.start();
    }

    public void detieneTemporizador(){
        running = false;
        view.setTiempo(0);
    }

    public void check(Color color) throws QueueException, UnsupportedAudioFileException, IOException, InterruptedException {
        iterator = Service.instance().getSequence().getSequence().iterator();

        Color expectedColor = iterator.next().getColor();
        if(color.equals(expectedColor)){
            if(!iterator.hasNext()){//terminó de introducir la secuencia correctamente
                win();
            }
        }else{//el color ingresado es incorrecto
            JOptionPane.showMessageDialog(view, "COLOR INCORRECTO", "HAZ PERDIDO", JOptionPane.INFORMATION_MESSAGE);
            fail();
        }
    }

}
