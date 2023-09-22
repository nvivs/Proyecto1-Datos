package Proyecto.MVC;

import Proyecto.Util.QueueException;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import static java.lang.Thread.sleep;

public class View extends JFrame implements Observer {
    private JPanel panel;
    private JButton[] botones;
    int tiempoRestante;
    int tiempoTotal;
    private JMenu fileMenu;
    private JMenuItem quitItem;
    private JMenuBar mainMenu;
    public Color[] COLORS;
    private JLabel Nivel;
    private JLabel Puntuacion;
    private JLabel Tiempo;
    private JLabel nivelLbl;
    private JLabel puntuacionLbl;
    private JLabel tiempoLbl;

    public View() {

        initColors();

        panel = new JPanel();
        panel.setFocusable(true);
        panel.setFocusTraversalKeysEnabled(false);
        Tiempo.setVisible(true);
        Nivel.setVisible(true);
        Puntuacion.setVisible(true);
    }

    public Color obtenerColorEnPosicion(int x, int y){
        BufferedImage panelImage = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        panel.paint(panelImage.getGraphics());
        return new Color(panelImage.getRGB(x, y));
    }

    public void closeWindow() {
        if (confirmClose()) {
            System.out.println("Cerrando la aplicación..");
            System.exit(0);
        }
    }

    public Color[] getColors() {
        return COLORS;
    }

    private JPanel setupComponents(Container c) {
        c.setLayout(new BorderLayout());
        c.add(BorderLayout.CENTER, panel);
        int i = 0;
        if(model.getNivel() <= 5) {// 4 colores
            i = 4;
        }else if(model.getNivel() <= 10){// 5 colores
            i = 5;
        }else if(model.getNivel() <= 15){// 6 colores
            i = 6;
        }else if(model.getNivel() > 15){// 7 colores
            i = 7;
        }

        return controller.format(COLORS, i);
    }


    public boolean confirmClose() {
        Object[] options = {"Sí", "No"};
        return JOptionPane.showOptionDialog(this,
                "¿Desea cerrar la aplicación?", "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
                options, // texto de los botones
                options[0] // opción por defecto
        ) == JOptionPane.OK_OPTION;
    }

    public Container getPanel() {
        return panel;
    }

    public void initColors(){
        COLORS = new Color[]{
                new Color(255, 0, 0),
                new Color(33, 255, 0),
                new Color(255, 243, 0),
                new Color(0, 42, 255),
                new Color(255, 136, 0),
                new Color(223, 0, 255),
                new Color(0, 245, 255, 255)
        };
    }

    public int setTiempoRestante(){
//            try(Clip clip = AudioSystem.getClip()){
//                clip.open(model.getSecuencia().iterator().next().getSound());
//                clip.start();
//                //Thread.sleep(clip.getMicrosecondLength() / 1_000);
//            } catch (IOException
//                     //| InterruptedException
//                     | LineUnavailableException ex) {
//                System.err.printf("Excepción: '%s'%n", ex.getMessage());
//            }
        if (model.getNivel() == 1) {
            tiempoRestante = 30;
        } else if (model.getNivel() <= 4) {
            tiempoRestante = 25;
        } else if (model.getNivel() <= 7) {
            tiempoRestante = 20;
        } else if (model.getNivel() <= 10) {
            tiempoRestante = 15;
        } else if (model.getNivel() <= 13) {
            tiempoRestante = 10;
        } else if (model.getNivel() <= 15) {
            tiempoRestante = 5;
        } else if (model.getNivel() > 15) {
            tiempoRestante = 2;
        }
        return tiempoRestante;
    }

    public void espera() throws InterruptedException {
        if (model.getNivel() == 1) {
            sleep(5000);
        } else if (model.getNivel() <= 4) {
            sleep(4000);
        } else if (model.getNivel() <= 7) {
            sleep(3000);
        } else if (model.getNivel() <= 10) {
            sleep(2000);
        } else if (model.getNivel() <= 13) {
            sleep(1000);
        } else if (model.getNivel() <= 15) {
            sleep(500);
        } else if (model.getNivel() > 15) {
            sleep(100);
        }
    }

