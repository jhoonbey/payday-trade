package az.ibar.accountservice.messages;

import az.ibar.accountservice.messages.model.Message;
import az.ibar.accountservice.model.dto.AccountStockDto;
import az.ibar.accountservice.service.AccountService;
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

import static az.ibar.accountservice.util.AccountUtil.UPDATE_ACCOUNT_STOCK;

@Component
@Slf4j
@EnableBinding(Sink.class)
@RequiredArgsConstructor
public class MessageListener {

    private final ObjectMapper objectMapper;
    private final AccountService accountService;

    @StreamListener(target = Sink.INPUT)
    public void handleUpdateAccount(String messageJson, @Header("type") String key) throws JsonProcessingException {

        if (key.equalsIgnoreCase(UPDATE_ACCOUNT_STOCK)) {
            Message<AccountStockDto> message = objectMapper.readValue(messageJson, new TypeReference<>() {
            });

            accountService.updateAccount(message.getData());
        }
    }

}
