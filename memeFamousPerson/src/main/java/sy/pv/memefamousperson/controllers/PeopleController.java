package sy.pv.memefamousperson.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sy.pv.memefamousperson.documents.PeopleDocument;
import sy.pv.memefamousperson.dto.request.PeopleRequestDto;
import sy.pv.memefamousperson.dto.response.PeopleListResponseDto;
import sy.pv.memefamousperson.dto.response.PeopleResponseDto;
import sy.pv.memefamousperson.services.PeopleService;

import java.io.IOException;
import java.util.List;

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
    List<PeopleListResponseDto> GetAllPeople(Pageable pageable) {
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
}
