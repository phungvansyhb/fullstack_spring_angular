package sy.pv.mailservice.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sy.pv.mailservice.dto.request.MailRequestDto;
import sy.pv.mailservice.services.MailService;

@RestController
@RequestMapping("/api/mail")
@AllArgsConstructor
public class MailController {
    final MailService mailService;

    @PostMapping
    String CreatePeople(@RequestBody MailRequestDto mailRequestDto) throws Exception {
        return mailService.createMail(mailRequestDto);
    }
}
