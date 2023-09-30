package Proyecto.logic;

import Proyecto.Util.QueueException;
import Proyecto.data.Data;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

public class Service {

    private static Service theInstance;
    private Data data;

    public static Service instance(){
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    public Data getData() {
        return data;
    }

    private Service(){
        data = new Data();
    }

    public Sequence createSequence() throws UnsupportedAudioFileException, QueueException, IOException {
        return data.getSequence().createSequence(data.getLevel().getLevel());
    }

    public Sequence getSequence() {
        return data.getSequence();
    }

    public Level getLevel(){
        return data.getLevel();
    }

    public Score getScore(){
        return data.getScore();
    }

    public void check(Color color) throws Exception {
        if(data.getSequence().getSequence().head().getColor() == color){//si el color seleccionado coincide con
            data.getSequence().getSequence().dequeue();// la cabeza del queue
        }else {
            throw new Exception();// si falló
        }
        if(data.getSequence().getSequence().isEmpty()){// si llegó al final de la lista
            throw new QueueException();
        }
    }

    public int increaseLevel(){
        return data.getLevel().increase();
    }

    public int updateScore(int totalTime, int timeSpend){
        return data.getScore().updateScore(totalTime, timeSpend);
    }
}
