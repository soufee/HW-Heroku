package ci.ashamaz.hwheroku.parser;


import ci.ashamaz.hwheroku.downloader.BetCityEventOddsDownloader;
import ci.ashamaz.hwheroku.dto.BetCityEventBaseDto;
import ci.ashamaz.hwheroku.enums.OddPositions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BetCityEventParser {

    private final BetCityEventOddsDownloader eventDownloader;

    private static final List<Long> additionalOddIds = new ArrayList<>(Arrays.asList(112L, 395L, 14L, 71L, 862L, 863L));

    public BetCityEventBaseDto downloadAndParseBetCityEvent(String url) {
        if (!url.contains("https://betcity.ru")) {
            throw new IllegalArgumentException("Url should belong to BetCity");
        }
        if (!url.contains("soccer")) {
            throw new IllegalArgumentException("The system supports only soccer events");
        }
        Long eventId;
        Long tournamentId;
        try {
            String[] splitParts = url.split("/");
            eventId = Long.valueOf(splitParts[splitParts.length - 1]);
            tournamentId = Long.valueOf(splitParts[splitParts.length - 2]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not parse provided URL. Please check it " + url);
        }
        JsonObject mainOddsJson = eventDownloader.getMainOddsJson(eventId);
        JsonObject additionalOddsJson = eventDownloader.getAdditionalOdds(eventId);
        BetCityEventBaseDto dto = buildDto(eventId, tournamentId, mainOddsJson, additionalOddsJson);

        return dto;
    }

    private BetCityEventBaseDto buildDto(Long eventId, Long tournamentId, JsonObject mainOddsJson, JsonObject additionalOddsJson) {
        JsonObject tournamentJson =
                mainOddsJson.get("sports").getAsJsonObject().get("1").getAsJsonObject().get("chmps").getAsJsonObject().get(tournamentId.toString())
                        .getAsJsonObject();
        JsonObject matchJson = tournamentJson.get("evts").getAsJsonObject().get(eventId.toString()).getAsJsonObject();
        BetCityEventBaseDto dto = BetCityEventBaseDto.builder().id(eventId).tournament(tournamentJson.get("name_ch").getAsString()).tournamentId(tournamentId)
                .startsAt(LocalDateTime.ofInstant(Instant.ofEpochSecond(matchJson.get("date_ev").getAsLong()), ZoneId.of("Europe/Belgrade")))
                .hostTeam(matchJson.get("name_ht").getAsString()).guestTeam(matchJson.get("name_at").getAsString())
               .build();
        addMainOdds(dto, mainOddsJson);
        addAdditionalOdds(dto, additionalOddsJson);
        return dto;
    }

    private void addAdditionalOdds(BetCityEventBaseDto dto, JsonObject additionalOddsJson) {
        EnumMap<OddPositions, Double> odds = dto.getOdds();
        if (odds == null) {
            odds = new EnumMap<>(OddPositions.class);
            dto.setOdds(odds);
        }

        JsonObject chmps = additionalOddsJson.getAsJsonObject("sports").getAsJsonObject("1").getAsJsonObject("chmps");
        JsonObject evts = chmps.entrySet().iterator().next().getValue().getAsJsonObject().getAsJsonObject("evts").entrySet().iterator().next().getValue()
                .getAsJsonObject();

        List<Map.Entry<String, JsonElement>> extList =
                evts.get("ext").getAsJsonObject().entrySet().stream().filter(e -> additionalOddIds.contains(Long.valueOf(e.getKey())))
                        .collect(Collectors.toList());

        Map<String, JsonObject> data =
                extList.stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().getAsJsonObject().getAsJsonObject("data").getAsJsonObject()));

        Map<String, Double> stringDoubleMap = buildAdditionalCoeficentAbbr(data);
        for (OddPositions value : OddPositions.values()) {
            Double aDouble = stringDoubleMap.get(value.getAbbr());
            if (aDouble != null) {
                odds.put(value, aDouble);
            }
        }
    }

    private void addMainOdds(BetCityEventBaseDto dto, JsonObject mainOddsJson) {
        EnumMap<OddPositions, Double> odds = dto.getOdds();
        if (odds == null) {
            odds = new EnumMap<>(OddPositions.class);
            dto.setOdds(odds);
        }
        JsonObject chmps = mainOddsJson.getAsJsonObject("sports").getAsJsonObject("1").getAsJsonObject("chmps");
        Set<Map.Entry<String, JsonElement>> entries =
                chmps.entrySet().iterator().next().getValue().getAsJsonObject().getAsJsonObject("evts").entrySet().iterator().next().getValue()
                        .getAsJsonObject().getAsJsonObject("main").entrySet();
        Map<String, JsonObject> map = entries.stream().collect(Collectors.toMap(Map.Entry::getKey,
                v -> v.getValue().getAsJsonObject().getAsJsonObject("data").entrySet().iterator().next().getValue().getAsJsonObject()
                        .getAsJsonObject("blocks")));

        Map<String, Double> stringDoubleMap = buildMainCoeficentAbbr(map);
        for (OddPositions value : OddPositions.values()) {
            Double aDouble = stringDoubleMap.get(value.getAbbr());
            if (aDouble != null) {
                odds.put(value, aDouble);
            }
        }
    }

    private Map<String, Double> buildAdditionalCoeficentAbbr(Map<String, JsonObject> map) {
        Map<String, Double> coefs = new HashMap<>();
        for (Map.Entry<String, JsonObject> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.equals("72")) key = "112";
            List<JsonObject> blocks =
                    entry.getValue().entrySet().stream().map(e -> e.getValue().getAsJsonObject().get("blocks").getAsJsonObject()).collect(Collectors.toList());
            for (JsonObject block : blocks) {
                Map.Entry<String, JsonElement> next = block.entrySet().iterator().next();
                JsonObject value1 = next.getValue().getAsJsonObject();
                List<Map.Entry<String, JsonElement>> collect =
                        value1.entrySet().stream().filter(e -> e.getValue() instanceof JsonObject).collect(Collectors.toList());
                for (int i = 0; i < collect.size(); i++) {
                    Map.Entry<String, JsonElement> ent = collect.get(i);
                    StringBuilder sb = new StringBuilder();
                    sb.append(key + "_");
                    sb.append(next.getKey() + "_");
                    sb.append(ent.getKey());
                    JsonElement lv = ent.getValue().getAsJsonObject().get("lv");
                    if (lv != null) {
                        sb.append("_").append(lv.getAsString());
                    }
                    Double c = ent.getValue().getAsJsonObject().get("kf").getAsDouble();
                    coefs.put(sb.toString(), c);
                }
            }
        }
        return coefs;
    }

    private Map<String, Double> buildMainCoeficentAbbr(Map<String, JsonObject> map) {
        Map<String, Double> coefs = new HashMap<>();
        for (Map.Entry<String, JsonObject> entry : map.entrySet()) {
            Map.Entry<String, JsonElement> next = entry.getValue().entrySet().iterator().next();
            JsonObject value1 = next.getValue().getAsJsonObject();
            List<Map.Entry<String, JsonElement>> collect =
                    value1.entrySet().stream().filter(e -> e.getValue() instanceof JsonObject).collect(Collectors.toList());
            for (int i = 0; i < collect.size(); i++) {
                Map.Entry<String, JsonElement> ent = collect.get(i);
                StringBuilder sb = new StringBuilder();
                String entryKey = entry.getKey();
                if (entryKey.equals("72")) entryKey = "112";
                sb.append(entryKey + "_");
                String key = next.getKey();
                if (key.equals("F1m")) {
                    key = "F1";
                }
                if (key.equals("T1m")) {
                    key = "T";
                }
                sb.append(key + "_");
                sb.append(ent.getKey());
                JsonElement lv = ent.getValue().getAsJsonObject().get("lv");
                if (lv != null) {
                    sb.append("_").append(lv.getAsString());
                }
                Double c = ent.getValue().getAsJsonObject().get("kf").getAsDouble();
                coefs.put(sb.toString(), c);
            }

        }
        return coefs;
    }


}
