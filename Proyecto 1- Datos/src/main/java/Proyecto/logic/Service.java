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

    public void createSequence() throws UnsupportedAudioFileException, QueueException, IOException {
        data.getSequence().createSequence(data.getLevel().getLevel());
    }

    public Sequence getSequence() {
        return data.getSequence();
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

    public void increaseLevel(){
        data.getLevel().increase();
    }

    public void updateScore(int totalTime, int timeSpend){
        data.getScore().updateScore(totalTime, timeSpend);
    }
}
