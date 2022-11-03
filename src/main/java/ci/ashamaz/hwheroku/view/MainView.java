package ci.ashamaz.hwheroku.view;

import ci.ashamaz.hwheroku.dto.BetCityViewDto;
import ci.ashamaz.hwheroku.security.SecurityService;
import ci.ashamaz.hwheroku.service.CardStatService;
import ci.ashamaz.hwheroku.service.MatchRegisterService;
import ci.ashamaz.hwheroku.view.component.EventChooserComponent;
import ci.ashamaz.hwheroku.view.component.SportEventTemplateComponent;
import ci.ashamaz.hwheroku.view.component.TripletComponent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Service;

@UIScope
@Route("")
@PageTitle("Sport me! | Main")
@Service
public class MainView extends ComplexPageView {
    private final MatchRegisterService matchRegisterService;
    private SportEventTemplateComponent sportEventTemplateComponent;
    private BetCityViewDto eventDto;
    private EventChooserComponent eventChooserComponent;
    private HorizontalLayout mainBodyLayout;
    private final CardStatService cardStatService;
    public MainView(SecurityService securityService, MatchRegisterService matchRegisterService, CardStatService cardStatService) {
        super(securityService);
        this.matchRegisterService = matchRegisterService;
        this.cardStatService = cardStatService;
        setWidth(100, Unit.PERCENTAGE);
        setPadding(false);
        setSpacing(false);
        mainBodyLayout = new HorizontalLayout();
        mainBodyLayout.setWidth(100,Unit.PERCENTAGE);
        mainBodyLayout.setPadding(false);
        mainBodyLayout.setSpacing(false);
        mainBodyLayout.addClassName("body");
        add(mainBodyLayout);

        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.addClassNames("panel","left-panel");
        leftLayout.setSpacing(false);
        leftLayout.setPadding(false);
        VerticalLayout rightLayout = new VerticalLayout();
        rightLayout.addClassNames("panel", "right-panel");

        leftLayout.setWidth(50, Unit.PERCENTAGE);
        rightLayout.setWidth(50, Unit.PERCENTAGE);

        mainBodyLayout.add(leftLayout,rightLayout);
        eventChooserComponent = new EventChooserComponent(matchRegisterService);
        leftLayout.add(eventChooserComponent);
        TripletComponent triplet = new TripletComponent(cardStatService);
        rightLayout.add(triplet);
        rightLayout.setSpacing(false);
        rightLayout.setPadding(false);
    }

}
