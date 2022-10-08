package ci.ashamaz.hwheroku.view.component;


import ci.ashamaz.hwheroku.entity.Account;
import ci.ashamaz.hwheroku.security.SecurityService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class HeaderComponent extends HorizontalLayout {
    public HeaderComponent(SecurityService securityService) {
        this.getStyle().set("background-color","#fff9f0");
        H1 logo = new H1("Sport me!");
        logo.getStyle().set("color", "#bb960c");
        Label greeting = new Label();
        logo.addClassNames("text-l", "m-m");
        Button logout = new Button("Log out", e -> securityService.logout());
        Button login = new Button("Log In", event -> UI.getCurrent().navigate("login"));

        login.setVisible(false);
        login.setVisible(false);

        Account principal = securityService.getAuthenticatedAccount();
        if (principal != null) {
            greeting.setText("Hello, " + principal.getName());
            logout.setVisible(true);
            login.setVisible(false);
        } else {
            logout.setVisible(false);
            login.setVisible(true);
            greeting.setText("Hello, please log in");
        }
        logo.addClassNames("text-l", "m-m");
        HorizontalLayout inOutGreetingLayout = new HorizontalLayout();
        inOutGreetingLayout.add(greeting, login, logout);
        inOutGreetingLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        add(logo, inOutGreetingLayout);
        setDefaultVerticalComponentAlignment(Alignment.START);
        expand(logo);
        setWidth("100%");
        addClassNames("py-0", "px-m");
        getStyle().set("border-bottom", "1px solid");
        getStyle().set("border-color", "#f44336!important");
        getStyle().set("padding", "0.01em 16px");
    }
}
