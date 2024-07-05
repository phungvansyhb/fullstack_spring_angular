package sy.pv.mailservice.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:9000/api/people" ,name = "people-service")
public interface PeopleServiceClient {
    @GetMapping("/checkMail/{mail}")
    ResponseEntity<Boolean> checkMailPeople(@PathVariable String mail);
}
