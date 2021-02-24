package az.ibar.orderservice.messages;

import az.ibar.orderservice.messages.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(Source.class)
@RequiredArgsConstructor
public class MessageSender<T> {

    private final MessageChannel output;
    private final ObjectMapper objectMapper;

    public void send(Message<T> message) {
        try {

            var messageJson = objectMapper.writeValueAsString(message);

            log.info("STREAM MESSAGE SENT: {}", messageJson);

            output.send(MessageBuilder.withPayload(messageJson)
                    .setHeader("type", message.getType())
                    .build());

        } catch (Exception e) {
            throw new RuntimeException("Could not transform and send message due to: " + e.getMessage(), e);
        }
    }
}