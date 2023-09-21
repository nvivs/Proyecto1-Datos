package Proyecto.MVC;

import Proyecto.logic.SequencePartColor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;

public class View extends JFrame implements Observer {
    private JPanel panel;
    private JLabel Puntuación;
    private JButton[] botones;

    public View() {

//            botones[0].addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    System.out.println("Se clickeo el boton rojo");
//                }
//            });
//
//            botones[1].addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    System.out.println("Se clickeo el boton verde");
//                }
//            });
//
//            botones[2].addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    System.out.println("Se clickeo el boton azul");
//                }
//            });
//
//            botones[3].addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    System.out.println("Se clickeo el boton amarillo");
//                }
//            });

        // Hacer visible la ventan
        setVisible(true);
        setTitle("Simon Dice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(503, 530);
        setLayout(new GridLayout(2, 2));

        if(botones[0] != null){
            botones[0].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
        }

        if(botones[1] != null){
            botones[1].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
        }

        if(botones[2] != null){
            botones[2].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
        }

        if(botones[3] != null){
            botones[3].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
        }

        if(botones[4] != null){
            botones[4].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
        }

        if(botones[5] != null){
            botones[5].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
        }
        // Crea los botones y configura su apariencia
        // botones = new JButton[4]; // Cambia el número según tus botones
        // for (int i = 0; i < botones.length; i++) {
        //   botones[i] = new JButton("Botón " + (i + 1));
        // botones[i].setBackground(Color.BLACK); // Establece el color de fondo
        // botones[i].setOpaque(true);
        // botones[i].setEnabled(false); // Desactiva los botones inicialmente
        // add(botones[i]);
        // }

//            int x=0;
//            // Inicializa el temporizador con un retraso de 1 segundo
//            timer = new Timer(1000, new ActionListener() {
//                private int indiceIluminado = 0; // Inicializa el índice aquí
//
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    // Verifica si hemos iluminado todos los botones
//                    if (indiceIluminado < 4) {
//                        // Configura el botón actual según el índice iluminado
//                        switch (indiceIluminado) {
//                            case 0:
//                                ImageIcon img = new ImageIcon("Proyecto 1- Datos/src/main/resources/rojoClaro.png");
//                                botoRojo.setIcon(img);
//                                break;
//                            case 1:
//                                ImageIcon img2 = new ImageIcon("Proyecto 1- Datos/src/main/resources/verdeClaro.png");
//                                botoVerde.setIcon(img2);
//                                break;
//                            case 2:
//                                ImageIcon img3 = new ImageIcon("Proyecto 1- Datos/src/main/resources/amarilloClaro.png");
//                                botoAmarillo.setIcon(img3);
//                                break;
//                            case 3:
//                                ImageIcon img4 = new ImageIcon("Proyecto 1- Datos/src/main/resources/azulClaro.png");
//                                botoAzul.setIcon(img4);
//                                break;
//                        }
//
//                        // Incrementa el índice iluminado
//                        indiceIluminado++;
//                    } else {
//                        // Cuando se completa la secuencia, detén el temporizador
//                        ((Timer) e.getSource()).stop();
//                    }
//                }
//            });
//        }
//        public void iniciarSecuencia() {
//            // Iniciar la secuencia de iluminación
//            // indiceIluminado = 0;
//            timer.start();
//        }
    }

    Controller controller;
    Model model;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }

    public void iniciaBotones(int tam){
        for (int i = 0; i < tam; i++) {
            botones[i].setBorderPainted(false);
            botones[i].setContentAreaFilled(false);
        }

        setLayout(null);

        for (int i = 0; i < tam; i++) {
            getContentPane().add(botones[i]);
        }
    }

    public void reproduceSecuencia() throws InterruptedException {
        for(int i = 0; i < model.getBotones().length; i++){
            botones[i].setIcon(model.getSecuencia().iterator().next().getColor());
            if(model.getNivel() == 1) {
                Thread.sleep(5000);
            }else if(model.getNivel() <= 4){
                Thread.sleep(4000);
            }else if(model.getNivel() <= 7){
                Thread.sleep(3000);
            }else if(model.getNivel() <= 10){
                Thread.sleep(2000);
            }else if(model.getNivel() <= 13){
                Thread.sleep(1000);
            }else if(model.getNivel() <= 15){
                Thread.sleep(500);
            }else if(model.getNivel() > 15){
                Thread.sleep(100);
            }
            botones = model.getBotones();
        }
    }

    @Override
    public void update(Observable o, Object properties) {
        int changedProps = (int) properties;

        if((changedProps & Model.BOTONS) == Model.BOTONS){
            botones = model.getBotones();
            iniciaBotones(botones.length);
        }

        if((changedProps & Model.SEQUENCE) == Model.SEQUENCE){
            try {
                reproduceSecuencia();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if((changedProps & Model.LEVEL) == Model.LEVEL){
            controller.newLevel(botones);
        }

        if((changedProps & Model.SCORE) == Model.SCORE){
            Puntuación.setText(String.valueOf(model.getScore()));
        }
    }
}
