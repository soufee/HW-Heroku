package ci.ashamaz.hwheroku.downloader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public abstract class AbstractJsonDownloader {
    //use only for get requests
    protected JsonObject downloadJson(String url) {
        try {
            String response = getArrayOfEventsByURL(url);
            return JsonParser.parseString(response).getAsJsonObject();
        } catch (Exception e) {
            throw new RuntimeException("Could not download json", e);
        }
    }

    private String getArrayOfEventsByURL(String url) {
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<JsonNode> response = Unirest.get(url).header("Cookie", "cud=JeRzC2NZszVmenQ/B5NnAg==").asJson();
            return response.getBody().toString();
        } catch (Exception e) {
            throw new RuntimeException("Could not get object from external service. Maybe you need to update cookie");
        }

    }
}
