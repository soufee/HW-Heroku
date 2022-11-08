package ci.ashamaz.hwheroku.view;

import ci.ashamaz.hwheroku.security.SecurityService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@Route("prognosis")
@PageTitle("My prognosis! | prognosis")
public class PrognosisView extends ComplexPageView {
    public PrognosisView(SecurityService securityService) {
        super(securityService);
        add(new H1("Сделанные прогнозы"));
    }
}