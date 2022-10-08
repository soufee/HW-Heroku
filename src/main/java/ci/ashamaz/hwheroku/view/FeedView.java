package ci.ashamaz.hwheroku.view;

import ci.ashamaz.hwheroku.security.SecurityService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@Route("feed")
@PageTitle("Tarot statistic! | Feed")
public class FeedView extends ComplexPageView {
    public FeedView(SecurityService securityService) {
        super(securityService);
        add(new H1("Лента предстоящих событий"));
    }
}