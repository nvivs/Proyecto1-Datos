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

    public Controller(View view, Model model, Color[] colors) throws UnsupportedAudioFileException, QueueException, IOException {
        model.init(colors);
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public JPanel format(Color[] colors, int i){
        return model.format(colors, i);
    }

//    public void reproduceSecuencia(){
//        if(model.getSequenceIndex() > 0){
//            int x = 0;
//            if(model.getNivel() <= 5) {// 4 colores
//                x = 4;
//            }else if(model.getNivel() <= 10){// 5 colores
//                x = 5;
//            }else if(model.getNivel() <= 15){// 6 colores
//                x = 6;
//            }else if(model.getNivel() > 15){// 7 colores
//                x = 7;
//            }
//            Color[] colores = view.COLORS;
//
//            colores[model.getSecuencia().getIndex()] = model.getSecuencia().getSequence().iterator().next().getColor();
//            model.setMainPanel(model.format(colores, x));
//            model.commit();
//
//        }else {
//            model.changedProps = Model.NONE;
//            view.tiempoRestante = view.setTiempoRestante();
//            view.Tiempo.setText(String.valueOf(view.tiempoRestante));
//            view.temporizador();
//        }
//    }
//
//    public void reinicia(){
//        int x = 0;
//        if(model.getNivel() <= 5) {// 4 colores
//            x = 4;
//        }else if(model.getNivel() <= 10){// 5 colores
//            x = 5;
//        }else if(model.getNivel() <= 15){// 6 colores
//            x = 6;
//        }else if(model.getNivel() > 15){// 7 colores
//            x = 7;
//        }
//        model.setSequenceIndex(model.getSequenceIndex() - 1);
//        view.initColors();
//        view.panel = model.format(view.COLORS, x);
//        model.setMainPanel(view.panel);
//        model.commit();
//    }

    public void createSequence() throws UnsupportedAudioFileException, QueueException, IOException {
        model.setSecuencia(Service.instance().createSequence());
        model.setSequenceIndex(Service.instance().createSequence().getSequence().count());
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
        format(colors, 4);
        createSequence();
    }

    public void win(int totalTime, int timeSpend, int x, Color[] colors){
        model.setNivel(Service.instance().increaseLevel());
        model.setScore(Service.instance().updateScore(totalTime, timeSpend));
        model.format(colors, x);
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
