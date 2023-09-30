package Proyecto.logic;

public class Level {

    private int level;

    public Level() {
        level = 1;
    }

    public int getLevel() {
        return level;
    }

    public int increase(){
        level += 1;
        return level;
    }

    public void resetLevel(){
        level = 1;
    }
}
