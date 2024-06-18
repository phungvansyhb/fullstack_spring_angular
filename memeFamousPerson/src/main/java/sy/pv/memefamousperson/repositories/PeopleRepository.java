package sy.pv.memefamousperson.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import sy.pv.memefamousperson.documents.PeopleDocument;

public interface PeopleRepository extends MongoRepository<PeopleDocument,String> {
}
