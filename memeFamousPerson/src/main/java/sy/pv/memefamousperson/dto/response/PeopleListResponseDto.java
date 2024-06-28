package sy.pv.memefamousperson.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PeopleListResponseDto {
    String id;
    String username;
    String aka;
    String avatar;
    Long like ;
    Long disLike ;
}
