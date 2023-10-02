package Proyecto.logic;

public class BestScore {
    private Score[] scores;

    public BestScore(Score[] scores) {
        this.scores = scores;
        for(int i = 0; i < 3; i++){
            this.scores[i] = new Score();
        }
    }

    public BestScore() {
        scores = new Score[3];
        for(int i = 0; i < 3; i++){
            this.scores[i] = new Score();
        }
    }

    public Score[] getScores() {
        return scores;
    }

    public void setScores(Score[] scores) {
        this.scores = scores;
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
            n--; // Reduce the array size for optimization
        } while (swapped);
    }
}
