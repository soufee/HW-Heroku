package ci.ashamaz.hwheroku.view;

import ci.ashamaz.hwheroku.security.SecurityService;
import ci.ashamaz.hwheroku.service.ResultService;
import ci.ashamaz.hwheroku.view.component.ResultGridContent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@Route("results")
@PageTitle("Tarot results! | Results")
public class ResultsView extends ComplexPageView {
    private final ResultService resultService;

    public ResultsView(SecurityService securityService, ResultService resultService) {
        super(securityService);
        this.resultService = resultService;
        HorizontalLayout buttonBar = new HorizontalLayout();
        Button buttonToday = new Button("Сегодня");
        Button buttonWeek = new Button("За неделю");
        Button buttonMonth = new Button("За месяц");
        Button buttonAll = new Button("За все время");
        buttonToday.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonWeek.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonMonth.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonAll.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonToday.setEnabled(false);
        buttonToday.setDisableOnClick(true);
        buttonWeek.setDisableOnClick(true);
        buttonMonth.setDisableOnClick(true);
        buttonAll.setDisableOnClick(true);
        buttonBar.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        buttonBar.add(buttonToday, buttonWeek, buttonMonth, buttonAll);
        ResultGridContent resultGridContent = new ResultGridContent(resultService);
        buttonToday.addClickListener(event->{
            buttonWeek.setEnabled(true);
            buttonMonth.setEnabled(true);
            buttonAll.setEnabled(true);
            resultGridContent.addItems(resultService.getTodaysEvents());
        });
        buttonWeek.addClickListener(event->{
            buttonToday.setEnabled(true);
            buttonMonth.setEnabled(true);
            buttonAll.setEnabled(true);
            resultGridContent.addItems(resultService.getLastWeekEvents());

        });
        buttonMonth.addClickListener(event->{
            buttonWeek.setEnabled(true);
            buttonToday.setEnabled(true);
            buttonAll.setEnabled(true);
            resultGridContent.addItems(resultService.getLastMonthEvents());

        });
        buttonAll.addClickListener(event->{
            buttonWeek.setEnabled(true);
            buttonMonth.setEnabled(true);
            buttonToday.setEnabled(true);
            resultGridContent.addItems(resultService.getAllEvents());

        });
        add(buttonBar, resultGridContent);


    }
}
