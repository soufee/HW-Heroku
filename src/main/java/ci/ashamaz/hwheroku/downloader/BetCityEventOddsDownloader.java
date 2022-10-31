package ci.ashamaz.hwheroku.downloader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BetCityEventOddsDownloader extends AbstractJsonDownloader {
    @Value("${match.main.url.template}")
    private String MAIN_URL;
    @Value("${match.additional.url.template}")
    private String ADDITIONAL_ODDS;

    public JsonObject getMainOddsJson(Long eventId) {
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<JsonNode> response = Unirest.post(MAIN_URL).field("id_ev", eventId).asJson();
            return JsonParser.parseString(response.getBody().toString()).getAsJsonObject().get("reply").getAsJsonObject();
        } catch (Exception ex) {
            throw new RuntimeException("Ошибка во время обработки события", ex);
        }
    }

    public JsonObject getAdditionalOdds(Long eventId) {
        return downloadJson(String.format(ADDITIONAL_ODDS, eventId)).get("reply").getAsJsonObject();
    }

}
