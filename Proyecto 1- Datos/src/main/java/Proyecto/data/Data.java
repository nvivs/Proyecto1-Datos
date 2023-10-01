package Proyecto.data;

import Proyecto.logic.BestScore;
import Proyecto.logic.Level;
import Proyecto.logic.Score;
import Proyecto.logic.Sequence;

public class Data {
    private Sequence sequence;
    private Level level;
    private BestScore scores;

    public Data() {
        sequence = new Sequence();
        level = new Level();
        scores = new BestScore();
    }

    public Sequence getSequence() {
        return sequence;
    }

    public Level getLevel() {
        return level;
    }

    public BestScore getScores() {
        return scores;
    }
}
