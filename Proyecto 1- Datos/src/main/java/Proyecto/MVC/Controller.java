package Proyecto.MVC;

import Proyecto.Util.QueueException;
import Proyecto.logic.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
    private boolean playerTurn;
    private Iterator<SequencePart> iterator;
    private int totalTime;
    private int timeSpend;
    Thread thread;
    JOptionPane pane;
    Boolean running;
    Icon check = new ImageIcon("/check.png");
    Icon x = new ImageIcon("/x.png");
    SequencePart part;
    private int reproductionTime;

    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
        model.init(Service.instance().getLevel(),
            Service.instance().getScores(), Service.instance().getScore());
        totalTime = Service.instance().getTotalTime();
        reproductionTime = Service.instance().getReproductionTime();
        this.view.setModel(model);
        this.view.setController(this);
        this.playerTurn = false;
        pane = new JOptionPane();
       this.view.setup();
    }

    public int getReproductionTime() {
        return reproductionTime -= 100;
    }

    public Color cambiaColorClickeado(Color colorAtPosition){
        if (colorAtPosition.equals(model.getColors()[0])) {
            colorAtPosition = SequencePartColor.instance().getColor("RED");
            model.changeColor(colorAtPosition);
        } else if (colorAtPosition.equals(model.getColors()[1])) {
            colorAtPosition = SequencePartColor.instance().getColor("GREEN");
            model.changeColor(colorAtPosition);
        } else if (colorAtPosition.equals(model.getColors()[2])) {
            colorAtPosition = SequencePartColor.instance().getColor("YELLOW");
            model.changeColor(colorAtPosition);
        } else if (colorAtPosition.equals(model.getColors()[3])) {
            colorAtPosition = SequencePartColor.instance().getColor("BLUE");
            model.changeColor(colorAtPosition);
        } else if (colorAtPosition.equals(model.getColors()[4])) {
            colorAtPosition = SequencePartColor.instance().getColor("ORANGE");
            model.changeColor(colorAtPosition);
        } else if (colorAtPosition.equals(model.getColors()[5])) {
            colorAtPosition = SequencePartColor.instance().getColor("PINK");
            model.changeColor(colorAtPosition);
        } else if (colorAtPosition.equals(model.getColors()[6])) {
            colorAtPosition = SequencePartColor.instance().getColor("LIGHTBLUE");
            model.changeColor(colorAtPosition);
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

    public void startGame() throws UnsupportedAudioFileException, QueueException, IOException, InterruptedException, LineUnavailableException {
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

    private void createSequence() throws UnsupportedAudioFileException, QueueException, IOException, LineUnavailableException {
        Service.instance().createSequence();
        iterator = Sequence.instance().getSequence().iterator();
        setTiempoRestante();
    }

    public void sound(int i){
        try {
            if(i == 0) {
                part.getSound().setMicrosecondPosition(0);
                part.getSound().start();
            }else{
                SequencePartSound.instance().getSound(i).setMicrosecondPosition(0);
                SequencePartSound.instance().getSound(i).start();
            }
            Thread.sleep(SequencePartSound.instance().getSound(i).getMicrosecondLength() / 750);
        } catch (InterruptedException | UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
    }

    public void showNextColor() throws InterruptedException {
        // Muestra el siguiente color en la secuencia
        model.format();
        if (iterator.hasNext()) {
            part = iterator.next();
            model.changeColor(part.getColor());
            view.repaint();
        } else {
            // La secuencia ha sido mostrada, es el turno del jugador
            model.initColors();
            view.repaint();
            playerTurn = true;
            view.stopTimer();
            model.setCount(true);
            view.startCount();
            iterator = Sequence.instance().getSequence().iterator();
        }
    }

    public void reestart(){
        detieneTemporizador();
        Service.instance().updateScores();
        model.setMaxScore(Service.instance().getScores());
        view.updateMejoresPuntuaciones(model.getMaxScore());
        model.reset(Service.instance().resetScore(), Service.instance().resetLevel());
        view.updateLevel(model.getLevel().getLevel());
        view.updateScore(model.getScore().getScore());
        view.activaStart();
        playerTurn = false;
    }

    public void exit(){
        Service.instance().stop();
    }

    public void win(){
        detieneTemporizador();
        model.setScore(Service.instance().updateScore(totalTime, timeSpend));
        model.setLevel(Service.instance().increaseLevel());
        model.updateTam();
        view.updateLevel(model.getLevel().getLevel());
        view.updateScore(model.getScore().getScore());
        view.activaStart();
        playerTurn = false;
    }

    public void setTiempoRestante(){
        totalTime--;
    }

    public void iniciaTemporizador() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = totalTime; i >= 0; i--) {
                    if(!running){
                        break;
                    }
                    view.setTiempo(i);
                    timeSpend = totalTime - i;
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (i == 0) {
                        sound(8);
                        JOptionPane.showMessageDialog(view, "TE HAS QUEDADO SIN TIEMPO", "HAS PERDIDO", JOptionPane.ERROR_MESSAGE, x);
                        pane.addComponentListener(new ComponentAdapter() {
                            @Override
                            public void componentHidden(ComponentEvent e) {
                                super.componentHidden(e);
                                reestart();
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
            if (!iterator.hasNext()) {
                // terminó de introducir la secuencia correctamente
                detieneTemporizador();
                sound(7);
                JOptionPane.showMessageDialog(view, "FELICIDADES!", "HAS GANADO", JOptionPane.INFORMATION_MESSAGE, check);
                win();
            }
        }else{//el color ingresado es incorrecto
            sound(8);
            JOptionPane.showMessageDialog(view, "COLOR INCORRECTO", "HAS PERDIDO", JOptionPane.ERROR_MESSAGE, x);
            reestart();
        }
    }
}
