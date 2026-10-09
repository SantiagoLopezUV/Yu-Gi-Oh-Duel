package battle;

import model.Card;


// desacoplar la logica del duelo de la ui
// ui implementa y recibe notificaciones

public interface BattleListener {

    void onTurnRevealed(int playerIndex, Card playerCard, int aiIndex, Card aiCard, String winner);
    void onScoreChanged(int playerScore, int aiScore);
    void onDuelEnded(String winner);

}

