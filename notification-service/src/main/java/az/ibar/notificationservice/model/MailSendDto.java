package az.ibar.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailSendDto {
    private String toMail;
    private String subject;
    private String message;
}
