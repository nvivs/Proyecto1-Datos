package Proyecto.logic;

import Proyecto.Util.QueueException;
import Proyecto.Util.XmlPersister;
import Proyecto.data.Data;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Service {

    private static Service theInstance;
    private Data data;

    public static Service instance(){
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private Service(){
        try{
            data = XmlPersister.instance().load();
        }catch (Exception e) {
            data = new Data();
        }
    }

    public void stop(){
        try{
            XmlPersister.instance().store(data);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public Sequence createSequence() throws UnsupportedAudioFileException, QueueException, IOException, LineUnavailableException {
        return Sequence.instance().createSequence(data.getLevel().getLevel());
    }

    public int getTotalTime(){
        return data.getTotalTime();
    }

    public int getReproductionTime(){
        return data.getReproductionTime();
    }

    public Level getLevel(){
        return data.getLevel();
    }

    public BestScore getScores(){
        return data.getScores();
    }

    public Level increaseLevel(){
        return data.getLevel().increase();
    }

    public Level resetLevel(){
        return data.getLevel().resetLevel();
    }

    public BestScore updateScores(){
        return data.getScores().add(data.getScore());
    }

    public Score getScore(){
        return data.getScore();
    }

    public Score updateScore(int totalTime, int timeSpend){
        return data.getScore().updateScore(totalTime, timeSpend);
    }

    public Score resetScore(){
        return data.getScore().resetScore();
    }

    public int setTotalTime(int i){
        return data.setTotalTime(i);
    }
}
