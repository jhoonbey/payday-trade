package az.ibar.notificationservice.service;

import az.ibar.notificationservice.model.MailSendDto;

public interface MailService {
    void sendMail(MailSendDto dto);
}
