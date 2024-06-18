package sy.pv.memefamousperson.services;

import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import sy.pv.memefamousperson.documents.PeopleDocument;
import sy.pv.memefamousperson.dto.request.PeopleRequestDto;
import sy.pv.memefamousperson.repositories.PeopleRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PeopleService {

    private final PeopleRepository peopleRepository;

    public void createPeople(PeopleRequestDto people) throws Exception {
        try {
            PeopleDocument peopleDocument = new PeopleDocument();
            BeanUtils.copyProperties(people, peopleDocument);
            peopleRepository.save(peopleDocument);
        } catch (Error e) {
            throw new Exception("Tao people loi");
        }
    }

    public List<PeopleDocument> getAllPeople() {
        return peopleRepository.findAll();

    }

    public PeopleDocument getDetailPeopleDocument(String id){
        return peopleRepository.findById(id).orElseThrow();
    }

    public void deletePeople(String id){
        peopleRepository.deleteById(id);
    }

    public void updatePeople(PeopleRequestDto people , String id){
        PeopleDocument peopleDocument = peopleRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(people, peopleDocument);
        peopleRepository.save(peopleDocument);
    }

}
