package az.ibar.notificationservice.controllers;

import az.ibar.notificationservice.model.MailSendDto;
import az.ibar.notificationservice.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final MailService mailService;

    @PostMapping
    public void send(@RequestBody MailSendDto dto) {
        mailService.sendMail(dto);
    }
}