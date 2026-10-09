package battle;

import model.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battle {
    private final List<Card> playerDeck;
    private final List<Card> aiDeck;
    private final List<BattleListener> listeners = new ArrayList<>();
    private final Random random = new Random();

    private int playerScore = 0;
    private int aiScore = 0;

    public Battle(List<Card> playerDeck, List<Card> aiDeck) {
        this.playerDeck = playerDeck;
        this.aiDeck = aiDeck;
    }

    public void addBattleListener(BattleListener listener) {
        listeners.add(listener);
    }

    public void playTurn(int playerCardIndex) {
        if (playerScore >= 2 || aiScore >= 2) return;

        //detener el juego si se llega a 2 puntos
        Card playerCard = playerDeck.get(playerCardIndex);
        playerCard.setUsed(true);

        // Buscar cartas disponibles de la maquina
        List<Integer> availableAiIndices = new ArrayList<>();
        for (int i = 0; i < aiDeck.size(); i++) {
            if (!aiDeck.get(i).isUsed()) {
                availableAiIndices.add(i);
            }
        }

        if (availableAiIndices.isEmpty()) return;

        int chosenAiIndex = availableAiIndices.get(random.nextInt(availableAiIndices.size()));
        Card aiCard = aiDeck.get(chosenAiIndex);
        aiCard.setUsed(true);

        //Comparacion de atk y def si es empate
        String winner;
        if (playerCard.getAtk() > aiCard.getAtk())
        {
            playerScore++;
            winner = "Jugador";
        } else if (aiCard.getAtk() > playerCard.getAtk())
        {
            aiScore++;
            winner = "Maquina";
        } else {
            if (playerCard.getDef() > aiCard.getDef())
            {
                playerScore++;
                winner = "Jugador DEF";
            } else if (aiCard.getDef() > playerCard.getDef())
            {
                aiScore++;
                winner = "Máquina DEF";
            } else {
                winner = "Empate";
            }
        }

        // Notificar eventos a la interfaz pasando los índices y cartas jugadas
        for (BattleListener l : listeners)
        {
            l.onTurnRevealed(playerCardIndex, playerCard, chosenAiIndex, aiCard, winner);
            l.onScoreChanged(playerScore, aiScore);
        }

        if (playerScore >= 2)
        {
            for (BattleListener l : listeners) l.onDuelEnded("Jugador");
        } else if (aiScore >= 2) {
            for (BattleListener l : listeners) l.onDuelEnded("Máquina");
        }
    }
}