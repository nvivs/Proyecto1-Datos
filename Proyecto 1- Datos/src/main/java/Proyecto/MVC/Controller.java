package Proyecto.MVC;
import Proyecto.Util.PathUtils;
import Proyecto.Util.QueueException;
import Proyecto.data.Configuration;
import Proyecto.logic.*;
import jakarta.xml.bind.JAXBException;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import static java.lang.Thread.sleep;

public class Controller {
    private Model model;
    private View view;
    private boolean playerTurn;
    private Iterator<SequencePart> iterator;
    private int totalTime;
//   Configuration configuration;
    private int timeSpend;
    Thread thread;
    JOptionPane pane;
    Boolean running;
    Icon check = new ImageIcon("/src/main/resources/check.png");
    Icon x = new ImageIcon("/src/main/resources/x.png");
    SequencePart part;

    public Controller(Model model, View view) //throws JAXBException
     {
        this.model = model;
        this.view = view;
        model.init(Service.instance().getLevel(),
                Service.instance().getScores());
        view.setModel(model);
        view.setController(this);
        this.playerTurn = false;
        pane = new JOptionPane();
        view.activaNewGame();
        view.activaStart();
        //CONFIGURATION FILE
       /* try{
            Configuration configuration = PathUtils.loadConfiguration("Configuration.xml");

        }catch (JAXBException e){
            //e.printStackTrace();
            List<Integer> defaultTimes = new ArrayList<>();
            int defaultTime = 30; // Cambia 30 al valor deseado
            int max = model.getLevel().getLevel();
            for (int i = 0; i < max; i++) {
                defaultTimes.add(defaultTime);
            }

            configuration = new Configuration();
            configuration.setLevelTimes(defaultTimes);

            // Guarda la configuración en un archivo XML
            PathUtils.saveConfiguration("Configuration.xml", configuration);
        }
        List<Integer> levelTimes = configuration.getLevelTimes();
        int timeForCurrentLevel = levelTimes.get(model.getLevel().getLevel() - 1);

        */
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
        Service.instance().getSequence().createSequence(model.getLevel().getLevel());
        iterator = Service.instance().getSequence().getSequence().iterator();
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
            iterator = Service.instance().getSequence().getSequence().iterator();
        }
    }

    public void reestart(){
        detieneTemporizador();
        Service.instance().updateScores(model.getScore());
        model.setMaxScore(Service.instance().getScores());
        view.updateMejoresPuntuaciones(model.getMaxScore());
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
        model.updateScore(totalTime, timeSpend);
        model.increaseLevel();
        model.updateTam();
        view.updateLevel(model.getLevel().getLevel());
        view.updateScore(model.getScore().getScore());
        view.activaStart();
        playerTurn = false;
    }

    public void setTiempoRestante(){
       if (model.getLevel().getLevel() == 1) {
            totalTime = 30;
        } else if (model.getLevel().getLevel() <= 4) {
            totalTime = 25;
        } else if (model.getLevel().getLevel() <= 7) {
            totalTime = 20;
        } else if (model.getLevel().getLevel() <= 10) {
            totalTime = 15;
        } else if (model.getLevel().getLevel() <= 13) {
            totalTime = 10;
        } else if (model.getLevel().getLevel() <= 15) {
            totalTime = 5;
        } else if (model.getLevel().getLevel() > 15) {
            totalTime = 2;
        }
      /*  List<Integer> levelTimes = this.configuration.getLevelTimes();
        int currentLevel = model.getLevel().getLevel();

        if (currentLevel >= 1 && currentLevel <= levelTimes.size()) {
            totalTime = levelTimes.get(currentLevel - 1);
        } else {
            totalTime = 30; // o cualquier otro valor predeterminado que desees
        }

       */
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
