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
    private HorizontalLayout searchLayout = new HorizontalLayout();
    private Button getOdds;
    private Button clear;
    private TextField field;

    public EventChooserComponent() {
        add(searchLayout);
        field = new TextField("Betcity link");
        field.setWidth("450px");
        field.setPlaceholder("https://betcity.ru/en/line/soccer/?/?");
        getOdds = new Button("Get Odds");
        getOdds.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clear = new Button("Clear");
        clear.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        searchLayout.add(field, getOdds, clear);
        searchLayout.setAlignItems(Alignment.BASELINE);
        searchLayout.setVisible(true);
    }

    public void setSearchButtonEventListener(ComponentEventListener<ClickEvent<Button>> event) {
        getOdds.addClickListener(event);
    }

    public void setClearventListener(ComponentEventListener<ClickEvent<Button>> event) {
        clear.addClickListener(event);
    }

    public void addError(TextField field, String message) {
        field.setInvalid(true);
        field.setErrorMessage(message);
    }

    public boolean isCorrectBetCitLink(String value) {
        final Pattern pattern = Pattern.compile("https://betcity\\.ru/.*/line/soccer/\\d*/\\d*$", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(value).matches();
    }
}
