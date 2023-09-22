package Proyecto.logic;

import Proyecto.Util.Queue;
import Proyecto.Util.QueueException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Random;

public class Sequence {
    private Queue<SequencePart> sequence;

    public Sequence(Queue<SequencePart> sequence) {
        this.sequence = sequence;
    }

    public Sequence() {
        new Sequence(null);
    }

    public Queue<SequencePart> getSequence() {
        return sequence;
    }

    public void setSequence(Queue<SequencePart> sequence) {
        this.sequence = sequence;
    }

    public Queue<SequencePart> createSequence(int level) throws QueueException, UnsupportedAudioFileException, IOException {
        int index1;
        int index2;
        this.updateSequenceLength(level + 1);

        if(level < 5){
            index1 = new Random().nextInt(4) + 4;
            index2 = index1;
        }else{
            index1 = new Random().nextInt(4) + 4;
            index2 = new Random().nextInt(4) + 4;
        }
        for(int i = 0; i < level; i++) {
            sequence.enqueue(new SequencePart
                    (SequencePartColor.instance().getColor(index1)
                            , SequencePartSound.instance().getSound(index2)));
        }
        return sequence;
    }

    public void updateSequenceLength(int level) {
        sequence = new Queue<>(level);
    }
}
