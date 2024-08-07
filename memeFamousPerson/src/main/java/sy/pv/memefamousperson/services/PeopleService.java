package sy.pv.memefamousperson.services;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sy.pv.memefamousperson.documents.PeopleDocument;
import sy.pv.memefamousperson.dto.ReactionEnum;
import sy.pv.memefamousperson.dto.request.PeopleRequestDto;
import sy.pv.memefamousperson.dto.request.ReactionPeopleRecord;
import sy.pv.memefamousperson.dto.response.PeopleListResponseDto;
import sy.pv.memefamousperson.dto.response.PeopleResponseDto;
import sy.pv.memefamousperson.repositories.PeopleRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PeopleService {
    private static final String HASH_KEY = "PEOPLE_REACTION";
    private PeopleRepository peopleRepository;
    private HashOperations<Object, String, ReactionPeopleRecord> hashOperations;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, RedisTemplate<Object, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
        this.peopleRepository = peopleRepository;
    }

    public void createPeople(PeopleRequestDto people) throws Exception {
        try {
            PeopleDocument peopleDocument = new PeopleDocument();
            BeanUtils.copyProperties(people, peopleDocument);
            peopleRepository.save(peopleDocument);
        } catch (Error e) {
            throw new Exception("Tao people loi");
        }
    }

    public Page<PeopleListResponseDto> getAllPeople(Pageable pageable) {
        Page<PeopleDocument> result = peopleRepository.findAll(pageable);
        List<PeopleListResponseDto> content = result.getContent().stream().map(item -> {
            PeopleListResponseDto peopleDocument = new PeopleListResponseDto();
            BeanUtils.copyProperties(item, peopleDocument);
            ReactionPeopleRecord reaction = hashOperations.get(HASH_KEY, item.getId());
            if (reaction != null) {
                peopleDocument.setLike(reaction.getLike());
                peopleDocument.setDisLike(reaction.getDislike());
            }
            return peopleDocument;
        }).toList();
        return new PageImpl<>(content, result.getPageable(), result.getTotalElements());
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

    public ResponseEntity<Map<String, String>> reactPeople(ReactionEnum reaction, String id) {
        peopleRepository.findById(id).orElseThrow(() -> new Error("People doesn't exist"));
        ReactionPeopleRecord reactionPeopleRecord = new ReactionPeopleRecord();
        ReactionPeopleRecord cachedReactionPeopleRecord = hashOperations.get(HASH_KEY, id);

        if (cachedReactionPeopleRecord != null) {
            reactionPeopleRecord = cachedReactionPeopleRecord;
        } else {
            reactionPeopleRecord.setLike(0L);
            reactionPeopleRecord.setDislike(0L);
            reactionPeopleRecord.setTimeStamp(LocalDate.now().toString());
        }

        if (reaction == ReactionEnum.LIKE) {
            reactionPeopleRecord.setLike(reactionPeopleRecord.getLike() + 1);
        } else {
            reactionPeopleRecord.setDislike(reactionPeopleRecord.getDislike() + 1);
        }
        hashOperations.put(HASH_KEY, id, reactionPeopleRecord);
        return new ResponseEntity<>(Map.of("message", "success"), HttpStatus.OK);
    }

    public void syncDataRedisToMongo() {
        List<PeopleDocument> peopleDocumentList = new ArrayList<>();
        hashOperations.keys(HASH_KEY).forEach(id -> {
            ReactionPeopleRecord reactionPeopleRecord = hashOperations.get(HASH_KEY, id);
            if(reactionPeopleRecord == null) return;
            peopleRepository.findById(id).ifPresent(item -> {
                item.setLike(reactionPeopleRecord.getLike());
                        item.setDisLike(reactionPeopleRecord.getDislike());
                        peopleDocumentList.add(item);
                    }
            );
        });
        peopleRepository.saveAll(peopleDocumentList);
    }


}
