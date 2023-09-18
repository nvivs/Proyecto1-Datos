package Proyecto.MVC;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame{
    private JButton[] botones;
    private JPanel panel;
    private JMenuBar mainMenu;
    private JMenu fileMenu;
    private JMenuItem quitItem;

    public View() {
        setupComponents(getContentPane());
    }

//    private void creaPanel(){
//        int radio = 100;
//        int xCentro = panel.getWidth() / 2;
//        int yCentro = panel.getWidth() / 2;
//        int anguloInicio = 0;
//        panel.setLayout(new BorderLayout());
//
//        botones = new JButton[4];
//
//        // Define los colores para cada sector
//        Color[] sectorColores = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW };
//
//        // Crea un botón personalizado para cada sector
//        for (int i = 0; i < 4; i++) {
//            botones[i] = new JButton();
//            botones[i].setBackground(sectorColores[i]);
//            botones[i].setOpaque(true);
//            botones[i].setBorderPainted(false);
//            botones[i].setFocusPainted(false);
//
//            final int index = i;
//            botones[i].addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    // Aquí puedes manejar la acción para cada color
//                    System.out.println("Botón " + index + " presionado.");
//                }
//            });
//
//            panel.add(botones[i], BorderLayout.CENTER);
//        }
//    }

//        panel = new JPanel(){
//            @Override
//            public void paintComponent(Graphics g){
//                int radio = 100;
//                int xCentro = panel.getWidth() / 2;
//                int yCentro = panel.getHeight() / 2;
//                int anguloInicio = 0;
//
//                Graphics2D g2d = (Graphics2D) g;
//
//                // Dibuja los cuatro sectores de colores
//                for (int i = 0; i < 4; i++) {
//                    int anguloArco = 90; // Cada sector abarca 90 grados
//
//                    g2d.setColor(botones[i].getBackground());
//                    g2d.fill(new Arc2D.Double(xCentro - radio, yCentro - radio, radio * 2, radio * 2, anguloInicio, anguloArco, Arc2D.PIE));
//
//                    // Incrementa el ángulo de inicio para el siguiente sector
//                    anguloInicio += anguloArco;
//                }
//
//                panel.paintComponents(g2d);
//            }
//        };

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
//            mainControl.remove(this);
//            mainControl.closeApplication();
        }
    }

    private void setupComponents(Container c) {
        c.setLayout(new BorderLayout());

        setupMenus();

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics bg) {
                super.paintComponent(bg);
                Graphics2D g = (Graphics2D) bg;
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                int cx = getWidth() / 2;
                int cy = getHeight() / 2;

                g.setColor(Color.CYAN.darker());
                //g.setColor(Color.WHITE);
                g.drawLine(cx, 0, cx, getHeight());
                g.drawLine(0, cy, getWidth(), cy);

                int s = (int) (0.80 * Math.min(getWidth(), getHeight()));

                int n = 4;
                for (int i = 0; i < n; i++) {
                    drawWedge(g, cx, cy, s, (i * 360 - 180) / n, 360 / n, COLORS[i]);
                }

                g.setColor(Color.DARK_GRAY);
                g.fillOval(cx - s / 6, cy - s / 6, s / 3, s / 3);
            }

            private void drawWedge(Graphics2D g, int cx, int cy, int s, int start, int end, Color c) {

                double r = Math.PI / 180.0;
                int x0 = (int) (cx + s * 0.5 * Math.cos(-start * r));
                int y0 = (int) (cy + s * 0.5 * Math.sin(-start * r));
                int x1 = (int) (cx + s * 0.5 * Math.cos(-(start + end) * r));
                int y1 = (int) (cy + s * 0.5 * Math.sin(-(start + end) * r));

                g.setColor(c);
                g.fillArc(cx - s / 2, cy - s / 2, s, s, start, end);

                g.setColor(Color.DARK_GRAY);
                g.setStroke(new BasicStroke(16f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
                g.drawLine(cx, cy, x0, y0);
                g.drawLine(cx, cy, x1, y1);
                g.drawArc(cx - s / 2, cy - s / 2, s, s, start, end);
            }

            Color[] COLORS = new Color[]{
                    Color.RED,
                    Color.GREEN,
                    Color.YELLOW,
                    new Color(72, 72, 255),
//                Color.RED.darker(),
//                Color.GREEN.darker(),
//                Color.YELLOW.darker(),
//                new Color(72, 72, 255).darker(),
                    Color.ORANGE,
                    Color.PINK,
                    Color.WHITE
            };

        };

        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        c.add(BorderLayout.CENTER, panel);

        //c.add(BorderLayout.PAGE_END, status = new StatusBar());

        // Define los eventos asociados a cada componente de la ventana.
        // Los menús son configurados en un método separado.
        //
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
}
