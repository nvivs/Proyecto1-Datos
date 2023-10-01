package Proyecto.MVC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class View extends JFrame {
    private Model panel;
    private JButton newGameButton;
    private JButton startButton;
    private JLabel levelLabel;
    private JLabel scoreLabel;
    private JLabel tiempo;
    private Timer timer;
    private Timer count;
    public Model getPanel() {
        return panel;
    }

    public View() {

        setTitle("Simon");
        setIconImage(new ImageIcon("C:/Users/julyr/OneDrive/Escritorio/Proyecto1-Datos/Proyecto 1- Datos/src/main/resources/simon.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());

        panel = new Model();

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        newGameButton = new JButton("Reiniciar");
        buttonPanel.add(newGameButton);
        startButton = new JButton("Reproducir secuencia");
        activaBotones();
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);

        levelLabel = new JLabel(" Nivel: 1");
        scoreLabel = new JLabel(" Puntuación: 0");
        tiempo = new JLabel(" Tiempo restante: 0");

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(levelLabel);
        infoPanel.add(scoreLabel);
        infoPanel.add(tiempo);

        add(infoPanel, BorderLayout.NORTH);

        // Configura el temporizador para mostrar los colores
        timer = new Timer(1500, new ActionListener() {
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
    }

    public Color obtenerColorEnPosicion(int x, int y) throws Exception{
        BufferedImage panelImage = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        panel.paint(panelImage.getGraphics());
        return new Color(panelImage.getRGB(x, y));
    }

    public void setNewGameButtonListener(ActionListener listener) {
        newGameButton.addActionListener(listener);
    }

    public void desactivaBotones(){
        startButton.setEnabled(false);
        startButton.setForeground(Color.BLACK);
        startButton.setToolTipText(null);
        newGameButton.setEnabled(false);
        newGameButton.setToolTipText(null);
    }

    public void activaBotones(){
        startButton.setEnabled(true);
        startButton.setBackground(new Color(36,187,72));
        startButton.setToolTipText("Presione para reproducir la secuencia");
        newGameButton.setEnabled(true);
        newGameButton.setBackground(new Color(180,46,46));
        newGameButton.setToolTipText("Presione para reiniciar el juego");
    }

    public void setStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void setPanel(Model panel) {
        this.panel = panel;
    }

    public void setTimerListener(ActionListener listener) {
        timer.addActionListener(listener);
    }

    public void setCountListener(ActionListener listener) {
        count.addActionListener(listener);
    }

    public void updateLevel(int level) {
        levelLabel.setText(" Nivel: " + level);
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
