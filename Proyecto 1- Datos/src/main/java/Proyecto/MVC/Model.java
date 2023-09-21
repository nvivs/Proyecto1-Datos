package Proyecto.MVC;

import Proyecto.logic.SequencePart;

import javax.swing.*;
import java.util.List;
import java.util.Observer;
import Proyecto.Util.Queue;
import Proyecto.logic.SequencePartColor;

public class Model extends java.util.Observable{

    JButton[] botones;
    Queue<SequencePart> secuencia;
    int nivel;
    int changedProps = NONE;
    int score;

    public static int NONE = 0;
    public static int SEQUENCE = 1;
    public static int BOTONS = 2;
    public static int LEVEL = 4;
    public static int SCORE = 8;

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        commit();
    }

    public void commit(){
        setChanged();
        notifyObservers(changedProps);
        changedProps = NONE;
    }

    public Model() {
    }

    public void init(){
        botones = new JButton[4];
        botones[0].setIcon(SequencePartColor.instance().getRED());
        botones[1].setIcon(SequencePartColor.instance().getGREEN());
        botones[2].setIcon(SequencePartColor.instance().getYELLOW());
        botones[3].setIcon(SequencePartColor.instance().getBLUE());

        this.secuencia = null;
        this.nivel = 1;
        this.score = 0;
        changedProps = NONE;
        setBotones(botones);
        commit();
    }

    public JButton[] getBotones() {
        return botones;
    }

    public void setBotones(JButton[] botones) {
        this.botones = botones;
        changedProps += BOTONS;
    }

    public Queue<SequencePart> getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(Queue<SequencePart> secuencia) {
        this.secuencia = secuencia;
        changedProps += SEQUENCE;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
        changedProps += LEVEL;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
