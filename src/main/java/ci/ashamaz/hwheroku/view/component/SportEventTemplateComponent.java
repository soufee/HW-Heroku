package ci.ashamaz.hwheroku.view.component;

import ci.ashamaz.hwheroku.dto.BetCityEventBaseDto;
import ci.ashamaz.hwheroku.enums.OddPositions;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@CssImport(value = "./styles/event-card-style.css")
public class SportEventTemplateComponent extends VerticalLayout {
    private BetCityEventBaseDto dto;
    private Label teams = new Label();
    private Label tournament = new Label();
    private Label time = new Label();
    private Div div = new Div();

    public SportEventTemplateComponent(BetCityEventBaseDto dto) {
        this.dto = dto;
        div.addClassName("event-card");
        if (dto != null) {
            setDto(dto);
        }
        div.setVisible(dto != null);
    }

    public void setDto(BetCityEventBaseDto dto) {
        this.dto = dto;
        if (dto == null) {
            div.setVisible(false);
            div.getElement().removeAllChildren();
        } else {
            teams = new Label(dto.getHostTeam() + " - " + dto.getGuestTeam());
            teams.addClassName("teamLine");
            tournament = new Label(dto.getTournament());
            tournament.addClassName("tournament");
            time = new Label(dto.getStartsAt().toString());
            time.addClassName("time");
            div.add(tournament, new HtmlComponent("br"), teams, new HtmlComponent("br"), time);
            StringBuilder sb = new StringBuilder();
            sb.append("П1: ").append(dto.getOdds().get(OddPositions.WIN1)).append(" | ")
                    .append("X: ").append(dto.getOdds().get(OddPositions.DRAW)).append(" | ")
                    .append("П2: ").append(dto.getOdds().get(OddPositions.WIN2));
            div.add(new HtmlComponent("br"));
            Label odds = new Label(sb.toString());
            odds.getStyle().set("font-weight","bold" );
            div.add(odds);
            div.setVisible(true);
            add(div);
        }

    }

}
