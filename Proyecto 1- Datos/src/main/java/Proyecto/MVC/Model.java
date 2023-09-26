package Proyecto.MVC;

import Proyecto.logic.Sequence;
import Proyecto.logic.SequencePart;
import Proyecto.logic.SequencePartColor;
import Proyecto.logic.Service;

import javax.swing.*;
import java.awt.*;

public class Model extends JPanel {
    private int currentIndex;
    private Color[] colors;
    int tam;

    public Model() {
        this.colors = colors;
        this.currentIndex = 0;
        tam = 4;
        initColors();
        repaint();
    }

    public void initColors(){
        colors = new Color[]{
                new Color(255, 0, 0),
                new Color(33, 255, 0),
                new Color(255, 243, 0),
                new Color(0, 42, 255),
                new Color(255, 136, 0),
                new Color(223, 0, 255),
                new Color(0, 245, 255, 255)
        };
    }

    public void setSecuencia(Sequence secuencia) {
        for(SequencePart i : secuencia.getSequence()){
            if(i.getColor().equals(SequencePartColor.instance().getColor("RED"))){
                colors[0] = SequencePartColor.instance().getColor("RED");
            }else if(i.getColor().equals(SequencePartColor.instance().getColor("GREEN"))){
                colors[1] = SequencePartColor.instance().getColor("GREEN");
            }else if(i.getColor().equals(SequencePartColor.instance().getColor("YELLOW"))){
                colors[2] = SequencePartColor.instance().getColor("YELLOW");
            }else if(i.getColor().equals(SequencePartColor.instance().getColor("BLUE"))){
                colors[3] = SequencePartColor.instance().getColor("BLUE");
            }else if(i.getColor().equals(SequencePartColor.instance().getColor("ORANGE"))){
                colors[4] = SequencePartColor.instance().getColor("ORANGE");
            }else if(i.getColor().equals(SequencePartColor.instance().getColor("PINK"))){
                colors[5] = SequencePartColor.instance().getColor("PINK");
            }else if(i.getColor().equals(SequencePartColor.instance().getColor("LIGTHBLUE"))){
                colors[6] = SequencePartColor.instance().getColor("LIGTHBLUE");
            }
        }
    }

    public void showColor() {
        if (currentIndex < tam) {
            // Muestra el color actual en el panel
            //setBackground(colors[currentIndex]);
            repaint();
            currentIndex++;
        }
    }

    public void x(){

    }

    public void reset() {
        // Reinicia el panel
        //setBackground(Color.WHITE);
        initColors();
        currentIndex = 0;
        repaint();
    }

    public void paintComponent (Graphics bg){
        super.paintComponent(bg);
        Graphics2D g = (Graphics2D) bg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        g.setColor(Color.WHITE);
        g.drawLine(cx, 0, cx, getHeight());
        g.drawLine(0, cy, getWidth(), cy);

        int s = (int) (0.80 * Math.min(getWidth(), getHeight()));

        int n = tam;
        for (int i = 0; i < n; i++) {
            drawWedge(g, cx, cy, s, (i * 360 - 180) / n, 360 / n, colors[i]);
        }

        g.setColor(Color.DARK_GRAY);
        g.fillOval(cx - s / 6, cy - s / 6, s / 3, s / 3);
    }

    private void drawWedge (Graphics2D g,int cx, int cy, int s, int start, int end, Color c){

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
}
