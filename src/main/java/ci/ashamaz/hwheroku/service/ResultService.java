package ci.ashamaz.hwheroku.service;

import ci.ashamaz.hwheroku.dto.ResultDisplayDto;

import java.util.List;

public interface ResultService {
    void checkResults();
    List<ResultDisplayDto> getTodaysEvents();
    List<ResultDisplayDto> getLastWeekEvents();
    List<ResultDisplayDto> getLastMonthEvents();
    List<ResultDisplayDto> getAllEvents();
}
