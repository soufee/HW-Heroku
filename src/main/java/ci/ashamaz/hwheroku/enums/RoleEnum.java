package ci.ashamaz.hwheroku.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum RoleEnum {
    ANONYMOUS,
    USER,
    ADMIN;
    public static List<RoleEnum> allRoles = Arrays.asList(RoleEnum.values());
    public static List<RoleEnum> allAuthorized = Arrays.stream(RoleEnum.values())
            .filter(r -> !r.equals(RoleEnum.ANONYMOUS))
            .collect(Collectors.toList());
    public static List<RoleEnum> adminRoles = Collections.singletonList(RoleEnum.ADMIN);
    public static List<RoleEnum> userRole = Collections.singletonList(RoleEnum.USER);
}

