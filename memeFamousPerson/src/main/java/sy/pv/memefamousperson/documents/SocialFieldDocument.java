package sy.pv.memefamousperson.documents;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection =  "socialField")
public class SocialFieldDocument {
    @Id
    String id;
    String avatar;
    String name;
    String description;
    @DBRef
    List<PeopleDocument> peopleDocumentList;
}
