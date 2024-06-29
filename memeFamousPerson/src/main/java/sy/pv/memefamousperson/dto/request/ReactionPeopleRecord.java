package sy.pv.memefamousperson.dto.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    Long like ;
    Long dislike;
    String timeStamp;
    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            // Handle serialization exception
            return super.toString();
        }
    }
}
