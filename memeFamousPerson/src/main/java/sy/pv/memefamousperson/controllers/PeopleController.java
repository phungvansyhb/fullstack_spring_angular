package sy.pv.memefamousperson.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sy.pv.memefamousperson.documents.PeopleDocument;
import sy.pv.memefamousperson.dto.request.PeopleRequestDto;
import sy.pv.memefamousperson.dto.request.ReactRequestDto;
import sy.pv.memefamousperson.dto.request.ReactionPeopleRecord;
import sy.pv.memefamousperson.dto.response.PeopleListResponseDto;
import sy.pv.memefamousperson.dto.response.PeopleResponseDto;
import sy.pv.memefamousperson.services.PeopleService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/people")
@AllArgsConstructor
public class PeopleController {
    final PeopleService peopleService;

    @PostMapping
    void CreatePeople(@RequestBody PeopleRequestDto people) throws Exception {
        peopleService.createPeople(people);
    }

    @GetMapping
    Page<PeopleListResponseDto> GetAllPeople(Pageable pageable) {
        return peopleService.getAllPeople(pageable);
    }

    @GetMapping("/{id}")
    PeopleResponseDto GetDetailPerson(@PathVariable String id) {
        return peopleService.getDetailPeopleDocument(id);
    }

    @DeleteMapping("/{id}")
    void DeletePerson(@PathVariable String id) {
        peopleService.deletePeople(id);
    }

    @PutMapping("/{id}")
    void UpdatePerson(@RequestBody PeopleRequestDto people, @PathVariable String id) {
        peopleService.updatePeople(people, id);
    }

    @PostMapping("/import")
        /* because use form-data we need to use @RequestParam */
    void ImportPerson(@RequestParam("file") MultipartFile file) throws IOException {
        peopleService.importPeople(file);
    }

    @PutMapping("/react/{peopleId}")
        /* because use form-data we need to use @RequestParam */
    ResponseEntity<Map<String, String>> reactPeople(@RequestBody ReactRequestDto records, @PathVariable String peopleId) {
        return peopleService.reactPeople(records.getReactType(), peopleId);
    }

    @GetMapping("/checkMail/{mail}")
    ResponseEntity<Boolean> checkMailPeople(@PathVariable String mail) {
        if (mail.equals("phungvansyhbfw@gmail.com")) {
            return new ResponseEntity<>(true,HttpStatus.OK);
        } else return new ResponseEntity<>(false,HttpStatus.OK);
    }
}
