package ci.ashamaz.hwheroku.view.component;

import ci.ashamaz.hwheroku.dto.BetCityEventBaseDto;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class SportEventTemplateComponent extends VerticalLayout {
    private BetCityEventBaseDto dto;

    public SportEventTemplateComponent(BetCityEventBaseDto dto) {
        this.dto = dto;
        Text text = new Text(dto.toString());
        add(text);
    }
}
