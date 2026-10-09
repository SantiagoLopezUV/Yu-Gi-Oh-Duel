package ui;

import javax.swing.*;

import api.LoadCard;
import battle.Battle;
import battle.BattleListener;
import model.Card;

import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class BoardCards extends JFrame implements BattleListener {

    public JPanel mainPanel;
    private JButton Bttn_StartBattle;
    private JButton Bttn_selectCardPlayer_1;
    private JButton Bttn_selectCardPlayer_2;
    private JButton Bttn_selectCardPlayer_3;
    private JPanel JPanelBottom;
    private JPanel JPanelCardsPlayer;
    private JPanel JPanelCardsAuto;
    private JLabel lbl_CardsPlayer;
    private JLabel lbl_CardsAuto;
    private JPanel JPanelBattle;
    private JScrollPane JScrollPane_logBattle;
    private JLabel lblScore;
    private JLabel imageCardPlayer_1;
    private JLabel imageCardPlayer_2;
    private JLabel imageCardPlayer_3;
    private JLabel imageCardAuto_1;
    private JLabel imageCardAuto_3;
    private JLabel imageCardAuto_2;
    private JLabel lblDetailsCardAuto_3;
    private JLabel lblDetailsCardAuto_1;
    private JLabel lblDetailsCardAuto_2;
    private JLabel lblDetailsCardPlayer_1;
    private JLabel lblDetailsCardPlayer_2;
    private JLabel lblDetailsCardPlayer_3;
    private JTextArea taLog;

    private final List<Card> playerDeck = new ArrayList<>();
    private final List<Card> aiDeck = new ArrayList<>();
    private Battle currentDuel;

    private JButton[] btnPlayerCards;
    private JLabel[] imageCardPlayer;
    private JLabel[] lblDetailsCardPlayer;
    private JLabel[] imageCardAuto;
    private JLabel[] lblDetailsCardAuto;

    public BoardCards() {
        super("Yu-Gi-Oh! Stadium - Duelo de Cartas");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        initUI();
    }


    private void initUI() {
        // 1. Agrupar componentes del formulario en arreglos
        btnPlayerCards = new JButton[]{ Bttn_selectCardPlayer_1, Bttn_selectCardPlayer_2, Bttn_selectCardPlayer_3 };
        imageCardPlayer = new JLabel[]{ imageCardPlayer_1, imageCardPlayer_2, imageCardPlayer_3 };
        lblDetailsCardPlayer = new JLabel[]{ lblDetailsCardPlayer_1, lblDetailsCardPlayer_2, lblDetailsCardPlayer_3 };

        imageCardAuto = new JLabel[]{ imageCardAuto_1, imageCardAuto_2, imageCardAuto_3 };
        lblDetailsCardAuto = new JLabel[]{ lblDetailsCardAuto_1, lblDetailsCardAuto_2, lblDetailsCardAuto_3 };

        //Configurar el JTextArea dentro del JScrollPane_logBattle
        taLog = new JTextArea(8, 50);
        taLog.setEditable(false);
        DefaultCaret caret = (DefaultCaret) taLog.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane_logBattle.setViewportView(taLog);

        // Deshabilitar botones de juego hasta cargar mazos
        for (JButton btn : btnPlayerCards) {
            btn.setEnabled(false);
        }

        // Asignar Listeners a los botones de selección
        for (int i = 0; i < btnPlayerCards.length; i++) {
            final int cardIndex = i;
            btnPlayerCards[i].addActionListener(e -> playTurn(cardIndex));
        }

        //Listener para iniciar duelo / cargar cartas
        Bttn_StartBattle.addActionListener(e -> loadDecksAsync());
    }

    private void loadDecksAsync() {
        Bttn_StartBattle.setEnabled(false);
        taLog.setText("Cargando 3 cartas aleatorias de monstruo por mazo desde la API...\n");

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                playerDeck.clear();
                aiDeck.clear();

                for (int i = 0; i < 3; i++) {
                    playerDeck.add(LoadCard.loadRandomMonster());
                    aiDeck.add(LoadCard.loadRandomMonster());
                }
                return null;
            }

            @Override
            protected void done() {
                Bttn_StartBattle.setEnabled(true);
                try {
                    get(); // Obtener resultado o relanzar excepciones de doInBackground
                    renderDecks();
                    currentDuel = new Battle(playerDeck, aiDeck);
                    currentDuel.addBattleListener(BoardCards.this);

                    for (JButton btn : btnPlayerCards) {
                        btn.setEnabled(true);
                    }
                    taLog.append("¡Mazos cargados con éxito! Selecciona una carta para comenzar.\n");
                } catch (Exception ex) {
                    Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
                    JOptionPane.showMessageDialog(BoardCards.this,
                            "Error al cargar las cartas: " + cause.getMessage(),
                            "Error de Red", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void renderDecks() {
        for (int i = 0; i < 3; i++) {
            // Renderizar cartas del Jugador
            Card pCard = playerDeck.get(i);
            lblDetailsCardPlayer[i].setText("<html><center><b>" + pCard.getName() + "</b><br>ATK: " + pCard.getAtk() + " | DEF: " + pCard.getDef() + "</center></html>");
            setLabelSprite(imageCardPlayer[i], pCard.getImageUrl());

            // Renderizar slots de la Máquina
            lblDetailsCardAuto[i].setText("<html><center><b>Carta Oculta</b><br>ATK: ??? | DEF: ???</center></html>");
            imageCardAuto[i].setIcon(null);
        }
    }

    private void setLabelSprite(JLabel label, String url) {
        if (url != null && !url.isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(URI.create(url).toURL());
                Image img = icon.getImage().getScaledInstance(100, 140, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(img));
            } catch (Exception ignored) {}
        }
    }

    private void playTurn(int cardIndex) {
        if (currentDuel != null) {
            btnPlayerCards[cardIndex].setEnabled(false);
            currentDuel.playTurn(cardIndex);
        }
    }

    @Override
    public void onTurnRevealed(int playerIndex, Card playerCard, int aiIndex, Card aiCard, String winner) {
        // 1. Log de la jugada
        taLog.append("Jugador jugó: " + playerCard.getName() + " (ATK: " + playerCard.getAtk() + ")\n");
        taLog.append("Máquina jugó: " + aiCard.getName() + " (ATK: " + aiCard.getAtk() + ")\n");
        taLog.append("--> Ganador de la ronda: " + winner + "\n\n");

        // 2. Voltear y revelar la carta correspondiente de la máquina en el formulario
        lblDetailsCardAuto[aiIndex].setText("<html><center><b>" + aiCard.getName() + "</b><br>ATK: " + aiCard.getAtk() + " | DEF: " + aiCard.getDef() + "</center></html>");
        setLabelSprite(imageCardAuto[aiIndex], aiCard.getImageUrl());
    }

    @Override
    public void onScoreChanged(int playerScore, int aiScore) {
        lblScore.setText("Puntaje: Jugador " + playerScore + " - " + aiScore + " Máquina");
    }

    @Override
    public void onDuelEnded(String winner) {
        taLog.append("¡" + winner.toUpperCase() + " GANA EL DUELO!\n");
        for (JButton btn : btnPlayerCards) {
            btn.setEnabled(false);
        }
        JOptionPane.showMessageDialog(this, "¡" + winner + " ha ganado el duelo!", "Duelo Finalizado", JOptionPane.INFORMATION_MESSAGE);
    }

}
