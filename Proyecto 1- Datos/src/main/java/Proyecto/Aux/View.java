////package Proyecto.MVC;
////
////import Proyecto.Util.QueueException;
////
////import javax.sound.sampled.*;
////import javax.swing.*;
////import java.awt.*;
////import java.awt.event.*;
////import java.awt.image.BufferedImage;
////import java.io.IOException;
////import java.lang.management.BufferPoolMXBean;
////import java.util.Observable;
////import java.util.Observer;
////import static java.lang.Thread.sleep;
////
////public class View extends JFrame implements Observer {
////    JPanel panel;
////    private JLabel puntuacionLbl;
////    private JLabel puntuacion;
////    private JLabel nivelLbl;
////    private JLabel nivel;
////    private JLabel tiempoLbl;
////    private JLabel tiempo;
////    private JPanel buttons;
////    int tiempoRestante;
////    int tiempoTotal;
////    public Color[] COLORS;
////    JButton inicia;
////    JPanel datos;
////
////    public View() {
////
////        initColors();
////
////        panel = new JPanel();
////        datos = new JPanel(new GridLayout());
////        inicia = new JButton("Iniciar");
////
////        GridBagConstraints gbc = new GridBagConstraints();
////        gbc.anchor = GridBagConstraints.NORTHEAST;
////        gbc.gridx = 0;
////        gbc.gridy = 0;
////        gbc.insets = new Insets(5, 5, 5, 5);
////
////
////        puntuacionLbl.setHorizontalAlignment(SwingConstants.CENTER);
////        nivelLbl.setHorizontalAlignment(SwingConstants.CENTER);
////        tiempoLbl.setHorizontalAlignment(SwingConstants.CENTER);
////        inicia.setHorizontalAlignment(SwingConstants.CENTER);
////
////        datos.add(tiempoLbl, gbc);
////        datos.add(tiempo, gbc);
////        gbc.gridy = 1;
////        datos.add(puntuacionLbl, gbc);
////        datos.add(puntuacion, gbc);
////        gbc.gridy = 2;
////        datos.add(nivelLbl, gbc);
////        datos.add(nivel, gbc);
////        gbc.gridy = 3;
////        datos.add(inicia, gbc);
////
////        datos.setBackground(Color.WHITE);
////        datos.setForeground(Color.WHITE);
////
////        panel.setFocusable(true);
////        panel.setFocusTraversalKeysEnabled(false);
////
////        inicia.addMouseListener(new MouseAdapter() {
////            @Override
////            public void mouseClicked(MouseEvent e) {
////                super.mouseClicked(e);
////                try {
////                    controller.reproduceSecuencia();
////                } catch (InterruptedException ex) {
////                    throw new RuntimeException(ex);
////                }
////            }
////        });
////    }
////
////    public Color obtenerColorEnPosicion(int x, int y){
////        BufferedImage panelImage = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
////        panel.paint(panelImage.getGraphics());
////        return new Color(panelImage.getRGB(x, y));
////    }
////
////    public void closeWindow() {
////        if (confirmClose()) {
////            System.out.println("Cerrando la aplicación..");
////            System.exit(0);
////        }
////    }
////
////    public Color[] getColors() {
////        return COLORS;
////    }
////
////    private JPanel setupComponents(Container c) {
////        c.setLayout(new BorderLayout());
////        c.add(BorderLayout.CENTER, panel);
////        int i = 0;
////        if(model.getNivel() <= 5) {// 4 colores
////            i = 4;
////        }else if(model.getNivel() <= 10){// 5 colores
////            i = 5;
////        }else if(model.getNivel() <= 15){// 6 colores
////            i = 6;
////        }else if(model.getNivel() > 15){// 7 colores
////            i = 7;
////        }
////
////        return controller.format(COLORS, i);
////    }
////
////
////    public boolean confirmClose() {
////        Object[] options = {"Sí", "No"};
////        return JOptionPane.showOptionDialog(this,
////                "¿Desea cerrar la aplicación?", "Confirmación",
////                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
////                options, // texto de los botones
////                options[0] // opción por defecto
////        ) == JOptionPane.OK_OPTION;
////    }
////
////    public Container getPanel() {
////        return panel;
////    }
////
////    public void initColors(){
////        COLORS = new Color[]{
////                new Color(255, 0, 0),
////                new Color(33, 255, 0),
////                new Color(255, 243, 0),
////                new Color(0, 42, 255),
////                new Color(255, 136, 0),
////                new Color(223, 0, 255),
////                new Color(0, 245, 255, 255)
////        };
////    }
////
////    public int setTiempoRestante(){
//////            try(Clip clip = AudioSystem.getClip()){
//////                clip.open(model.getSecuencia().iterator().next().getSound());
//////                clip.start();
//////                //Thread.sleep(clip.getMicrosecondLength() / 1_000);
//////            } catch (IOException
//////                     //| InterruptedException
//////                     | LineUnavailableException ex) {
//////                System.err.printf("Excepción: '%s'%n", ex.getMessage());
//////            }
////        if (model.getNivel() == 1) {
////            tiempoRestante = 30;
////        } else if (model.getNivel() <= 4) {
////            tiempoRestante = 25;
////        } else if (model.getNivel() <= 7) {
////            tiempoRestante = 20;
////        } else if (model.getNivel() <= 10) {
////            tiempoRestante = 15;
////        } else if (model.getNivel() <= 13) {
////            tiempoRestante = 10;
////        } else if (model.getNivel() <= 15) {
////            tiempoRestante = 5;
////        } else if (model.getNivel() > 15) {
////            tiempoRestante = 2;
////        }
////        return tiempoRestante;
////    }
////
////    public void espera() throws InterruptedException {
////        if (model.getNivel() == 1) {
////            sleep(5000);
////        } else if (model.getNivel() <= 4) {
////            sleep(4000);
////        } else if (model.getNivel() <= 7) {
////            sleep(3000);
////        } else if (model.getNivel() <= 10) {
////            sleep(2000);
////        } else if (model.getNivel() <= 13) {
////            sleep(1000);
////        } else if (model.getNivel() <= 15) {
////            sleep(500);
////        } else if (model.getNivel() > 15) {
////            sleep(100);
////        }
////    }
////
////    void temporizador(){
////        Thread thread = new Thread(() -> {
////            for (int i = tiempoRestante; i >= 0; i--) {
////                tiempo.setText(String.valueOf(i));
////                tiempoTotal = tiempoRestante - i;
////                try {
////                    sleep(1000);
////                } catch (InterruptedException e) {
////                    throw new RuntimeException(e);
////                }
////                if (i == 0) {
////                    JOptionPane pane = new JOptionPane();
////                    pane.showMessageDialog(panel, "Te has quedado sin tiempo", "HAZ PERDIDO", JOptionPane.INFORMATION_MESSAGE);
////                    pane.addComponentListener(new ComponentAdapter() {
////                        @Override
////                        public void componentHidden(ComponentEvent e) {
////                            super.componentHidden(e);
////                            try {
////                                controller.fail(COLORS);
////                            } catch (UnsupportedAudioFileException | QueueException | IOException ex) {
////                                throw new RuntimeException(ex);
////                            }
////                        }
////                    });
////                }
////            }
////        });
////        thread.start();
////    }
////
////    @Override
////    public void update(Observable o, Object properties) {
////        int changedProps = (int) properties;
////
////        if((changedProps & Model.PANEL) == Model.PANEL){
////            panel = setupComponents(getContentPane());
////            panel.add(datos, BorderLayout.NORTH);
////        }
////
////        if((changedProps & Model.SEQUENCE) == Model.SEQUENCE){
////            try {
////                controller.reproduceSecuencia();
////            } catch (InterruptedException e) {
////                throw new RuntimeException(e);
////            }
////        }
////
////        if((changedProps & Model.LEVEL) == Model.LEVEL){
////            nivel.setText(String.valueOf(model.getNivel()));
////        }
////
////        if((changedProps & Model.SCORE) == Model.SCORE){
////            puntuacion.setText(String.valueOf(model.getScore()));
////        }
////        panel.revalidate();
////    }
////
////    Controller controller;
////    Model model;
////
////    public void setController(Controller controller) {
////        this.controller = controller;
////    }
////
////    public void setModel(Model model) {
////        this.model = model;
////        model.addObserver(this);
////    }
////
////    public void check(Color color){
////        try {
////            controller.check(color);
////        } catch (QueueException ex) {// si ganó
////            JOptionPane.showMessageDialog(panel, "Haz completado la secuencia correctamente", "HAZ GANADO", JOptionPane.INFORMATION_MESSAGE);
////            int x = 0;
////            if(model.getNivel() <= 5) {// 4 colores
////                x = 4;
////            }else if(model.getNivel() <= 10){// 5 colores
////                x = 5;
////            }else if(model.getNivel() <= 15){// 6 colores
////                x = 6;
////            }else if(model.getNivel() > 15){// 7 colores
////                x = 7;
////            }
////            controller.win(tiempoTotal, tiempoRestante, x, COLORS);
////        }
////        catch (RuntimeException ex){// si perdió
////            JOptionPane pane = new JOptionPane();
////            pane.showMessageDialog(panel, "Haz fallado al imitar la secuencia", "HAZ PERDIDO", JOptionPane.INFORMATION_MESSAGE);
////            pane.addComponentListener(new ComponentAdapter() {
////                @Override
////                public void componentHidden(ComponentEvent e) {
////                    super.componentHidden(e);
////                    try {
////                        controller.fail(COLORS);
////                    } catch (UnsupportedAudioFileException | QueueException | IOException ex) {
////                        throw new RuntimeException(ex);
////                    }
////                }
////            });
////        }
////        catch (UnsupportedAudioFileException | IOException ex){
////            throw new RuntimeException(ex);
////        }
////    }
////}
//package Proyecto.MVC;
//
//import Proyecto.Util.QueueException;
//import Proyecto.logic.Service;
//
//import javax.sound.sampled.UnsupportedAudioFileException;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
//import java.util.Observable;
//import java.util.Observer;
//
//public class View extends JFrame implements Observer {
//    JPanel panel;
//    private JLabel puntuacionLbl;
//    private JLabel puntuacion;
//    private JLabel nivelLbl;
//    private JLabel nivel;
//    private JLabel tiempoLbl;
//    private JLabel tiempo;
//    int tiempoRestante;
//    int tiempoTotal;
//    public Color[] COLORS;
//    JButton inicia;
//    JPanel datos;
//
//    public View() {
//
//        initColors();
//
//        panel = new JPanel();
//        datos = new JPanel(new GridLayout());
//        inicia = new JButton("Iniciar");
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.anchor = GridBagConstraints.NORTHEAST;
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.insets = new Insets(5, 5, 5, 5);
//
//        puntuacionLbl.setHorizontalAlignment(SwingConstants.CENTER);
//        nivelLbl.setHorizontalAlignment(SwingConstants.CENTER);
//        tiempoLbl.setHorizontalAlignment(SwingConstants.CENTER);
//        inicia.setHorizontalAlignment(SwingConstants.CENTER);
//
//        datos.add(tiempoLbl, gbc);
//        datos.add(tiempo, gbc);
//        gbc.gridy = 1;
//        datos.add(puntuacionLbl, gbc);
//        datos.add(puntuacion, gbc);
//        gbc.gridy = 2;
//        datos.add(nivelLbl, gbc);
//        datos.add(nivel, gbc);
//        gbc.gridy = 3;
//        datos.add(inicia, gbc);
//
//        datos.setBackground(Color.WHITE);
//        datos.setForeground(Color.WHITE);
//
//        panel.setFocusable(true);
//        panel.setFocusTraversalKeysEnabled(false);
//
//        inicia.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                try {
//                    controller.createSequence();
//                } catch (UnsupportedAudioFileException | QueueException | IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//        });
//    }
//
//    public void flashColor(Color color) {
//
//        Color originalColor = panel.getBackground();
//
//        panel.setBackground(color);
//
//        try {
//            espera();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        panel.setBackground(originalColor);
//    }
//
//    public Color obtenerColorEnPosicion(int x, int y) {
//        BufferedImage panelImage = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
//        panel.paint(panelImage.getGraphics());
//        return new Color(panelImage.getRGB(x, y));
//    }
//
//    public void closeWindow() {
//        if (confirmClose()) {
//            System.out.println("Cerrando la aplicación..");
//            System.exit(0);
//        }
//    }
//
//    public Color[] getColors() {
//        return COLORS;
//    }
//
//    private JPanel setupComponents(Container c) {
//        c.setLayout(new BorderLayout());
//        c.add(BorderLayout.CENTER, panel);
//        int i = 0;
//        if (model.getNivel() <= 5) {
//            i = 4;
//        } else if (model.getNivel() <= 10) {
//            i = 5;
//        } else if (model.getNivel() <= 15) {
//            i = 6;
//        } else if (model.getNivel() > 15) {
//            i = 7;
//        }
//
//        return controller.format(COLORS, i);
//    }
//
//    public boolean confirmClose() {
//        Object[] options = {"Sí", "No"};
//        return JOptionPane.showOptionDialog(this,
//                "¿Desea cerrar la aplicación?", "Confirmación",
//                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
//                options, options[0]) == JOptionPane.OK_OPTION;
//    }
//
//    public Container getPanel() {
//        return panel;
//    }
//
//    public void initColors() {
//        COLORS = new Color[]{
//                new Color(255, 0, 0),
//                new Color(33, 255, 0),
//                new Color(255, 243, 0),
//                new Color(0, 42, 255),
//                new Color(255, 136, 0),
//                new Color(223, 0, 255),
//                new Color(0, 245, 255, 255)
//        };
//    }
//
//    public int setTiempoRestante() {
//        if (model.getNivel() == 1) {
//            tiempoRestante = 30;
//        } else if (model.getNivel() <= 4) {
//            tiempoRestante = 25;
//        } else if (model.getNivel() <= 7) {
//            tiempoRestante = 20;
//        } else if (model.getNivel() <= 10) {
//            tiempoRestante = 15;
//        } else if (model.getNivel() <= 13) {
//            tiempoRestante = 10;
//        } else if (model.getNivel() <= 15) {
//            tiempoRestante = 5;
//        } else if (model.getNivel() > 15) {
//            tiempoRestante = 2;
//        }
//        return tiempoRestante;
//    }
//
//    public void espera() throws InterruptedException {
//        if (model.getNivel() == 1) {
//            sleep(5000);
//        } else if (model.getNivel() <= 4) {
//            sleep(4000);
//        } else if (model.getNivel() <= 7) {
//            sleep(3000);
//        } else if (model.getNivel() <= 10) {
//            sleep(2000);
//        } else if (model.getNivel() <= 13) {
//            sleep(1000);
//        } else if (model.getNivel() <= 15) {
//            sleep(500);
//        } else if (model.getNivel() > 15) {
//            sleep(100);
//        }
//    }
//
//    void temporizador() {
//        Thread thread = new Thread(() -> {
//            for (int i = tiempoRestante; i >= 0; i--) {
//                tiempo.setText(String.valueOf(i));
//                tiempoTotal = tiempoRestante - i;
//                try {
//                    sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                if (i == 0) {
//                    JOptionPane pane = new JOptionPane();
//                    pane.showMessageDialog(panel, "Te has quedado sin tiempo", "HAZ PERDIDO", JOptionPane.INFORMATION_MESSAGE);
//                    pane.addComponentListener(new ComponentAdapter() {
//                        @Override
//                        public void componentHidden(ComponentEvent e) {
//                            super.componentHidden(e);
//                            try {
//                                controller.fail(COLORS);
//                            } catch (UnsupportedAudioFileException | QueueException | IOException ex) {
//                                throw new RuntimeException(ex);
//                            }
//                        }
//                    });
//                }
//            }
//        });
//        thread.start();
//    }
//
//    @Override
//    public void update(Observable o, Object properties) {
//        int changedProps = (int) properties;
//
//        if ((changedProps & Model.PANEL) == Model.PANEL) {
//            panel = setupComponents(getContentPane());
//            panel.add(datos, BorderLayout.NORTH);
//        }
//
//        if ((changedProps & Model.SEQUENCE) == Model.SEQUENCE) {
//            controller.playNextColor(); // Llama al método para reproducir la secuencia de colores
//        }
//
//        if ((changedProps & Model.LEVEL) == Model.LEVEL) {
//            nivel.setText(String.valueOf(model.getNivel()));
//        }
//
//        if ((changedProps & Model.SCORE) == Model.SCORE) {
//            puntuacion.setText(String.valueOf(model.getScore()));
//        }
//        panel.revalidate();
//    }
//
//    public void sleep(int ms) throws InterruptedException {
//        Thread.sleep(ms);
//    }
//
//    public void setController(Controller controller) {
//        this.controller = controller;
//    }
//
//    public void setModel(Model model) {
//        this.model = model;
//    }
//
//    private Controller controller;
//    private Model model;
//}
//
