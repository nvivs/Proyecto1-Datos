package Proyecto.logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Level {

    private int level;

    public Level() {
        level = 1;
    }

    public int getLevel() {
        return level;
    }

    public Level increase(){
        level += 1;
        return this;
    }

    public Level resetLevel(){
        level = 1;
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(level);
    }
}
