package sy.pv.memefamousperson.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sy.pv.memefamousperson.dto.ReactionEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReactRequestDto {
    ReactionEnum reactType;
}
