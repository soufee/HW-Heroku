package ci.ashamaz.hwheroku.view.component;

import ci.ashamaz.hwheroku.dto.ResultDisplayDto;
import ci.ashamaz.hwheroku.enums.OddPositions;
import ci.ashamaz.hwheroku.enums.ResultState;
import ci.ashamaz.hwheroku.service.ResultService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        Grid.Column<ResultDisplayDto> startTime = grid.addColumn(ResultDisplayDto::getStartsAt).setHeader("Start time").setAutoWidth(true);
        grid.addColumn(ResultDisplayDto::getCards).setHeader("Cards").setAutoWidth(true);
        grid.addColumn(createChoiceWithResultRenderer()).setHeader("My choice").setAutoWidth(true);
        grid.addColumn(ResultDisplayDto::getComment).setHeader("Comment").setAutoWidth(true);

        grid.setItems(resultService.getTodaysEvents());
        GridSortOrder<ResultDisplayDto> order = new GridSortOrder<>(startTime, SortDirection.ASCENDING);

        grid.sort(Arrays.asList(order));
        add(grid);
    }

    public void addItems(List<ResultDisplayDto> dtos) {
        grid.setItems(dtos);
    }

    private Renderer<ResultDisplayDto> createChoiceWithResultRenderer() {
        return LitRenderer.<ResultDisplayDto>of("<div style=\"align-items: center;text-align: center;background-color:${item.color};\"theme=\"spacing\">"
                        + "    <span style=\"font-weight: bold;\">${item.choice}</span>" + "</div>")

                .withProperty("choice", e -> {
                    OddPositions choice = e.getChoice();
                    if (choice == null) {
                        return "";
                    } else {
                        return choice.getDisplay();
                    }
                }).withProperty("color", e -> {
                    OddPositions choice = e.getChoice();
                    ResultState resultState = e.getResultState();
                    if (resultState == null || choice == null) {
                        return "#ffffff";
                    } else {
                        switch (resultState) {
                        case WIN:
                            return "#b7e8d6";
                        case LOOSE:
                            return "#e8bcb7";
                        case RETURN:
                            return "fcbd2f7";
                        default:
                            return "#ffffff";
                        }
                    }
                });


    }

    private static Renderer<ResultDisplayDto> createEventRenderer() {

        return LitRenderer.<ResultDisplayDto>of("<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                        + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">" + "    <span> ${item.teams} </span>"
                        + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">" + "" + "      ${item.score}"
                        + "    </span>" + "  </vaadin-vertical-layout>" + "</vaadin-horizontal-layout>").withProperty("teams", ResultDisplayDto::getTeams)
                .withProperty("score", ResultDisplayDto::getScore);

    }
}
