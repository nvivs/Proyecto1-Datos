package Proyecto.data;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;


@XmlRootElement
public class Configuration {

    @XmlElement
    private List<Integer> levelTimes;

    public List<Integer> getLevelTimes() {
        return levelTimes;
    }
    public void setLevelTimes(List<Integer> levelTimes) {
        this.levelTimes = levelTimes;
    }
}