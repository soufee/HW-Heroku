package ci.ashamaz.hwheroku.view.component;


import ci.ashamaz.hwheroku.enums.RoleEnum;
import ci.ashamaz.hwheroku.security.SecurityUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainMenuBar extends VerticalLayout {
    private final MenuBar menuBar;

    private MainMenuBar(MainMenuItems... items) {
        menuBar = new MenuBar();
        for (MainMenuItems item : items) {
            menuBar.addItem(item.getTitle(), e -> UI.getCurrent().navigate(item.getUrl()));
        }
        menuBar.addThemeVariants(MenuBarVariant.LUMO_PRIMARY);
        addAndExpand(menuBar);
        setWidth("100%");
        setHeight("100px");
        getStyle().set("border-bottom", "1px solid");
        getStyle().set("border-color", "#055be9");
    }

    private MainMenuBar() {
        menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_PRIMARY);
        addAndExpand(menuBar);
        setWidth("100%");
        getStyle().set("border-bottom", "1px solid");
        getStyle().set("border-color", "#055be9");
    }

    public void addItems(MainMenuItems... items) {
        addItems(Arrays.asList(items));

    }

    private void addItems(List<MainMenuItems> items) {
        for (MainMenuItems item : items) {
            menuBar.addItem(item.getTitle(), e -> UI.getCurrent().navigate(item.getUrl()));
        }
    }

    public static MainMenuBar getMainMenuBar() {
        MainMenuBar mainMenuBar = new MainMenuBar();

        List<RoleEnum> authenticatedRoles = SecurityUtils.getAuthenticatedRoles()
                .stream()
                .map(e -> RoleEnum.valueOf(e.replace("ROLE_", "")))
                .collect(Collectors.toList());
        List<MainMenuItems> items = Arrays.stream(MainMenuItems.values()).filter(e -> {
            ArrayList<RoleEnum> permitedRoles = new ArrayList<>(e.getPermitedRoles());
            permitedRoles.retainAll(authenticatedRoles);
            return permitedRoles.size() > 0;
        }).collect(Collectors.toList());
        mainMenuBar.addItems(items.toArray(new MainMenuItems[items.size()]));

        return mainMenuBar;
    }
}
