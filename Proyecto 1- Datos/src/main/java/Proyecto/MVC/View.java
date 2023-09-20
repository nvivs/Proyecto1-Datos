package Proyecto.MVC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame{
    private JPanel panel;

    private JButton[] botones; // Arreglo de botones a iluminar
        //private int indiceIluminado; // Índice del botón que se ilumina actualmente
        private Timer timer; // Temporizador para la secuencia de iluminación
        private BotonPersonalizado botoRojo;
        private BotonPersonalizado botoVerde;
        private BotonPersonalizado botoAmarillo;
        private BotonPersonalizado botoAzul;

        public View() {
            botoRojo= new BotonPersonalizado("Proyecto 1- Datos\\src\\main\\resources\\Rojo.png");
            botoVerde= new BotonPersonalizado("Proyecto 1- Datos\\src\\main\\resources\\Verde.png");
            botoAzul= new BotonPersonalizado("Proyecto 1- Datos\\src\\main\\resources\\Azul.png");
            botoAmarillo= new BotonPersonalizado("Proyecto 1- Datos\\src\\main\\resources\\Amarillo.png");
            setLayout(null);
            botoRojo.setBackground(Color.BLACK);
            botoRojo.setBorderPainted(false);
            botoRojo.setContentAreaFilled(false);
            botoRojo.setBounds(20,30,500,600);
            botoVerde.setBounds(210,30,500,600);
            botoAmarillo.setBounds(20,225,500,600);
            botoAzul.setBounds(210,225,500,600);
            getContentPane().add(botoRojo);
            getContentPane().add(botoVerde);
            getContentPane().add(botoAmarillo);
            getContentPane().add(botoAzul);
            botoRojo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Se clickeo el boton rojo");
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

            int x=0;
            // Inicializa el temporizador con un retraso de 1 segundo
            timer = new Timer(1000, new ActionListener() {
                private int indiceIluminado = 0; // Inicializa el índice aquí

                @Override
                public void actionPerformed(ActionEvent e) {
                    // Verifica si hemos iluminado todos los botones
                    if (indiceIluminado < 4) {
                        // Configura el botón actual según el índice iluminado
                        switch (indiceIluminado) {
                            case 0:
                                ImageIcon img = new ImageIcon("Proyecto 1- Datos\\src\\main\\resources\\rojoiluminado.png");
                                botoRojo.setIcon(img);
                                break;
                            case 1:
                                ImageIcon img2 = new ImageIcon("Proyecto 1- Datos\\src\\main\\resources\\verdeiluminado.png");
                                botoVerde.setIcon(img2);
                                break;
                            case 2:
                                ImageIcon img3 = new ImageIcon("Proyecto 1- Datos\\src\\main\\resources\\amarilloiluminado.png");
                                botoAmarillo.setIcon(img3);
                                break;
                            case 3:
                                ImageIcon img4 = new ImageIcon("Proyecto 1- Datos\\src\\main\\resources\\azuliluminado.png");
                                botoAzul.setIcon(img4);
                                break;
                        }

                        // Incrementa el índice iluminado
                        indiceIluminado++;
                    } else {
                        // Cuando se completa la secuencia, detén el temporizador
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
        }
        public void iniciarSecuencia() {
            // Iniciar la secuencia de iluminación
            // indiceIluminado = 0;
            timer.start();
        }

    public static class BotonPersonalizado extends JButton {
        public BotonPersonalizado(String imagePath) {
            // Cargar una imagen personalizada para el botón
            ImageIcon imageIcon = new ImageIcon(imagePath);
            setIcon(imageIcon);
            setBorderPainted(false);
            setContentAreaFilled(false);
        }
    }
}
