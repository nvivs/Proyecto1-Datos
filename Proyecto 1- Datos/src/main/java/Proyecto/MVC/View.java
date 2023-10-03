package Proyecto.MVC;

import Proyecto.Util.QueueException;
import Proyecto.logic.BestScore;
import Proyecto.logic.Level;
import Proyecto.logic.Score;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class View extends JFrame {
    private Model model;
    private JButton newGameButton;
    private JButton startButton;
    private JLabel levelLabel;
    private JLabel scoreLabel;
    private JLabel tiempo;
    private Timer timer;
    private Timer count;
    private int level;
    private Controller controller;
    private JLabel numero1;
    private JLabel numero2;
    private JLabel numero3;

    public View() {

        setTitle("Simon");
        setIconImage(new ImageIcon("C:/Users/julyr/OneDrive/Escritorio/Proyecto1-Datos/Proyecto 1- Datos/src/main/resources/simon.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLayout(new BorderLayout());

        model = new Model();

        add(model, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        newGameButton = new JButton("Reiniciar");
        buttonPanel.add(newGameButton);
        startButton = new JButton("Reproducir secuencia");
        activaStart();
        activaNewGame();
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);

        levelLabel = new JLabel(" Nivel: 1");
        scoreLabel = new JLabel(" Puntuación: 0");
        tiempo = new JLabel(" Tiempo restante: 0");
        numero1 = new JLabel("#1: 0");
        numero2 = new JLabel("#2: 0");
        numero3 = new JLabel("#3: 0");

        JPanel infoPanel = new JPanel(new GridLayout(4, 2));
        infoPanel.add(levelLabel);
        infoPanel.add(new JLabel("Mejores puntuaciones:"));
        infoPanel.add(tiempo);
        infoPanel.add(numero1);
        infoPanel.add(scoreLabel);
        infoPanel.add(numero2);
        infoPanel.add(new JLabel(""));
        infoPanel.add(numero3);

        add(infoPanel, BorderLayout.NORTH);

        // Configura el temporizador para mostrar los colores
        timer = new Timer(tiempoDeReproduccion(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        count = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        setVisible(true);

        // Configura el controlador para el botón "Iniciar"
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.reestart();
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.startGame();
                } catch (UnsupportedAudioFileException | QueueException | IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Configura el controlador para el temporizador
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.getPlayerTurn()) {
                    controller.setPlayerTurn(false);
                    stopTimer();
                } else {
                    try {
                        controller.sound(0);
                        controller.showNextColor();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        count.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (model.getCount()) {
                    controller.setPlayerTurn(false);
                    if(model.getCountIndex() > 1){
                        model.reduce();
                    }else{
                        model.setCount(false);
                        model.setCountIndex(3);
                    }
                } else {
                    stopCount();
                    model.initColors();
                    repaint();
                    controller.setRunning(true);
                    controller.iniciaTemporizador();
                    controller.setPlayerTurn(true);
                }
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int x = evt.getX();
                int y = evt.getY();
                Color colorAtPosition;
                Color c = null;
                boolean valid = false;

                if(controller.getPlayerTurn()) {
                    try {
                        colorAtPosition = obtenerColorEnPosicion(x, y);

                        for (int i = 0; i < model.getColors().length; i++) {
                            if (model.getColors()[i].equals(colorAtPosition)) {
                                valid = true;
                                break;
                            }
                        }

                        if (valid) {
                            colorAtPosition = controller.cambiaColorClickeado(colorAtPosition);
                            // model.changeColor(c);
                            repaint();
                            controller.check(colorAtPosition);
                            //colorChangeTimer.restart();
                            //model.initColors();
                            //view.repaint();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Model getModel() {
        return model;
    }


    public int tiempoDeReproduccion(){
        if (level == 1) {
            return 1700;
        } else if (level <= 4) {
            return 1500;
        } else if (level <= 7) {
            return 1300;
        } else if (level <= 10) {
            return 1100;
        } else if (level <= 13) {
            return 900;
        } else if (level <= 15) {
            return 700;
        } else if (level > 15) {
            return 500;
        }else {
            return 1000;
        }
    }

    public Color obtenerColorEnPosicion(int x, int y) throws Exception{
        BufferedImage panelImage = new BufferedImage(model.getWidth(), model.getHeight(), BufferedImage.TYPE_INT_RGB);
        model.paint(panelImage.getGraphics());
        return new Color(panelImage.getRGB(x, y));
    }

    public void updateMejoresPuntuaciones(BestScore b){
        String p1;
        String p2;
        String p3;

        p1 = String.valueOf(b.get(0).getScore());
        p2 = String.valueOf(b.get(1).getScore());
        p3 = String.valueOf(b.get(2).getScore());

        numero1.setText("#1: " + p1);
        numero2.setText("#2: " + p2);
        numero3.setText("#3: " + p3);
    }

    public void desactivaNewGame(){
        newGameButton.setEnabled(false);
        newGameButton.setToolTipText(null);
    }

    public void activaNewGame(){
        newGameButton.setEnabled(true);
        newGameButton.setBackground(new Color(180,46,46));
        newGameButton.setToolTipText("Presione para reiniciar el juego");
    }

    public void activaStart(){
        startButton.setEnabled(true);
        startButton.setBackground(new Color(36,187,72));
        startButton.setForeground(Color.RED);
        startButton.setToolTipText("Presione para reproducir la secuencia");
    }

    public void desactivaStart(){
        startButton.setEnabled(false);
        startButton.setForeground(Color.BLACK);
        startButton.setToolTipText(null);
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void updateLevel(int level) {
        levelLabel.setText(" Nivel: " + level);
        this.level = level;
    }

    public void updateScore(int score) {
        scoreLabel.setText(" Puntuación: " + score);
    }

    public JLabel getTiempo(){
        return tiempo;
    }

    public void setTiempo(int tiempo){
        this.tiempo.setText(" Tiempo restante: " + String.valueOf(tiempo));
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void startCount() {
        count.start();
    }

    public void stopCount() {
        count.stop();
    }
}
