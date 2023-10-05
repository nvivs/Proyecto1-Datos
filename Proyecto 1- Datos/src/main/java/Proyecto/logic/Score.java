package Proyecto.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Score {

    private int score;

    public Score(int score) {
        this.score = score;
    }

    public Score() {
        new Score(0);
    }

    public int getScore() {
        return score;
    }

    public Score updateScore(int totalTime, int timeSpend){
        score += 100;
        float x = (float) totalTime / 4;//un cuarto de tiempo total
        int time = totalTime - timeSpend;

        if(time > x * 3){// si duró menos de 3/4 del tiempo total
            score += 15;
        }else if(time > x * 2){// si duró menos de la mitad del tiempo total
            score += 10;
        }else if(time > x){// si duró menos de 1 cuarto del tiempo total
            score += 5;
        }
        return this;
    }

    public Score resetScore(){
        score = 0;
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(score);
    }
}
