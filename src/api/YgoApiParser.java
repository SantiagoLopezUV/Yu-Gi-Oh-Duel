package api;

import exceptions.CardException;
import model.Card;
import org.json.JSONArray;
import org.json.JSONObject;

public class YgoApiParser {

    private YgoApiParser() { }

    public static Card parseCard(String jsonBody) throws Exception
    {
        // soporta objetos y arreglos
        JSONObject json;
        if (jsonBody.trim().startsWith("[")) {
            json = new JSONArray(jsonBody).getJSONObject(0);
        } else if (jsonBody.contains("\"data\"")) {
            json = new JSONObject(jsonBody).getJSONArray("data").getJSONObject(0);
        } else {
            json = new JSONObject(jsonBody);
        }

        String type = json.optString("type", "");
        // Validar que la carta sea tipo Monster
        if (!type.toLowerCase().contains("monster")) {
            throw new CardException.NotAMonster();
        }

        Card card = new Card();
        card.setId(json.getLong("id"));
        card.setName(json.getString("name"));
        card.setType(type);
        card.setAtk(json.optInt("atk", 0));
        card.setDef(json.optInt("def", 0));

        if (json.has("card_images")) {
            JSONArray images = json.getJSONArray("card_images");
            if (images.length() > 0) {
                JSONObject imgObj = images.getJSONObject(0);
                card.setImageUrl(imgObj.optString("image_url_small", imgObj.optString("image_url", "")));
            }
        }

        return card;
    }
}

