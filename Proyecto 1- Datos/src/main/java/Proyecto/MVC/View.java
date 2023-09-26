package Proyecto.MVC;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class View extends JFrame {
    private Model panel;
    private JButton startButton;
    private JLabel levelLabel;
    private JLabel scoreLabel;
    private Timer timer;

    public Model getPanel() {
        return panel;
    }

    public View() {

        setTitle("Simon Dice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());

        panel = new Model();

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Iniciar");
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.SOUTH);

        levelLabel = new JLabel(" Nivel: 1");
        scoreLabel = new JLabel(" Puntuación: 0");

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(levelLabel);
        infoPanel.add(scoreLabel);
        add(infoPanel, BorderLayout.NORTH);

        // Configura el temporizador para mostrar los colores
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.showColor();
            }
        });

        setVisible(true);
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

    public void updateLevel(int level) {
        levelLabel.setText(" Nivel: " + level);
    }

    public void updateScore(int score) {
        scoreLabel.setText(" Puntuación: " + score);
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }
}
