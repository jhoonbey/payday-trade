package az.ibar.stockservice.messages;

import az.ibar.stockservice.messages.model.Message;
import az.ibar.stockservice.model.OrderDto;
import az.ibar.stockservice.repository.OrderRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import static az.ibar.stockservice.util.StockUtil.ORDER_CREATE_KEY;

@Component
@EnableBinding(Sink.class)
@RequiredArgsConstructor
@Slf4j
public class MessageListener {

    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    @StreamListener(target = Sink.INPUT)
    public void handle(String messageJson, @Header("type") String key) throws Exception {
        log.info("messageJson: {}", messageJson);
        log.info("key: {}", key);
        if (key.equals(ORDER_CREATE_KEY)) {
            Message<OrderDto> message = objectMapper.readValue(messageJson, new TypeReference<>() {
            });
            var orderDto = message.getData();
            orderDto.setOrderId(message.getId());

            orderRepository.saveOrder(orderDto);
        }

    }
}