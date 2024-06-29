package sy.pv.memefamousperson.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private PeopleService peopleService;

    @Scheduled(fixedRate = 60000)
    public void syncDataPeopleRedis() {
        System.out.println("Sync data started");
        peopleService.syncDataRedisToMongo();
    }
}
