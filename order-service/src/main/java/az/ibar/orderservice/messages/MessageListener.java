package az.ibar.orderservice.messages;

import az.ibar.orderservice.messages.model.Message;
import az.ibar.orderservice.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static az.ibar.orderservice.util.OrderUtil.ORDER_COMPLETE;

@Component
@EnableBinding(Sink.class)
@RequiredArgsConstructor
@Slf4j
public class MessageListener {

    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    @StreamListener(target = Sink.INPUT)
    public void handle(String messageJson, @Header("type") String key) throws Exception {

        if (key.equals(ORDER_COMPLETE)) {
            log.info("Consuming complete order...{}", messageJson);
            Message<Long> message = objectMapper.readValue(messageJson, new TypeReference<>() {
            });
            orderService.completeOrder(message.getData());
        }
    }
}