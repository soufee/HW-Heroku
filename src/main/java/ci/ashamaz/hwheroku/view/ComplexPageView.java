package ci.ashamaz.hwheroku.view;

import ci.ashamaz.hwheroku.security.SecurityService;
import ci.ashamaz.hwheroku.view.component.HeaderComponent;
import ci.ashamaz.hwheroku.view.component.MainMenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
public abstract class ComplexPageView extends VerticalLayout {
    protected final SecurityService securityService;

    public ComplexPageView(SecurityService securityService) {
        this.securityService = securityService;
        add(new HeaderComponent(securityService), MainMenuBar.getMainMenuBar());
    }

}
