package sy.pv.mailservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sy.pv.mailservice.documents.MailDocument;

public interface MailRepository extends MongoRepository<MailDocument,String> {
}
