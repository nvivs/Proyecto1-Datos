package Proyecto.MVC;


import Proyecto.Util.QueueException;
import Proyecto.logic.Service;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model, Color[] colors) {
        model.init(colors);
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public JPanel format(Color[] colors, int i){
        return model.format(colors, i);
    }

    public void createSequence() throws UnsupportedAudioFileException, QueueException, IOException {
        model.setSecuencia(Service.instance().createSequence());
        model.commit();
    }

    public void check(Color color) throws QueueException, UnsupportedAudioFileException, IOException {
        try {
            Service.instance().check(color);
        }catch (QueueException e){// llego al final (acertó)
            throw new QueueException();
        }
        catch (Exception e){// si falló
            throw new RuntimeException();
        }
    }

    public void fail(Color[] colors) throws UnsupportedAudioFileException, QueueException, IOException {
        model.init(colors);
        createSequence();
    }

    public void win(int totalTime, int timeSpend){
        model.setNivel(Service.instance().increaseLevel());
        model.setScore(Service.instance().updateScore(totalTime, timeSpend));
        model.commit();
    }

    public void newLevel(JButton[] botones){
        botones[0].setBounds(20, 30, 500, 600);
        botones[1].setBounds(210, 30, 500, 600);
        botones[2].setBounds(20, 225, 500, 600);
        botones[3].setBounds(210, 225, 500, 600);
        //se debe actualizar la secuencia
        //se deben actualizar los botones
    }
}
