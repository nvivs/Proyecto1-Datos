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
    private JLabel Puntuación;
    private JLabel tiempo;
    private JLabel nivel;
    private JButton[] botones;
    int tiempoRestante;
    int tiempoTotal;
    private JMenu fileMenu;
    private JMenuItem quitItem;
    private JMenuBar mainMenu;
    private Color[] COLORS;

    public View() {

        COLORS = new Color[]{
                new Color(255, 0, 0),
                new Color(33, 255, 0),
                new Color(255, 243, 0),
                new Color(0, 42, 255),
                new Color(255, 136, 0),
                new Color(223, 0, 255),
                new Color(255, 255, 255)
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX();
                int y = e.getY();

                Color colorEnPosicion = obtenerColorEnPosicion(x, y);

                for (int i = 0; i < COLORS.length; i++) {
                    if (colorEnPosicion.equals(COLORS[i])) {
                        check(COLORS[i]);
                    }
                }
            }
        });
    }

    private Color obtenerColorEnPosicion(int x, int y){
        BufferedImage panelImage = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        panel.paint(panelImage.getGraphics());
        return new Color(panelImage.getRGB(x, y));
    }

    private void setupMenus() {
        mainMenu = new JMenuBar();

        mainMenu.add(fileMenu = new JMenu("Archivo"));
        fileMenu.add(quitItem = new JMenuItem("Salir"));

        setJMenuBar(mainMenu);

        quitItem.addActionListener(e -> {
            closeWindow();
        });
    }

    public void closeWindow() {
        if (confirmClose()) {
            System.out.println("Cerrando la aplicación..");
            System.exit(0);
        }
    }

    private JPanel setupComponents(Container c) {
        c.setLayout(new BorderLayout());
        setupMenus();
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

    public Component getPanel() {
        return panel;
    }

    public int reproduceSecuencia() throws InterruptedException {
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

        for(int i = 0; i < model.getSecuencia().count(); i++){
            colores[i] = model.getSecuencia().iterator().next().getColor();
            controller.format(colores, x);
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
            controller.format(COLORS, x);
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

        if((changedProps & Model.PANEL) == Model.PANEL){
            panel = setupComponents(getContentPane());
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
            nivel.setText(String.valueOf(model.getNivel()));
        }

        if((changedProps & Model.SCORE) == Model.SCORE){
            Puntuación.setText(String.valueOf(model.getScore()));
        }

//        if(run == 1){
//            run = 2;
//            try {
//                controller.actualizaBotones();
//            } catch (UnsupportedAudioFileException | QueueException | IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        if(run == 2){
//            try {
//                controller.createSequence();
//            } catch (UnsupportedAudioFileException | QueueException | IOException e) {
//                throw new RuntimeException(e);
//            }
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

//    public View() throws InterruptedException, UnsupportedAudioFileException, QueueException, IOException {
//        botones = new JButton[6];
//        for(int i = 0; i < botones.length; i++){
//            if(botones[i] != null){
//                int finalI = i;
//                botones[i].addMouseListener(new MouseAdapter() {
//                    @Override
//                    public void mouseClicked(MouseEvent e) {
//                        super.mouseClicked(e);
//                        check(finalI);
//                    }
//                });
//            }
//        }
//    }
//
//    Controller controller;
//    Model model;
//
//    public void setController(Controller controller) {
//        this.controller = controller;
//    }
//
//    public void setModel(Model model) {
//        this.model = model;
//        model.addObserver(this);
//    }
//
//    public void check(int i){
//        try {
//            controller.check((ImageIcon) botones[i].getIcon());
//        } catch (QueueException ex) {// si ganó
//            JOptionPane.showMessageDialog(panel, "Haz completado la secuencia correctamente", "HAZ GANADO", JOptionPane.INFORMATION_MESSAGE);
//            controller.win(tiempoTotal, tiempoRestante);
//        }
//        catch (RuntimeException ex){// si perdió
//            JOptionPane pane = new JOptionPane();
//            pane.showMessageDialog(panel, "Haz fallado al imitar la secuencia", "HAZ PERDIDO", JOptionPane.INFORMATION_MESSAGE);
//            pane.addComponentListener(new ComponentAdapter() {
//                @Override
//                public void componentHidden(ComponentEvent e) {
//                    super.componentHidden(e);
//                    try {
//                        controller.fail();
//                    } catch (UnsupportedAudioFileException | QueueException | IOException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                }
//            });
//        }
//        catch (UnsupportedAudioFileException | IOException ex){
//            throw new RuntimeException(ex);
//        }
//    }
//
//    public JPanel getPanel() {
//        return panel;
//    }
//
//    public void actualizaBotones(int tam) throws UnsupportedAudioFileException, QueueException, IOException {
//        setLayout(new GridLayout(2, 2));
//
//        for (int i = 0; i < tam; i++) {
//            getContentPane().add(botones[i]);
//        }
//    }
//
//    public int reproduceSecuencia() throws InterruptedException {
//        this.botones = model.getBotones();
//            for(int i = 0; i < model.getBotones().length; i++){
//                botones[i].setIcon(model.getSecuencia().iterator().next().getColor());
////            try(Clip clip = AudioSystem.getClip()){
////                clip.open(model.getSecuencia().iterator().next().getSound());
////                clip.start();
////                //Thread.sleep(clip.getMicrosecondLength() / 1_000);
////            } catch (IOException
////                     //| InterruptedException
////                     | LineUnavailableException ex) {
////                System.err.printf("Excepción: '%s'%n", ex.getMessage());
////            }
//            if(model.getNivel() == 1) {
//                sleep(5000);
//                tiempoRestante = 30;
//            }else if(model.getNivel() <= 4){
//                sleep(4000);
//                tiempoRestante = 25;
//            }else if(model.getNivel() <= 7){
//                sleep(3000);
//                tiempoRestante = 20;
//            }else if(model.getNivel() <= 10){
//                sleep(2000);
//                tiempoRestante = 15;
//            }else if(model.getNivel() <= 13){
//                sleep(1000);
//                tiempoRestante = 10;
//            }else if(model.getNivel() <= 15){
//                sleep(500);
//                tiempoRestante = 5;
//            }else if(model.getNivel() > 15){
//                sleep(100);
//                tiempoRestante = 2;
//            }
//            botones = model.getBotones();
//        }
//        return tiempoRestante;
//    }
//
//    private void temporizador(){
//        Thread thread = new Thread(() -> {
//            for (int i = tiempoRestante; i >= 0; i++) {
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
//                                controller.fail();
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
//        if((changedProps & Model.BOTONS) == Model.BOTONS){
//            botones = model.getBotones();
//            try {
//                actualizaBotones(botones.length);
//            } catch (UnsupportedAudioFileException | IOException | QueueException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        if((changedProps & Model.SEQUENCE) == Model.SEQUENCE){
//            try {
//                tiempoRestante = reproduceSecuencia();
//                tiempo.setText(String.valueOf(tiempoRestante));
//                temporizador();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        if((changedProps & Model.LEVEL) == Model.LEVEL){
//            controller.newLevel(botones);
//        }
//
//        if((changedProps & Model.SCORE) == Model.SCORE){
//            Puntuación.setText(String.valueOf(model.getScore()));
//        }
//
////        if(run == 1){
////            run = 2;
////            try {
////                controller.actualizaBotones();
////            } catch (UnsupportedAudioFileException | QueueException | IOException e) {
////                throw new RuntimeException(e);
////            }
////        }
////        if(run == 2){
////            try {
////                controller.createSequence();
////            } catch (UnsupportedAudioFileException | QueueException | IOException e) {
////                throw new RuntimeException(e);
////            }
//        panel.revalidate();
//    }
}
