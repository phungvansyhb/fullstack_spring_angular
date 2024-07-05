package sy.pv.mailservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import sy.pv.mailservice.documents.MailDocument;
import sy.pv.mailservice.dto.request.MailRequestDto;
import sy.pv.mailservice.repositories.MailRepository;
import sy.pv.mailservice.services.PeopleServiceClient;

import java.time.LocalDate;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class MailService {

    private final MailRepository mailRepository;
    private final JavaMailSender mailSender;
    private final PeopleServiceClient peopleService;

    @Value("${spring.mail.username}")
    String mailAddress;

    public void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailAddress);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public String createMail(MailRequestDto mailRequestDto) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        try {
//            ResponseEntity<Boolean> check = restTemplate.exchange(
//                    "http://localhost:8080/api/people/checkMail/" + mailRequestDto.getTo(),
//                    HttpMethod.GET,
//                    null,
//                    Boolean.class);
            ResponseEntity<Boolean> check = peopleService.checkMailPeople(mailRequestDto.getTo());
            if (Objects.equals(check.getBody(), true)) {
                sendSimpleEmail(mailRequestDto.getTo(), mailRequestDto.getSubject(), mailRequestDto.getContent());
                mailRepository.save(MailDocument.builder()
                        .from(mailAddress)
                        .to(mailRequestDto.getTo())
                        .subject(mailRequestDto.getSubject())
                        .content(mailRequestDto.getContent())
                        .createdDate(LocalDate.now())
                        .build());
                return "Mail sent successfully";
            } else {
                return "Mail sent fail";
            }
        } catch (HttpClientErrorException e) {
            return "Mail sent fail";
        }
    }

}
