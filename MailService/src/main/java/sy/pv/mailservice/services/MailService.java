package sy.pv.mailservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sy.pv.mailservice.dto.request.MailRequestDto;
import sy.pv.mailservice.repositories.MailRepository;

@RequiredArgsConstructor
@Service
public class MailService {
    private final MailRepository mailRepository;

    public String createMail(MailRequestDto mailRequestDto) throws Exception {
        return "Mail sended";
    }

}
