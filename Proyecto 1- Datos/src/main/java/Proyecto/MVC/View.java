package Proyecto.MVC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View extends JFrame{
    private JPanel panel;

    private JButton[] botones; // Arreglo de botones a iluminar
        //private int indiceIluminado; // Índice del botón que se ilumina actualmente
        private Timer timer; // Temporizador para la secuencia de iluminación

        public View() {

            botones = new JButton[4];
            for (int i = 0; i < 4; i++) {
                botones[i] = new JButton();
            }
            botones[0].setIcon(new ImageIcon("Proyecto 1- Datos/src/main/resources/rojo.png"));
            botones[1].setIcon(new ImageIcon("Proyecto 1- Datos/src/main/resources/verde.png"));
            botones[2].setIcon(new ImageIcon("Proyecto 1- Datos/src/main/resources/amarillo.png"));
            botones[3].setIcon(new ImageIcon("Proyecto 1- Datos/src/main/resources/azul.png"));
            for (int i = 0; i < 4; i++) {
                botones[i].setBorderPainted(false);
                botones[i].setContentAreaFilled(false);
            }

            setLayout(null);
            botones[0].setBounds(20, 30, 500, 600);
            botones[1].setBounds(210, 30, 500, 600);
            botones[2].setBounds(20, 225, 500, 600);
            botones[3].setBounds(210, 225, 500, 600);

            for (int i = 0; i < 4; i++) {
                getContentPane().add(botones[i]);
            }

            botones[0].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Se clickeo el boton rojo");
                }
            });

            botones[1].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Se clickeo el boton verde");
                }
            });

            botones[2].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Se clickeo el boton azul");
                }
            });

            botones[3].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Se clickeo el boton amarillo");
                }
            });

            // Hacer visible la ventana
            setVisible(true);
            setTitle("Simon Dice");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 400);
            setLayout(new GridLayout(2, 2)); // Cambia el diseño según tus botones

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
}
