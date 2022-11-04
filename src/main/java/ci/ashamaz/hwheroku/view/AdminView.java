package ci.ashamaz.hwheroku.view;

import ci.ashamaz.hwheroku.security.SecurityService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@Route("admin")
@PageTitle("Tarot statistic! | Admin")
@CssImport(value = "./styles/radio-button-styles.css")
@CssImport(value = "./styles/radio-button-wrapper-style.css", themeFor="vaadin-radio-button")
public class AdminView extends ComplexPageView {
    public AdminView(SecurityService securityService) {
        super(securityService);
        add(new H1("Тут будет админка "));
        RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
        radioGroup.setLabel("Status");
        radioGroup.setItems("In progress", "Done", "Cancelled");
        add(radioGroup);
        radioGroup.addClassName("buttons");
        radioGroup.addValueChangeListener(
                (HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<RadioButtonGroup<String>, String>>) radioButtonGroupStringComponentValueChangeEvent -> {
                    System.out.println(radioButtonGroupStringComponentValueChangeEvent.getValue());
                });

    }
}
