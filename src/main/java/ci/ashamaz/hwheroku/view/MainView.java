package ci.ashamaz.hwheroku.view;

import ci.ashamaz.hwheroku.dto.BetCityEventBaseDto;
import ci.ashamaz.hwheroku.dto.ParseAndSaveDto;
import ci.ashamaz.hwheroku.entity.BetCityEvent;
import ci.ashamaz.hwheroku.enums.OddPositions;
import ci.ashamaz.hwheroku.security.SecurityService;
import ci.ashamaz.hwheroku.service.CardStatService;
import ci.ashamaz.hwheroku.service.MatchRegisterService;
import ci.ashamaz.hwheroku.view.component.EventChooserComponent;
import ci.ashamaz.hwheroku.view.component.SportEventTemplateComponent;
import ci.ashamaz.hwheroku.view.component.TripletComponent;
import ci.ashamaz.hwheroku.view.component.util.NotificationError;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@UIScope
@Route("")
@PageTitle("Sport me! | Main")
@Service
@Slf4j
public class MainView extends ComplexPageView {
    private final MatchRegisterService matchRegisterService;
    private SportEventTemplateComponent sportEventTemplateComponent;
    private EventChooserComponent eventChooserComponent;
    private HorizontalLayout mainBodyLayout;
    private final CardStatService cardStatService;
    private SportEventTemplateComponent eventTemplateComponent;
    private ComboBox<OddPositions> choise;
    private Button cleanChoise;
    private TripletComponent triplet;
    private TextField commentField;
    private Button save;
    private ParseAndSaveDto saveDto;

    public MainView(SecurityService securityService, MatchRegisterService matchRegisterService, CardStatService cardStatService) {
        super(securityService);
        this.matchRegisterService = matchRegisterService;
        this.cardStatService = cardStatService;
        setWidth(100, Unit.PERCENTAGE);
        setPadding(false);
        setSpacing(false);
        mainBodyLayout = new HorizontalLayout();
        mainBodyLayout.setWidth(100, Unit.PERCENTAGE);
        mainBodyLayout.setPadding(false);
        mainBodyLayout.setSpacing(false);
        mainBodyLayout.addClassName("body");
        add(mainBodyLayout);

        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.addClassNames("panel", "left-panel");
        leftLayout.setSpacing(false);
        leftLayout.setPadding(false);
        VerticalLayout rightLayout = new VerticalLayout();
        rightLayout.addClassNames("panel", "right-panel");

        leftLayout.setWidth(50, Unit.PERCENTAGE);
        rightLayout.setWidth(50, Unit.PERCENTAGE);

        triplet = new TripletComponent(cardStatService);
        rightLayout.add(triplet);
        rightLayout.setSpacing(false);
        rightLayout.setPadding(false);
        mainBodyLayout.add(leftLayout, rightLayout);
        eventChooserComponent = new EventChooserComponent();
        leftLayout.add(eventChooserComponent);
        eventTemplateComponent = new SportEventTemplateComponent(null);
        leftLayout.add(eventTemplateComponent);
        HorizontalLayout betChoise = new HorizontalLayout();
        betChoise.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        choise = new ComboBox<>("Ставка");
        choise.setItems(OddPositions.values());
        choise.setEnabled(eventTemplateComponent.getDto() != null && triplet.areCardsChosen());
        triplet.setOnComboChange(() -> {
            save.setEnabled(eventTemplateComponent.getDto() != null && triplet.areCardsChosen());
            choise.setEnabled(eventTemplateComponent.getDto() != null && triplet.areCardsChosen());
        });
        cleanChoise = new Button("Clean choice");
        cleanChoise.addClickListener((event) -> {
            if (choise != null) {
                choise.setValue(null);
            }
            if (saveDto != null) {
                saveDto.setOdd(null);
            }
        });
        betChoise.add(choise, cleanChoise);

        leftLayout.add(betChoise);
        commentField = new TextField();
        commentField.setWidth(50, Unit.PERCENTAGE);
        save = new Button("Save");
        save.setEnabled(eventTemplateComponent.getDto() != null && triplet.areCardsChosen());
        save.addClickListener(event -> {
            saveDto = ParseAndSaveDto.builder().url(eventChooserComponent.getField().getValue()).position1(triplet.getFirstTeam().getValue())
                    .drawPosition(triplet.getDraw().getValue()).position2(triplet.getSecondTeam().getValue()).odd(choise.getValue())
                    .comment(commentField.getValue()).build();
            BetCityEvent saved = matchRegisterService.addPrognosis(saveDto);
            if (saved != null) {
                String note = "Saved event " + saved.getHostTeam() + " - " + saved.getGuestTeam();
                Notification.show(note);
            } else {
                new NotificationError().show();
            }
            getCleanButton().run();
        });

        eventChooserComponent.setSearchButtonEventListener(event -> {
            if (StringUtils.hasLength(eventChooserComponent.getField().getValue()) && eventChooserComponent.isCorrectBetCitLink(
                    eventChooserComponent.getField().getValue())) {
                BetCityEventBaseDto eventTemplate = null;
                try {
                    eventTemplate = matchRegisterService.getEventTemplate(eventChooserComponent.getField().getValue());
                    if (eventTemplate == null) {
                        throw new IllegalStateException("Ошибка при получении данных матча");
                    } else {
                        eventTemplateComponent.setDto(eventTemplate);
                        choise.setItemLabelGenerator(e -> e.getDisplay() + " | " + eventTemplateComponent.getDto().getOdds().get(e));
                        choise.setEnabled(eventTemplateComponent.getDto() != null && triplet.areCardsChosen());
                        save.setEnabled(eventTemplateComponent.getDto() != null && triplet.areCardsChosen());
                        eventChooserComponent.getGetOdds().setEnabled(false);
                    }
                } catch (Exception e) {
                    eventChooserComponent.addError(eventChooserComponent.getField(), e.getMessage());
                }
            } else {
                eventChooserComponent.addError(eventChooserComponent.getField(), "Provide correct Betcity link");
            }
        });

        eventChooserComponent.setClearventListener(event -> {
            getCleanButton().run();
        });
        leftLayout.add(commentField);
        leftLayout.add(save);
    }

    private Runnable getCleanButton() {
        return () -> {
            eventChooserComponent.getField().setValue("");
            triplet.cleanTriplet();
            eventTemplateComponent.setDto(null);
            saveDto = null;
            save.setEnabled(eventTemplateComponent.getDto() != null && triplet.areCardsChosen());
            choise.setEnabled(eventTemplateComponent.getDto() != null && triplet.areCardsChosen());
            choise.setValue(null);
            commentField.setValue("");
            eventChooserComponent.getGetOdds().setEnabled(true);
        };
    }
}
