package sy.pv.memefamousperson.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sy.pv.memefamousperson.dto.ReactionEnum;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReactionPeopleRecord implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    ReactionEnum reaction;
    LocalDate timeStamp;
}
