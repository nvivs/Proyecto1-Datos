package Proyecto.logic;

import Proyecto.Util.Array;

public class BestScore {
    private Array<Score> scores;
    int can;

    public BestScore(Array<Score> scores) {
        this.scores = scores;
        for(int i = 0; i < 3; i++){
            this.scores.add(new Score());
        }
        can = 0;
    }

    public BestScore() {
        scores = new Array<>(3);for(int i = 0; i < 3; i++){
            this.scores.add(new Score());
        }
        can = 0;
    }

    public Array<Score> getScores() {
        return scores;
    }

    public void setScores(Array<Score> scores) {
        this.scores = scores;
    }

    public BestScore add(Score score){
        try {
            scores.add(score);
        }catch (NullPointerException e){
            for(int i = 0; i < 3; i++) {
                if (scores.get(i).getScore() < score.getScore()) {
                    scores.add(score, i);
                }
            }
            scores.bubbleSort();
        }
        return this;
    }
}
