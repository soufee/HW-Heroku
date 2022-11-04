package ci.ashamaz.hwheroku.endpoint;


import ci.ashamaz.hwheroku.dto.BetCityEventBaseDto;
import ci.ashamaz.hwheroku.dto.ParseAndSaveDto;
import ci.ashamaz.hwheroku.dto.UrlDto;
import ci.ashamaz.hwheroku.entity.BetCityEvent;
import ci.ashamaz.hwheroku.enums.OddPositions;
import ci.ashamaz.hwheroku.enums.TarotEnum;
import ci.ashamaz.hwheroku.service.MatchRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/event/*")
public class EventController {
    public final MatchRegisterService matchRegisterService;

    @RequestMapping(value = "/getOdds", method = RequestMethod.POST)
    public ResponseEntity<BetCityEventBaseDto> parseUrl(@RequestBody UrlDto urlDto) {
        log.info("Введен урл " + urlDto.getUrl());
        try {
            BetCityEventBaseDto eventTemplate = matchRegisterService.getEventTemplate(urlDto.getUrl());
            return ResponseEntity.ok(eventTemplate);
        } catch (Exception e) {
            log.error("Error while getting odds", e);
            return ResponseEntity.status(500).build();
        }

    }
    //
    //    @RequestMapping(value = "/parseAndSave", method = RequestMethod.POST)
    //    public ResponseEntity<String> parseAndSave(
    //            @RequestBody UrlDto urlDto, @RequestParam("position1") TarotEnum position1,
    //            @RequestParam("drawPosition") TarotEnum drawPosition, @RequestParam("position2") TarotEnum position2,
    //            @Nullable @RequestParam("odd") OddPositions odd
    //
    //    ) {
    //
    //        log.info("Введен урл " + urlDto.getUrl());
    //        try {
    //            BetCityEventBaseDto eventTemplate = matchRegisterService.getEventTemplate(urlDto.getUrl());
    //            BetCityEvent event = null;
    //            if (odd != null) {
    //                event = matchRegisterService.makeTripletAndSave(eventTemplate, position1, drawPosition, position2, odd);
    //
    //            } else {
    //                event = matchRegisterService.makeTripletAndSave(eventTemplate, position1, drawPosition, position2);
    //            }
    //            return ResponseEntity.ok("Event " + event.getHostTeam() + " - " + event.getGuestTeam() + " is registered. Prognosis added = " + (odd != null));
    //        } catch (Exception e) {
    //            log.error("Error while getting odds", e);
    //            return ResponseEntity.status(500).build();
    //        }
    //
    //    }

    @RequestMapping(value = "/parseAndSave", method = RequestMethod.POST)
    public ResponseEntity<String> parseAndSave(@RequestBody ParseAndSaveDto urlDto

    ) {

        log.info("Введен урл " + urlDto.getUrl());
        try {
            BetCityEventBaseDto eventTemplate = matchRegisterService.getEventTemplate(urlDto.getUrl());
            BetCityEvent event = null;
            if (urlDto.getOdd() != null) {
                event = matchRegisterService.makeTripletAndSave(eventTemplate, urlDto.getPosition1(), urlDto.getDrawPosition(), urlDto.getPosition2(),
                        urlDto.getOdd(), urlDto.getComment());

            } else {
                event = matchRegisterService.makeTripletAndSave(eventTemplate, urlDto.getPosition1(), urlDto.getDrawPosition(), urlDto.getPosition2(), urlDto.getComment());
            }
            return ResponseEntity.ok(
                    "Event " + event.getHostTeam() + " - " + event.getGuestTeam() + " is registered. Prognosis added = " + (urlDto.getOdd() != null));
        } catch (Exception e) {
            log.error("Error while getting odds", e);
            return ResponseEntity.status(500).build();
        }

    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<BetCityEvent> create(@RequestBody BetCityEventBaseDto dto, @RequestParam("position1") TarotEnum position1,
            @RequestParam("drawPosition") TarotEnum drawPosition, @RequestParam("position2") TarotEnum position2) {
        try {
            BetCityEvent event = matchRegisterService.makeTripletAndSave(dto, position1, drawPosition, position2, null);
            return ResponseEntity.ok(event);
        } catch (Exception e) {
            log.error("Error while creating event", e);
            return ResponseEntity.status(500).build();
        }

    }

    @RequestMapping(value = "/createWithPrognosis", method = RequestMethod.POST)
    public ResponseEntity<BetCityEvent> createWithPrognosis(@RequestBody BetCityEventBaseDto dto, @RequestParam("position1") TarotEnum position1,
            @RequestParam("drawPosition") TarotEnum drawPosition, @RequestParam("position2") TarotEnum position2, @RequestParam("odd") OddPositions odd) {
        try {
            BetCityEvent event = matchRegisterService.makeTripletAndSave(dto, position1, drawPosition, position2, odd, null);
            return ResponseEntity.ok(event);
        } catch (Exception e) {
            log.error("Error while creating prognosis", e);
            return ResponseEntity.status(500).build();
        }

    }

    @RequestMapping(value = "/prognosis", method = RequestMethod.POST)
    public ResponseEntity<BetCityEvent> create(@RequestBody BetCityEventBaseDto dto, @RequestParam("odd") OddPositions odd) {
        try {
            BetCityEvent event = matchRegisterService.addPrognosis(dto, odd);
            return ResponseEntity.ok(event);
        } catch (Exception e) {
            log.error("Error while adding prognosis", e);
            return ResponseEntity.status(500).build();
        }
    }

}
