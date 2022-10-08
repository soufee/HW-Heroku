package ci.ashamaz.hwheroku.view;

import ci.ashamaz.hwheroku.security.SecurityService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@Route("admin")
@PageTitle("Tarot statistic! | Admin")
public class AdminView extends ComplexPageView {
    public AdminView(SecurityService securityService) {
        super(securityService);
        add(new H1("Тут будет админка "));
    }
}
