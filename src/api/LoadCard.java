package api;

import exceptions.CardException;
import model.Card;

public class LoadCard {

    private LoadCard() { }

    public static Card loadRandomMonster() throws Exception {
        //intentos
        int attempts = 0;
        while (attempts < 10) {
            try {
                String jsonBody = YgoApiClient.fetchRandomCardJson();
                return YgoApiParser.parseCard(jsonBody);
            } catch (CardException.NotAMonster e) {
                attempts++; // Si no es un monstruo, intenta de nuevo
            }
        }
        throw new Exception("No se pudo obtener un monstruo válido después de varios intentos.");
    }
}