package az.ibar.customerservice.controller;

import az.ibar.customerservice.error.model.RestResponse;
import az.ibar.customerservice.model.dto.CustomerDto;
import az.ibar.customerservice.model.dto.CustomerRegisterDto;
import az.ibar.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public RestResponse<CustomerDto> register(@RequestBody CustomerRegisterDto dto) {
        return new RestResponse<>(customerService.createCustomer(dto));
    }

    @GetMapping(value = "/activate", params = "key")
    public RestResponse<String> activateCustomer(@RequestParam("key") @NotNull String key) {
        customerService.activateCustomer(key);
        return new RestResponse<>("Successfully activated");
    }

    @GetMapping(params = "customerId")
    public CustomerDto getCustomer(@RequestParam("customerId") Long customerId) {
        return customerService.getCustomer(customerId);
    }
}