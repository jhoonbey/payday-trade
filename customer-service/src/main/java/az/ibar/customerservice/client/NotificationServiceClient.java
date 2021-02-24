package az.ibar.customerservice.client;

import az.ibar.customerservice.client.model.MailSendDto;
import feign.Logger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "notification-service",
        configuration = NotificationServiceClient.FeignConfiguration.class,
        primary = false)
public interface NotificationServiceClient {

    @PostMapping("/notifications")
    void send(@RequestBody MailSendDto dto);

    class FeignConfiguration {
        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }
    }
}