package ci.ashamaz.hwheroku.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;

@AllArgsConstructor
@Getter
public enum OddPositions {
    WIN1("П1", "69_Wm_P1","1. 1 X 2", (a, b) -> a > b, (a, b) -> false),
    DRAW("Х", "69_Wm_X","1. 1 X 2", Integer::equals, (a, b) -> false),
    WIN2("П2", "69_Wm_P2","1. 1 X 2", (a, b) -> b > a, (a, b) -> false),

    WIN1_OR_DRAW("1Х", "70_WXm_1X","2. Двойной шанс", (a, b) -> a >= b, (a, b) -> false),
    WIN1_OR_WIN2("12", "70_WXm_12","2. Двойной шанс", (a, b) -> !a.equals(b), (a, b) -> false),
    WIN2_OR_DRAW("2Х", "70_WXm_X2","2. Двойной шанс", (a, b) -> b >= a, (a, b) -> false),

    BOTH_GOAL_YES("ОЗ да", "14_YN_Y","3. Обе забьют", (a, b) -> a > 0 && b > 0, (a, b) -> false),
    BOTH_GOAL_NO("ОЗ нет", "14_YN_N","3. Обе забьют", (a, b) -> a == 0 || b == 0, (a, b) -> false),

    TOTAL_UP_2("ТБ 2.0", "112_T_Tb_2","4. Тотал",(a, b) -> (a + b) > 2, (a, b) -> (a + b) == 2),
    TOTAL_UNDER_2("ТМ 2.0", "112_T_Tm_2","4. Тотал", (a, b) -> (a + b) < 2, (a, b) -> (a + b) == 3),
    TOTAL_UP_25("ТБ 2.5", "112_T_Tb_2.5","4. Тотал", (a, b) -> (a + b) > 2.5, (a, b) -> false),
    TOTAL_UNDER_25("ТМ 2.5", "112_T_Tm_2.5","4. Тотал", (a, b) -> (a + b) < 2.5, (a, b) -> false),
    TOTAL_UP_3("ТБ 3.0", "112_T_Tb_3","4. Тотал", (a, b) -> (a + b) > 3, (a, b) -> (a + b) == 3),
    TOTAL_UNDER_3("ТМ 3.0", "112_T_Tm_3","4. Тотал", (a, b) -> (a + b) < 3, (a, b) -> (a + b) == 3),
    TOTAL_UP_35("ТБ 3.5", "112_T_Tb_3.5","4. Тотал", (a, b) -> (a + b) > 3.5, (a, b) -> false),
    TOTAL_UNDER_35("ТМ 3.5", "112_T_Tm_3.5","4. Тотал", (a, b) -> (a + b) < 3.5, (a, b) -> false),


    HANDICAP_HOST_0("Ф1 0", "71_F1_Kf_F1_0","5. Фора", (a, b) -> a > b, Integer::equals),
    HANDICAP_GUEST_0("Ф2 0", "71_F1_Kf_F2_0","5. Фора", (a, b) -> a < b, Integer::equals),
    HANDICAP_HOST_1("Ф1 1", "71_F1_Kf_F1_1","5. Фора", (a, b) -> a + 1 > b, (a, b) -> a + 1 == b),
    HANDICAP_GUEST_1("Ф2 1", "71_F1_Kf_F2_1","5. Фора", (a, b) -> b + 1 > a, (a, b) -> b + 1 == a),
    HANDICAP_HOST_MINUS_1("Ф1 -1", "71_F1_Kf_F1_-1","5. Фора", (a, b) -> a - 1 > b, (a, b) -> a - 1 == b),
    HANDICAP_GUEST_MINUS_1("Ф2 -1", "71_F1_Kf_F2_-1","5. Фора", (a, b) -> b - 1 > a, (a, b) -> b - 1 == a),
    HANDICAP_HOST_15("Ф1 1.5", "71_F1_Kf_F1_1.5","5. Фора", (a, b) -> a + 1.5 > b, (a, b) -> false),
    HANDICAP_GUEST_15("Ф2 1.5", "71_F1_Kf_F2_1.5","5. Фора", (a, b) -> b + 1.5 > a, (a, b) -> false),
    HANDICAP_HOST_MINUS_15("Ф1 -1.5", "71_F1_Kf_F1_-1.5","5. Фора", (a, b) -> a - 1.5 > b, (a, b) -> false),
    HANDICAP_GUEST_MINUS_15("Ф2 -1.5", "71_F1_Kf_F2_-1.5","5. Фора", (a, b) -> b - 1.5 > a, (a, b) -> false),
    HANDICAP_HOST_2("Ф1 2", "71_F1_Kf_F1_2","5. Фора", (a, b) -> a + 2 > b, (a, b) -> a + 2 == b),
    HANDICAP_GUEST_2("Ф2 2", "71_F1_Kf_F2_2","5. Фора", (a, b) -> b + 2 > a, (a, b) -> b + 2 == a),
    HANDICAP_HOST_MINUS_2("Ф1 -2", "71_F1_Kf_F1_-2","5. Фора", (a, b) -> a - 2 > b, (a, b) -> a - 2 == b),
    HANDICAP_GUEST_MINUS_2("Ф2 -2", "71_F1_Kf_F2_-2","5. Фора", (a, b) -> b - 2 > a, (a, b) -> b - 2 == a),

    WIN1_BOTH_GOAL_YES("П1 и ОЗ ДА", "862_YN_Y","6. Комбинированные", (a, b) -> a > b && b > 0, (a, b) -> false),
    WIN1_BOTH_GOAL_NO("П1 и ОЗ НЕТ", "862_YN_N","6. Комбинированные", (a, b) -> a > b && b == 0, (a, b) -> false),
    WIN2_BOTH_GOAL_YES("П2 и ОЗ ДА", "863_YN_Y","6. Комбинированные", (a, b) -> b > a && a > 0, (a, b) -> false),
    WIN2_BOTH_GOAL_NO("П2 и ОЗ НЕТ", "863_YN_N","6. Комбинированные", (a, b) -> b > a && b == 0, (a, b) -> false),
    DRAW_WITH_GOALS("X и ОЗ ДА", "395_YN_Y","6. Комбинированные", (a, b) -> a.equals(b) && a > 0, (a, b) -> false),
    DRAW_WITH_NO_GOALS("X и ОЗ НЕТ", "395_YN_N","6. Комбинированные", (a, b) -> a.equals(b) && b == 0, (a, b) -> false);

    private String display;
    private String abbr;
    private String group;
    private BiPredicate<Integer, Integer> predicateWin;
    private BiPredicate<Integer, Integer> predicateReturn;
}
