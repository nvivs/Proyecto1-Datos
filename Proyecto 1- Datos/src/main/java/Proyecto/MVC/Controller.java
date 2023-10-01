package Proyecto.MVC;
import Proyecto.Util.QueueException;
import Proyecto.logic.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Iterator;
import static java.lang.Thread.sleep;

public class Controller {
    private Model model;
    private View view;
    //private Level level;
    //private Score score;
    private boolean playerTurn;
    private Iterator<SequencePart> iterator;
    private int tiempoRestante;
    private int tiempoTotal;
    Thread thread;
    JOptionPane pane;
    Boolean running;
    Icon check = new ImageIcon("/src/main/resources/check.png");
    Icon x = new ImageIcon("/src/main/resources/x.png");

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        model.init(Service.instance().getLevel(),
                Service.instance().getScores());
        view.setModel(model);
        view.setController(this);
        //this.level = Service.instance().getLevel();
        //score = Service.instance().getScore();
        this.playerTurn = false;
        pane = new JOptionPane();
        view.activaNewGame();
        view.activaStart();
    }

    public Color cambiaColorClickeado(Color colorAtPosition){
        if (colorAtPosition.equals(model.getColors()[0])) {
            colorAtPosition = SequencePartColor.instance().getColor("RED");
            cambiaColorClickeado(colorAtPosition);
        } else if (colorAtPosition.equals(model.getColors()[1])) {
            colorAtPosition = SequencePartColor.instance().getColor("GREEN");
            cambiaColorClickeado(colorAtPosition);
        } else if (colorAtPosition.equals(model.getColors()[2])) {
            colorAtPosition = SequencePartColor.instance().getColor("YELLOW");
            cambiaColorClickeado(colorAtPosition);
        } else if (colorAtPosition.equals(model.getColors()[3])) {
            colorAtPosition = SequencePartColor.instance().getColor("BLUE");
            cambiaColorClickeado(colorAtPosition);
        } else if (colorAtPosition.equals(model.getColors()[4])) {
            colorAtPosition = SequencePartColor.instance().getColor("ORANGE");
            cambiaColorClickeado(colorAtPosition);
        } else if (colorAtPosition.equals(model.getColors()[5])) {
            colorAtPosition = SequencePartColor.instance().getColor("PINK");
            cambiaColorClickeado(colorAtPosition);
        } else if (colorAtPosition.equals(model.getColors()[6])) {
            colorAtPosition = SequencePartColor.instance().getColor("LIGHTBLUE");
            cambiaColorClickeado(colorAtPosition);
        }
        model.changeColor(colorAtPosition);
        view.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                model.initColors();
            }
        });
        return colorAtPosition;
    }

    public void startGame() throws UnsupportedAudioFileException, QueueException, IOException, InterruptedException {
        view.desactivaStart();
        createSequence();

        // Comenzar a mostrar la secuencia
        playerTurn = false;
        view.startTimer();
        showNextColor();
    }

    public boolean getPlayerTurn(){
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public Boolean getRunning() {
        return running;
    }

    private void createSequence() throws UnsupportedAudioFileException, QueueException, IOException {
        Service.instance().getSequence().createSequence(model.getLevel().getLevel());
        iterator = Service.instance().getSequence().getSequence().iterator();
        setTiempoRestante();
    }

    public void showNextColor() throws InterruptedException {
        // Muestra el siguiente color en la secuencia
        model.format();
        if (iterator.hasNext()) {
            SequencePart part = iterator.next();
            model.changeColor(part.getColor());
            view.repaint();

//            try (Clip clip = AudioSystem.getClip()) {
//                clip.open(part.getSound());
//
//                clip.start();
//                Thread.sleep(clip.getMicrosecondLength() / 1_000);
//            } catch (IOException
//                     | InterruptedException
//                     | LineUnavailableException ex) {
//                System.err.printf("Excepción: '%s'%n", ex.getMessage());
//            }

        } else {
            // La secuencia ha sido mostrada, es el turno del jugador
            model.initColors();
            view.repaint();
            playerTurn = true;
            view.stopTimer();
            model.setCount(true);
            view.startCount();
            iterator = Service.instance().getSequence().getSequence().iterator();
        }
    }

    public void fail(){
        // Reiniciar el modelo y la vista
        detieneTemporizador();
        Service.instance().updateScores(model.getScore());
        model.setMaxScore(Service.instance().getScores());
        view.updateMejoresPuntuaciones(model.getMaxScore());
        reestart();
    }

    public void reestart(){
        detieneTemporizador();
        model.resetScore();
        model.resetLevel();
        model.reset();
        view.updateLevel(model.getLevel().getLevel());
        view.updateScore(model.getScore().getScore());
        view.activaStart();
        playerTurn = false;
    }

    public void win(){
        detieneTemporizador();
        model.updateScore(tiempoRestante, tiempoTotal);
        model.increaseLevel();
        model.updateTam();
        view.updateLevel(model.getLevel().getLevel());
        view.updateScore(model.getScore().getScore());
        view.activaStart();
        playerTurn = false;
    }

    public void setTiempoRestante(){
        if (model.getLevel().getLevel() == 1) {
            tiempoRestante = 30;
        } else if (model.getLevel().getLevel() <= 4) {
            tiempoRestante = 25;
        } else if (model.getLevel().getLevel() <= 7) {
            tiempoRestante = 20;
        } else if (model.getLevel().getLevel() <= 10) {
            tiempoRestante = 15;
        } else if (model.getLevel().getLevel() <= 13) {
            tiempoRestante = 10;
        } else if (model.getLevel().getLevel() <= 15) {
            tiempoRestante = 5;
        } else if (model.getLevel().getLevel() > 15) {
            tiempoRestante = 2;
        }
    }

    public void iniciaTemporizador() {
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
                        JOptionPane.showMessageDialog(view, "TE HAS QUEDADO SIN TIEMPO", "HAS PERDIDO", JOptionPane.ERROR_MESSAGE, x);
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

    public void check(Color color){
        Color expectedColor = iterator.next().getColor();
        Color c = null;
        if(expectedColor.equals(SequencePartColor.instance().getColor("RED"))){
            c = model.getColors()[0];
        }else if(expectedColor.equals(SequencePartColor.instance().getColor("GREEN"))){
            c = model.getColors()[1];
        }else if(expectedColor.equals(SequencePartColor.instance().getColor("YELLOW"))){
            c = model.getColors()[2];
        }else if(expectedColor.equals(SequencePartColor.instance().getColor("BLUE"))){
            c = model.getColors()[3];
        }else if(expectedColor.equals(SequencePartColor.instance().getColor("ORANGE"))){
            c = model.getColors()[4];
        }else if(expectedColor.equals(SequencePartColor.instance().getColor("PINK"))){
            c = model.getColors()[5];
        }else if(expectedColor.equals(SequencePartColor.instance().getColor("LIGHTBLUE"))){
            c = model.getColors()[6];
        }

        if(color.equals(c)){
            if(!iterator.hasNext()){//terminó de introducir la secuencia correctamente
                detieneTemporizador();
                JOptionPane.showMessageDialog(view, "FELICIDADES!", "HAS GANADO", JOptionPane.INFORMATION_MESSAGE, check);
                win();
            }
        }else{//el color ingresado es incorrecto
            JOptionPane.showMessageDialog(view, "COLOR INCORRECTO", "HAS PERDIDO", JOptionPane.ERROR_MESSAGE, x);
            fail();
        }
    }

}
