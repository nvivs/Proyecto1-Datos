package Proyecto.logic;

import Proyecto.Util.Queue;
import Proyecto.Util.QueueException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Random;

public class Sequence {
    private Queue<SequencePart> sequence;
    private int index;

    public Sequence(Queue<SequencePart> sequence) {
        this.sequence = sequence;
    }

    public Sequence() {
        new Sequence(null);
    }

    public Queue<SequencePart> getSequence() {
        return sequence;
    }

    public int getSize(){
        return sequence.count();
    }
    public void setSequence(Queue<SequencePart> sequence) {
        this.sequence = sequence;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Sequence createSequence(int level) throws QueueException, UnsupportedAudioFileException, IOException {
        int index1;
        int index2;
        this.updateSequenceLength(level + 1);
        int x = 0;
        if(level <= 5) {// 4 colores
            x = 4;
        }else if(level <= 10){// 5 colores
            x = 5;
        }else if(level <= 15){// 6 colores
            x = 6;
        }else if(level > 15){// 7 colores
            x = 7;
        }
        int anterior = -1;

        for(int i = 0; i < level; i++) {
            if(level < 5){
                index1 = new Random().nextInt(x);
                while(anterior == index1){
                    index1 = new Random().nextInt(x);
                }
                index2 = index1;
            }else{
                index1 = new Random().nextInt(x);
                while(anterior == index1){
                    index1 = new Random().nextInt(x);
                }
                index2 = new Random().nextInt(x);
            }
            sequence.enqueue(new SequencePart
                    (SequencePartColor.instance().getColor(index1)
                            , SequencePartSound.instance().getSound(index2)));
            anterior = index1;
        }
       // index = index1;
        return this;
    }

    public void updateSequenceLength(int level) {
        sequence = new Queue<>(level);
    }
}
