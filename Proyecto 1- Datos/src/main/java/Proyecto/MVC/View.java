package Proyecto.MVC;

import Proyecto.Util.QueueException;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import static java.lang.Thread.sleep;

public class View extends JFrame implements Observer {
    private JPanel panel;
    private JLabel Puntuación;
    private JLabel tiempo;
    private JButton[] botones;
    int tiempoRestante;
    int tiempoTotal;

    public View() throws InterruptedException, UnsupportedAudioFileException, QueueException, IOException {
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
//        if(botones[0] != null){
//            botones[0].addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    check(0);
//                }
//            });
//        }
//
//        if(botones[1] != null){
//            botones[1].addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    check(1);
//                }
//            });
//        }
//
//        if(botones[2] != null){
//            botones[2].addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    check(2);
//                }
//            });
//        }
//
//        if(botones[3] != null){
//            botones[3].addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    check(3);
//                }
//            });
//        }
//
//        if(botones[4] != null){
//            botones[4].addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    check(4);
//                }
//            });
//        }
//
//        if(botones[5] != null){
//            botones[5].addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    check(5);
//                }
//            });
//        }
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

    public void check(int i){
        try {
            controller.check((ImageIcon) botones[i].getIcon());
        } catch (QueueException ex) {// si ganó
            JOptionPane.showMessageDialog(panel, "Haz completado la secuencia correctamente", "HAZ GANADO", JOptionPane.INFORMATION_MESSAGE);
            controller.win(tiempoTotal, tiempoRestante);
        }
        catch (RuntimeException ex){// si perdió
            JOptionPane pane = new JOptionPane();
            pane.showMessageDialog(panel, "Haz fallado al imitar la secuencia", "HAZ PERDIDO", JOptionPane.INFORMATION_MESSAGE);
            pane.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentHidden(ComponentEvent e) {
                    super.componentHidden(e);
                    try {
                        controller.fail();
                    } catch (UnsupportedAudioFileException | QueueException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
        catch (UnsupportedAudioFileException | IOException ex){
            throw new RuntimeException(ex);
        }
    }

    public void iniciaBotones(int tam) throws UnsupportedAudioFileException, QueueException, IOException {
        controller.inicia();
        for (int i = 0; i < tam; i++) {
            botones[i].setBorderPainted(false);
            botones[i].setContentAreaFilled(false);
        }

        setLayout(null);

        for (int i = 0; i < tam; i++) {
            getContentPane().add(botones[i]);
        }
    }

    public int reproduceSecuencia() throws InterruptedException {
        this.botones = model.getBotones();
            for(int i = 0; i < model.getBotones().length; i++){
                botones[i].setIcon(model.getSecuencia().iterator().next().getColor());
//            try(Clip clip = AudioSystem.getClip()){
//                clip.open(model.getSecuencia().iterator().next().getSound());
//                clip.start();
//                //Thread.sleep(clip.getMicrosecondLength() / 1_000);
//            } catch (IOException
//                     //| InterruptedException
//                     | LineUnavailableException ex) {
//                System.err.printf("Excepción: '%s'%n", ex.getMessage());
//            }
            if(model.getNivel() == 1) {
                sleep(5000);
                tiempoRestante = 30;
            }else if(model.getNivel() <= 4){
                sleep(4000);
                tiempoRestante = 25;
            }else if(model.getNivel() <= 7){
                sleep(3000);
                tiempoRestante = 20;
            }else if(model.getNivel() <= 10){
                sleep(2000);
                tiempoRestante = 15;
            }else if(model.getNivel() <= 13){
                sleep(1000);
                tiempoRestante = 10;
            }else if(model.getNivel() <= 15){
                sleep(500);
                tiempoRestante = 5;
            }else if(model.getNivel() > 15){
                sleep(100);
                tiempoRestante = 2;
            }
            botones = model.getBotones();
        }
        return tiempoRestante;
    }

    private void temporizador(){
        Thread thread = new Thread(() -> {
            for (int i = tiempoRestante; i >= 0; i++) {
                tiempo.setText(String.valueOf(i));
                tiempoTotal = tiempoRestante - i;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (i == 0) {
                    JOptionPane pane = new JOptionPane();
                    pane.showMessageDialog(panel, "Te has quedado sin tiempo", "HAZ PERDIDO", JOptionPane.INFORMATION_MESSAGE);
                    pane.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentHidden(ComponentEvent e) {
                            super.componentHidden(e);
                            try {
                                controller.fail();
                            } catch (UnsupportedAudioFileException | QueueException | IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    });
                }
            }
        });
        thread.start();
    }

    @Override
    public void update(Observable o, Object properties) {
        int changedProps = (int) properties;

        if((changedProps & Model.BOTONS) == Model.BOTONS){
            botones = model.getBotones();
            try {
                iniciaBotones(botones.length);
            } catch (UnsupportedAudioFileException | IOException | QueueException e) {
                throw new RuntimeException(e);
            }
            for(int i = 0; i < botones.length; i++){
                if(botones[i] != null){
                    int finalI = i;
                    botones[i].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            check(finalI);
                        }
                    });
                }
            }
        }

        if((changedProps & Model.SEQUENCE) == Model.SEQUENCE){
            try {
                tiempoRestante = reproduceSecuencia();
                tiempo.setText(String.valueOf(tiempoRestante));
                temporizador();
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
