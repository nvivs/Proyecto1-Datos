package Proyecto.MVC;

import Proyecto.Util.QueueException;
import Proyecto.logic.Sequence;
import Proyecto.logic.SequencePartColor;
import Proyecto.logic.Service;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import Proyecto.Util.Queue;

public class Model extends JPanel {
    private int currentIndex;
    private Color[] colors;
    int tam;

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    Sequence sequence;
    Random random;

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Model() {
        this.colors = colors;
        this.currentIndex = 0;
        tam = 4;
        initColors();
        repaint();
    }

    public Sequence getSequence() {
        return sequence;
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

   public void changeColor(Color color) {
        if(color.equals(SequencePartColor.instance().getColor("RED"))){
            colors[0] = SequencePartColor.instance().getColor("RED");
        }else if(color.equals(SequencePartColor.instance().getColor("GREEN"))){
            colors[1] = SequencePartColor.instance().getColor("GREEN");
        }else if(color.equals(SequencePartColor.instance().getColor("YELLOW"))){
            colors[2] = SequencePartColor.instance().getColor("YELLOW");
        }else if(color.equals(SequencePartColor.instance().getColor("BLUE"))){
            colors[3] = SequencePartColor.instance().getColor("BLUE");
        }else if(color.equals(SequencePartColor.instance().getColor("ORANGE"))){
            colors[4] = SequencePartColor.instance().getColor("ORANGE");
        }else if(color.equals(SequencePartColor.instance().getColor("PINK"))){
            colors[5] = SequencePartColor.instance().getColor("PINK");
        }else if(color.equals(SequencePartColor.instance().getColor("LIGTHBLUE"))){
            colors[6] = SequencePartColor.instance().getColor("LIGTHBLUE");
        }
    }

    public void showColor() {
        currentIndex++;
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
        g.drawString("Index: " + currentIndex++, 24,34);
        System.out.println("paintComponent called");
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
