package ci.ashamaz.hwheroku.downloader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class BetCityResultDownloader extends AbstractJsonDownloader {

    public JsonObject downloadResult(String url) {
        if (!StringUtils.hasLength(url)) {
            return null;
        }
        try {
            JsonObject result = downloadJson(url);
            return result;
        } catch (Exception e) {
            log.error("Could not download result JSON", e);
            e.printStackTrace();
            return null;
        }
    }

    public String parseEventResult(JsonObject eventJson) {
        JsonObject evts =
                eventJson.getAsJsonObject("reply").getAsJsonObject("sports").entrySet().iterator().next().getValue().getAsJsonObject().getAsJsonObject("chmps")
                        .entrySet().iterator().next().getValue().getAsJsonObject().getAsJsonObject("evts").entrySet().iterator().next().getValue()
                        .getAsJsonObject();
        JsonElement scores = evts.get("sc_ev");
        if (scores != null) {
            return scores.getAsString();
        }
        return null;
    }

}
