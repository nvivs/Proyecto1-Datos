package Proyecto.logic;

public class Level {

    private int level;

    public Level() {
        level = 1;
    }

    public int getLevel() {
        return level;
    }

    public void increase(){
        level += 1;
    }
}