    private void temporizador(){
        Thread thread = new Thread(() -> {
            for (int i = tiempoRestante; i >= 0; i--) {
                Tiempo.setText(String.valueOf(i));
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
                                controller.fail(COLORS);
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

        if((changedProps & Model.PANEL) == Model.PANEL){
            panel = setupComponents(getContentPane());
//            panel.add(Tiempo);
//            panel.add(Puntuacion);
//            panel.add(Nivel);
//            panel.add(tiempoLbl);
//            panel.add(puntuacionLbl);
//            panel.add(nivelLbl);
        }

        if((changedProps & Model.SEQUENCEREPRODUCED) == Model.SEQUENCEREPRODUCED){
            if(model.getSequenceIndex() > 0){
                int x = 0;
                if(model.getNivel() <= 5) {// 4 colores
                    x = 4;
                }else if(model.getNivel() <= 10){// 5 colores
                    x = 5;
                }else if(model.getNivel() <= 15){// 6 colores
                    x = 6;
                }else if(model.getNivel() > 15){// 7 colores
                    x = 7;
                }
                Color[] colores = COLORS;

                colores[model.getSecuencia().getIndex()] = model.getSecuencia().getSequence().iterator().next().getColor();
                panel = controller.format(colores, x);

                try {
                   espera();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                model.setSequenceIndex(model.getSequenceIndex() - 1);
                model.changedProps = Model.RESTART;
                model.commit();
//                model.changedProps = Model.SEQUENCEREPRODUCED;
//                model.commit();
            }else {
                model.changedProps = Model.NONE;
                tiempoRestante = setTiempoRestante();
                Tiempo.setText(String.valueOf(tiempoRestante));
                temporizador();
            }
        }

        if((changedProps & Model.RESTART) == Model.RESTART){
            int x = 0;
            if(model.getNivel() <= 5) {// 4 colores
                x = 4;
            }else if(model.getNivel() <= 10){// 5 colores
                x = 5;
            }else if(model.getNivel() <= 15){// 6 colores
                x = 6;
            }else if(model.getNivel() > 15){// 7 colores
                x = 7;
            }
            initColors();
            panel = controller.format(COLORS, x);
            model.changedProps = Model.SEQUENCEREPRODUCED;
        }

        if((changedProps & Model.SEQUENCE) == Model.SEQUENCE){
            model.changedProps = Model.SEQUENCEREPRODUCED;
            //model.commit();
        }

        if((changedProps & Model.LEVEL) == Model.LEVEL){
            Nivel.setText(String.valueOf(model.getNivel()));
        }

        if((changedProps & Model.SCORE) == Model.SCORE){
            Puntuacion.setText(String.valueOf(model.getScore()));
        }
        panel.revalidate();
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

    public void check(Color color){
        try {
            controller.check(color);
        } catch (QueueException ex) {// si ganó
            JOptionPane.showMessageDialog(panel, "Haz completado la secuencia correctamente", "HAZ GANADO", JOptionPane.INFORMATION_MESSAGE);
            int x = 0;
            if(model.getNivel() <= 5) {// 4 colores
                x = 4;
            }else if(model.getNivel() <= 10){// 5 colores
                x = 5;
            }else if(model.getNivel() <= 15){// 6 colores
                x = 6;
            }else if(model.getNivel() > 15){// 7 colores
                x = 7;
            }
            controller.win(tiempoTotal, tiempoRestante, x, COLORS);
        }
        catch (RuntimeException ex){// si perdió
            JOptionPane pane = new JOptionPane();
            pane.showMessageDialog(panel, "Haz fallado al imitar la secuencia", "HAZ PERDIDO", JOptionPane.INFORMATION_MESSAGE);
            pane.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentHidden(ComponentEvent e) {
                    super.componentHidden(e);
                    try {
                        controller.fail(COLORS);
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
}
