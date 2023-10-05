package Proyecto.data;

import Proyecto.logic.BestScore;
import Proyecto.logic.Level;
import Proyecto.logic.Score;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {

    @XmlElement(name = "level")
    private Level level;

    @XmlElement(name = "bestScores")
    private BestScore scores;

    @XmlElement(name = "actualScore")
    private Score score;

    @XmlElement(name = "tiempoRestante")
    private int totalTime;

    @XmlElement(name = "tiempoDeReproduccion")
    private int reproductionTime;


    public Data() {
        level = new Level();
        scores = new BestScore();
        score = new Score();
        totalTime = 30;
        reproductionTime = 1000;
    }

    public Level getLevel() {
        return level;
    }

    public BestScore getScores() {
        return scores;
    }

    public Score getScore() {
        return score;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getReproductionTime() {
        return reproductionTime;
    }
}
