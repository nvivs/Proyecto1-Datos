////package Proyecto.MVC;
////
////import Proyecto.logic.Sequence;
////import Proyecto.logic.SequencePart;
////
////import javax.swing.*;
////import java.awt.*;
////import java.util.Observer;
////import Proyecto.Util.Queue;
////
////public class  extends java.util.Observable{
////
////    private Sequence secuencia;
////    private JPanel mainPanel;
////    private int nivel;
////    private int changedProps = NONE;
////    private int score;
////    private int sequenceIndex;
////
////    public static int NONE = 0;
////    public static int SEQUENCE = 1;
////    public static int LEVEL = 2;
////    public static int SCORE = 4;
////    public static int PANEL = 8;
////
////    @Override
////    public synchronized void addObserver(Observer o) {
////        super.addObserver(o);
////        commit();
////    }
////
////    public void commit(){
////        setChanged();
////        notifyObservers(changedProps);
////        changedProps = NONE;
////    }
////
////    public () {
////    }
////
////    public void init(Color[] colors){
////        mainPanel = format(colors, 4);
////        secuencia = new Sequence();
////        nivel = 1;
////        score = 0;
////        sequenceIndex = 0;
////        changedProps = 8;
////    }
////
////    public JPanel format(Color[] COLORS, int i){
////        mainPanel = new JPanel(new BorderLayout()) {
////            @Override
////            public void paintComponent(Graphics bg) {
////                super.paintComponent(bg);
////                Graphics2D g = (Graphics2D) bg;
////                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
////                        RenderingHints.VALUE_ANTIALIAS_ON);
////
////                int cx = getWidth() / 2;
////                int cy = getHeight() / 2;
////                g.setColor(Color.WHITE);
////                g.drawLine(cx, 0, cx, getHeight());
////                g.drawLine(0, cy, getWidth(), cy);
////
////                int s = (int) (0.80 * Math.min(getWidth(), getHeight()));
////
////                int n = i;
////                for (int i = 0; i < n; i++) {
////                    drawWedge(g, cx, cy, s, (i * 360 - 180) / n, 360 / n, COLORS[i]);
////                }
////
////                g.setColor(Color.DARK_GRAY);
////                g.fillOval(cx - s / 6, cy - s / 6, s / 3, s / 3);
////            }
////
////            private void drawWedge(Graphics2D g, int cx, int cy, int s, int start, int end, Color c) {
////
////                double r = Math.PI / 180.0;
////                int x0 = (int) (cx + s * 0.5 * Math.cos(-start * r));
////                int y0 = (int) (cy + s * 0.5 * Math.sin(-start * r));
////                int x1 = (int) (cx + s * 0.5 * Math.cos(-(start + end) * r));
////                int y1 = (int) (cy + s * 0.5 * Math.sin(-(start + end) * r));
////
////                g.setColor(c);
////                g.fillArc(cx - s / 2, cy - s / 2, s, s, start, end);
////
////                g.setColor(Color.DARK_GRAY);
////                g.setStroke(new BasicStroke(16f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
////                g.drawLine(cx, cy, x0, y0);
////                g.drawLine(cx, cy, x1, y1);
////                g.drawArc(cx - s / 2, cy - s / 2, s, s, start, end);
////            }
////        };
////
////        mainPanel.setBackground(Color.WHITE);
////        mainPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
////        mainPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
////
////        return mainPanel;
////    }
////
////
////
////    public Sequence getSecuencia() {
////        return secuencia;
////    }
////
////    public void setSecuencia(Sequence secuencia) {
////        this.secuencia = secuencia;
////        changedProps += SEQUENCE;
////    }
////
////    public int getSequenceIndex() {
////        return sequenceIndex;
////    }
////
////    public void setSequenceIndex(int sequenceIndex) {
////        this.sequenceIndex = sequenceIndex;
////    }
////
////    public int getNivel() {
////        return nivel;
////    }
////
////    public void setNivel(int nivel) {
////        this.nivel = nivel;
////        changedProps += LEVEL;
////    }
////
////    public int getScore() {
////        return score;
////    }
////
////    public void setScore(int score) {
////        this.score = score;
////        changedProps += SCORE;
////    }
////
////    public JPanel getMainPanel() {
////        return mainPanel;
////    }
////
////    public void setMainPanel(JPanel mainPanel) {
////        this.mainPanel = mainPanel;
////        changedProps += PANEL;
////        mainPanel.repaint();
////    }
////}
//package Proyecto.MVC;
//
//import java.awt.*;
//import java.util.ArrayList;
//
//public class Model extends java.util.Observable {
//    private GameState gameState;
//    private ColorSequence colorSequence;
//    private List<Color> playerInput;
//    private int currentLevel;
//    private int score;
//
//    public Model() {
//        gameState = GameState.NOT_STARTED;
//        colorSequence = new ColorSequence();
//        playerInput = new ArrayList<>();
//        currentLevel = 1;
//        score = 0;
//    }
//
//    public void startNewGame() {
//        gameState = GameState.PLAYING;
//        colorSequence.resetSequence();
//        playerInput.clear();
//        currentLevel = 1;
//        score = 0;
//        notifyObservers("new_game");
//    }
//
//    public void playColorSequence() {
//        // Lógica para reproducir la secuencia de colores
//        gameState = GameState.PLAYING_SEQUENCE;
//        // Obtén la secuencia de colores del o
//        List<Color> sequence = colorSequence.getSequenceForLevel(currentLevel);
//        // Notifica a la vista para reproducir la secuencia
//        notifyObservers(sequence);
//    }
//
//    public void handlePlayerInput(Color color) {
//        if (gameState == GameState.PLAYING_SEQUENCE) {
//            return; // No permitir entrada del jugador durante la secuencia
//        }
//
//        // Manejar la entrada del jugador aquí
//        playerInput.add(color);
//
//        if (color.equals(colorSequence.getColorAt(currentLevel - 1))) {
//            // El jugador seleccionó el color correcto en el nivel actual
//            if (playerInput.size() == currentLevel) {
//                // El jugador completó correctamente el nivel actual
//                score += ScoreCalculator.calculateScore(playerInput.size());
//                currentLevel++;
//
//                if (currentLevel > colorSequence.getMaxLevel()) {
//                    // El jugador ha ganado el juego
//                    gameState = GameState.WIN;
//                    notifyObservers("win_game");
//                } else {
//                    // Continuar con el siguiente nivel
//                    gameState = GameState.PLAYING;
//                    notifyObservers("next_level");
//                }
//            }
//        } else {
//            // El jugador cometió un error
//            gameState = GameState.GAME_OVER;
//            notifyObservers("game_over");
//        }
//    }
//
//    public int getScore() {
//        return score;
//    }
//
//    public GameState getGameState() {
//        return gameState;
//    }
//}