package sy.pv.memefamousperson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@EnableMongoRepositories
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class MemeFamousPersonApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemeFamousPersonApplication.class, args);
    }

}
