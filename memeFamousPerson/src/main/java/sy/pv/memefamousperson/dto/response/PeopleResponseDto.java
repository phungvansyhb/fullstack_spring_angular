package sy.pv.memefamousperson.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PeopleResponseDto {
    String id;
    String username;
    String aka;
    String address;
    String birthday;
    String description;
}
