package ci.ashamaz.hwheroku.view.component;

import ci.ashamaz.hwheroku.enums.RoleEnum;
import lombok.Getter;

import java.util.List;

@Getter
public enum MainMenuItems {
    MAIN("Главная", "main", RoleEnum.allAuthorized),
    PROGNOSIS("Прогнозы", "prognosis", RoleEnum.allAuthorized),
    RESULTS("Результаты", "results", RoleEnum.allAuthorized),
    STATISTIC("Статистика", "stat", RoleEnum.allAuthorized),
    ADMIN("Админка", "admin", RoleEnum.adminRoles);

    MainMenuItems(String title, String url, List<RoleEnum> permitedRoles) {
        this.title = title;
        this.url = url;
        this.permitedRoles = permitedRoles;
    }

    private final String title;
    private final String url;
    private final List<RoleEnum> permitedRoles;

}
