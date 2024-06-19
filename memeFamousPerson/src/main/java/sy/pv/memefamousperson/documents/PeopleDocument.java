package sy.pv.memefamousperson.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection =  "people")
public class PeopleDocument {
    @Id
    String id;
    String username;
    String aka;
    String address;
    LocalDate birthday;
    String description;
}
