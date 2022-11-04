package ci.ashamaz.hwheroku.dto;

import ci.ashamaz.hwheroku.enums.OddPositions;
import ci.ashamaz.hwheroku.enums.TarotEnum;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@Builder
public class ParseAndSaveDto {
    private String url;
    private TarotEnum position1;
    private TarotEnum drawPosition;
    private TarotEnum position2;
    @Nullable
    private OddPositions odd;
    private String comment;
}
