package Proyecto.data;

import Proyecto.Util.Queue;
import Proyecto.logic.Level;
import Proyecto.logic.Score;
import Proyecto.logic.Sequence;
import Proyecto.logic.SequencePart;

public class Data {
    private Sequence sequence;
    private Level level;
    private Score score;

    public Data() {
        sequence = new Sequence();
        level = new Level();
        score = new Score();
    }

    public Sequence getSequence() {
        return sequence;
    }

    public Level getLevel() {
        return level;
    }

    public Score getScore() {
        return score;
    }
}
