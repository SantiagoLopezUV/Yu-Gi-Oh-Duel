package api;

import exceptions.CardException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class YgoApiClient {

    private static final String url = "https://db.ygoprodeck.com/api/v7/randomcard.php";
    private static final HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    public static String fetchRandomCardJson() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        //envio de la petición
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //se ejecuta primero, si no encuentra nada devuelve la excepcion
        if (response.statusCode() == 404) {
            throw new CardException.NotFound();
        }
        if (response.statusCode() != 200) {//si cualquier otro diferente de 200 se interrumpe
            throw new CardException.ApiError(response.statusCode());
        }
    //si no se cumple lo anterior, devuelve el JSON
        return response.body();
    }
}