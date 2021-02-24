package az.ibar.stockservice.messages;

import az.ibar.stockservice.messages.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Source.class)
@RequiredArgsConstructor
public class MessageSender<T> {

    private final MessageChannel output;
    private final ObjectMapper objectMapper;

    public void send(Message<T> message) {
        try {

            var messageJson = objectMapper.writeValueAsString(message);

            System.out.println("STREAM MESSAGE SENT: " + messageJson);

            output.send(MessageBuilder.withPayload(messageJson)
                    .setHeader("type", message.getType())
                    .setHeader("a", "a")
                    .build());

        } catch (Exception e) {
            throw new RuntimeException("Could not transform and send message due to: " + e.getMessage(), e);
        }
    }
}