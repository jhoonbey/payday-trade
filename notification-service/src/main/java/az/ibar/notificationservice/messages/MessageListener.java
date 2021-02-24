package az.ibar.notificationservice.messages;

import az.ibar.notificationservice.client.CustomerServiceClient;
import az.ibar.notificationservice.client.model.CustomerDto;
import az.ibar.notificationservice.messages.model.Message;
import az.ibar.notificationservice.messages.model.OrderMessage;
import az.ibar.notificationservice.model.MailSendDto;
import az.ibar.notificationservice.service.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static az.ibar.notificationservice.util.NotificationUtil.COMPLETE_ORDER_NOTIFICATION;
import static az.ibar.notificationservice.util.NotificationUtil.ORDER_COMPLETION_MESSAGE;

@Component
@EnableBinding(Sink.class)
@RequiredArgsConstructor
@Slf4j
public class MessageListener {

    private final ObjectMapper objectMapper;
    private final MailService mailService;
    private final CustomerServiceClient customerServiceClient;

    @StreamListener(target = Sink.INPUT)
    public void handle(String messageJson, @Header("type") String key) throws JsonProcessingException {
        System.out.println("Consuming complete order notification..." + messageJson + "\n key:" + key);

        if (key.equals(COMPLETE_ORDER_NOTIFICATION)) {
            Message<OrderMessage> message = objectMapper.readValue(messageJson, new TypeReference<>() {
            });
            CustomerDto user = customerServiceClient.getCustomer(message.getData().getCustomerId());

            mailService.sendMail(new MailSendDto(user.getEmail(), ORDER_COMPLETION_MESSAGE, message.getData().getMessage()));

        }
        /*else if (key.equals(REGISTRATION_ACTIVATION)) {
            Message<String> message = objectMapper.readValue(messageJson, new TypeReference<>() {
            });

            mailService.sendMail(new MailSendDto(message.getData(), ACTIVATION_SUBJECT, ACTIVATION_MESSAGE));
            // sending activation email ...
        }*/
    }

}
