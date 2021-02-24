package az.ibar.notificationservice.service.impl;

import az.ibar.notificationservice.model.MailSendDto;
import az.ibar.notificationservice.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(MailSendDto dto) {
        var msg = new SimpleMailMessage();
        msg.setTo(dto.getToMail());
        msg.setSubject(dto.getSubject());
        msg.setText(dto.getMessage());

        javaMailSender.send(msg);
    }
}
