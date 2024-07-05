package sy.pv.mailservice.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection =  "mail")
public class MailDocument {
    @Id
    String id;
    String from;
    String to;
    String subject;
    String content;
    LocalDate createdDate;
}
