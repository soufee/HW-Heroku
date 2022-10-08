package ci.ashamaz.hwheroku.view;

import ci.ashamaz.hwheroku.security.SecurityService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@Route("")
@PageTitle("Sport me! | Main")
public class MainView extends ComplexPageView {
    public MainView(SecurityService securityService) {
        super(securityService);
        add(new H1("Its Main page. "));

    }
}
