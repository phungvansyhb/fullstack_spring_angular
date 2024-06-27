package sy.pv.memefamousperson.services;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sy.pv.memefamousperson.documents.PeopleDocument;
import sy.pv.memefamousperson.dto.request.PeopleRequestDto;
import sy.pv.memefamousperson.dto.response.PeopleListResponseDto;
import sy.pv.memefamousperson.dto.response.PeopleResponseDto;
import sy.pv.memefamousperson.repositories.PeopleRepository;

import java.io.IOException;
import java.util.ArrayList;
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

    public List<PeopleListResponseDto> getAllPeople(Pageable pageable) {
        return peopleRepository.findAll(pageable).stream().map(people -> {
            PeopleListResponseDto peopleListResponseDto = new PeopleListResponseDto();
            BeanUtils.copyProperties(people, peopleListResponseDto);
            return peopleListResponseDto;
        }).toList();

    }

    public PeopleResponseDto getDetailPeopleDocument(String id) {
        return peopleRepository.findById(id).map(peopleDocument -> {
            PeopleResponseDto peopleDocument1 = new PeopleResponseDto();
            BeanUtils.copyProperties(peopleDocument, peopleDocument1);
            if (peopleDocument.getBirthday() != null) {
                peopleDocument1.setBirthday(peopleDocument.getBirthday().toString());
            }
            return peopleDocument1;
        }).orElseThrow();
    }

    public void deletePeople(String id) {
        peopleRepository.deleteById(id);
    }

    public void updatePeople(PeopleRequestDto people, String id) {
        PeopleDocument peopleDocument = peopleRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(people, peopleDocument);
        peopleRepository.save(peopleDocument);
    }

    public void importPeople(MultipartFile file) throws IOException {
        List<PeopleDocument> peopleList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {

            PeopleRequestDto tempPeople = new PeopleRequestDto();
            PeopleDocument people = new PeopleDocument();
            XSSFRow row = worksheet.getRow(i);
            tempPeople.setUsername(row.getCell(0) != null ? row.getCell(0).getStringCellValue() : "");
            tempPeople.setAka(row.getCell(1) != null ? row.getCell(1).getStringCellValue() : "");
            tempPeople.setAvatar(row.getCell(2) != null ? row.getCell(2).getStringCellValue() : null);
            tempPeople.setBirthday(row.getCell(3) != null ?
                    row.getCell(3).getDateCellValue().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate()
                    : null);
            tempPeople.setDescription(row.getCell(4) != null ? row.getCell(4).getStringCellValue() : null);
            tempPeople.setAddress(row.getCell(5) != null ? row.getCell(5).getStringCellValue() : null);
            BeanUtils.copyProperties(tempPeople, people);
            peopleList.add(people);
        }
        peopleRepository.saveAll(peopleList);
    }
}
