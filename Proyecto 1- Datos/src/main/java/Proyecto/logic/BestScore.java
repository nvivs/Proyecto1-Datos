package Proyecto.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class BestScore {

    @XmlElementWrapper(name = "scores")
    @XmlElement(name = "score")
    private Score[] scores;

    public BestScore() {
        scores = new Score[3];
        for(int i = 0; i < 3; i++){
            this.scores[i] = new Score();
        }
    }

    public BestScore add(Score data) { // O(n)
        scores[2] = new Score(data.getScore());
        bubbleSort();
        return this;
    }

    public Score get(int p) { // O(1)
        if (!(p < 3)) {
            throw new IndexOutOfBoundsException();
        }
        return scores[p];
    }

    public void bubbleSort() {
        boolean swapped;
        int n = 3;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (scores[i - 1].getScore() < scores[i].getScore()) {
                    // Swap elements[i-1] and elements[i]
                    Score temp = scores[i - 1];
                    scores[i - 1] = scores[i];
                    scores[i] = temp;
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
    }
}
