package Proyecto.logic;

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

    public void setScore(int score) {
        this.score = score;
    }

    public void updateScore(int totalTime, int timeSpend){
        score += 100;
        float x = (float) totalTime / 4;//un cuarto de tiempo total

        if(timeSpend < Math.pow(x, 3)){// si duró menos de 3/4 del tiempo total
            score += 15;
        }else if(timeSpend < Math.pow(x, 2)){// si duró menos de la mitad del tiempo total
            score += 10;
        }else if(timeSpend < x){// si duró menos de 1 cuarto del tiempo total
            score += 5;
        }
    }
}