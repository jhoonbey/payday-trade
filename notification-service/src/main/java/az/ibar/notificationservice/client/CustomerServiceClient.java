package az.ibar.notificationservice.client;

import az.ibar.notificationservice.client.model.CustomerDto;
import feign.Logger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "customer-service",
        configuration = CustomerServiceClient.FeignConfiguration.class,
        primary = false)
public interface CustomerServiceClient {

    @GetMapping(value = "/customers", params = "customerId")
    CustomerDto getCustomer(@RequestParam("customerId") Long customerId);

    class FeignConfiguration {

        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }

    }
}
