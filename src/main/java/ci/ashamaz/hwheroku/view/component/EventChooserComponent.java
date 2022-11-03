package ci.ashamaz.hwheroku.view.component;

import ci.ashamaz.hwheroku.service.MatchRegisterService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class EventChooserComponent extends VerticalLayout {
    private MatchRegisterService matchRegisterService;
    private HorizontalLayout searchLayout = new HorizontalLayout();
    private Button getOdds;
    private TextField field;

    public EventChooserComponent(MatchRegisterService matchRegisterService) {
        this.matchRegisterService = matchRegisterService;
        add(searchLayout);
        field = new TextField("Betcity link");
        field.setWidth("450px");
        field.setPlaceholder("https://betcity.ru/en/line/soccer/?/?");
        getOdds = new Button("Get Odds");
        getOdds.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchLayout.add(field, getOdds);
        searchLayout.setAlignItems(Alignment.BASELINE);
        searchLayout.setVisible(true);

//        getOdds.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
//            if (StringUtils.hasLength(field.getValue()) && isCorrectBetCitLink(field.getValue())) {
//                BetCityEventBaseDto eventTemplate = null;
//                try {
//                    eventTemplate = matchRegisterService.getEventTemplate(field.getValue());
//                    if (eventTemplate == null) {
//                        throw new IllegalStateException("Ошибка при получении данных матча");
//                    }
//                } catch (Exception e) {
//                    addError(field, e.getMessage());
//                }
//            } else {
//                addError(field, "Provide correct Betcity link");
//            }
//        });
    }

    public void setSearchButtonEventListener(ComponentEventListener<ClickEvent<Button>> event) {
        getOdds.addClickListener(event);
    }

    private void addError(TextField field, String message) {
        field.setInvalid(true);
        field.setErrorMessage(message);
    }

    private boolean isCorrectBetCitLink(String value) {
        final Pattern pattern = Pattern.compile("https://betcity\\.ru/.*/line/soccer/\\d*/\\d*$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(value).matches();
    }
}
