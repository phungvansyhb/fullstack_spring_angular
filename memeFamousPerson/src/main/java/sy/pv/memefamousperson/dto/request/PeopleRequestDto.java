package sy.pv.memefamousperson.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PeopleRequestDto {
    String username;
    String aka;
    String address;
    LocalDate birthday;
    String description;
    String avatar;
}
