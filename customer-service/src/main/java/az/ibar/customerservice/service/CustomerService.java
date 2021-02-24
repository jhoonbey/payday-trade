package az.ibar.customerservice.service;

import az.ibar.customerservice.model.dto.CustomerDto;
import az.ibar.customerservice.model.dto.CustomerRegisterDto;

public interface CustomerService {
    CustomerDto createCustomer(CustomerRegisterDto dto);

    void activateCustomer(String key);

    CustomerDto getCustomer(Long id);
}