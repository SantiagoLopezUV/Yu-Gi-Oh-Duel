package exceptions;

public class CardException extends Exception {


    // jerarquía de las expeciones

    public CardException(String message) {
        super(message);
    }

    public static class NotFound extends CardException {
        public NotFound() {
            super("No se pudo obtener la carta");
        }
    }

    public static class ApiError extends CardException {
        public ApiError(int statusCode) {
            super("Error en el servidor de YGOProDeck");
        }
    }

    public static class NotAMonster extends CardException {
        public NotAMonster() {
            super("La carta obtenida no es de tipo Monstruo.");
        }
    }
}