package ci.ashamaz.hwheroku.view.component;

import ci.ashamaz.hwheroku.dto.ResultDisplayDto;
import ci.ashamaz.hwheroku.enums.OddPositions;
import ci.ashamaz.hwheroku.enums.ResultState;
import ci.ashamaz.hwheroku.service.ResultService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResultGridContent extends VerticalLayout {
    private final ResultService resultService;
    private Grid<ResultDisplayDto> grid;
    public ResultGridContent(ResultService resultService) {
        this.resultService = resultService;
        grid = new Grid<>(ResultDisplayDto.class, false);
        grid.setHeightByRows(true);
        grid.setPageSize(100);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addColumn(createEventRenderer()).setHeader("Event").setAutoWidth(true);
        grid.addColumn(ResultDisplayDto::getCards).setHeader("Cards").setAutoWidth(true);
        grid.addColumn(createChoiseWithResultRenderer()).setHeader("My choice").setAutoWidth(true);
        grid.addColumn(ResultDisplayDto::getComment).setHeader("Comment").setAutoWidth(true);
        grid.setItems(resultService.getTodaysEvents());
        add(grid);
    }

    public void addItems(List<ResultDisplayDto> dtos){
        grid.setItems(dtos);
    }

    private Renderer<ResultDisplayDto> createChoiseWithResultRenderer() {
        return LitRenderer.<ResultDisplayDto>of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">" + "    <span> ${item.choice} </span>"
                                + "</vaadin-horizontal-layout>")

                .withProperty("choice", e -> {
                    OddPositions choice = e.getChoise();
                    if (choice == null) {
                        return "";
                    } else {
                        String res = choice.getDisplay();
                        ResultState resultState = e.getResultState();
                        if (resultState != null) {
                            switch (resultState) {
                            case WIN:
                                res = res + " | ✅";
                                break;
                            case LOOSE:
                                res = res + " | ❌";
                                break;
                            case RETURN:
                                res = res + " | \uD83D\uDD04";
                                break;
                            default:
                            }
                        }

                        return res;
                    }
                });
    }

    private static Renderer<ResultDisplayDto> createEventRenderer() {

        return LitRenderer.<ResultDisplayDto>of("<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                        + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">" + "    <span> ${item.teams} (${item.score})</span>"
                        + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">" + "      ${item.startsAt}" + "    </span>"
                        + "  </vaadin-vertical-layout>" + "</vaadin-horizontal-layout>").withProperty("teams", ResultDisplayDto::getTeams)
                .withProperty("score", ResultDisplayDto::getScore).withProperty("startsAt", e->e.getStartsAt().toString());

    }
}
