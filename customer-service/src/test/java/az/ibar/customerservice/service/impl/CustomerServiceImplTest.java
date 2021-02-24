package az.ibar.customerservice.service.impl;

import az.ibar.customerservice.client.NotificationServiceClient;
import az.ibar.customerservice.error.CustomerIsExistException;
import az.ibar.customerservice.error.CustomerNotFoundException;
import az.ibar.customerservice.mapper.CustomerMapper;
import az.ibar.customerservice.model.dto.CustomerDto;
import az.ibar.customerservice.model.dto.CustomerRegisterDto;
import az.ibar.customerservice.model.entity.CustomerEntity;
import az.ibar.customerservice.repository.CustomerRepository;
import az.ibar.customerservice.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Customer service tests")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerServiceImpl.class})
class CustomerServiceImplTest {

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerMapper customerMapper;

    @MockBean
    private NotificationServiceClient notificationServiceClient;

    @Autowired
    private CustomerService customerService;

    private final static Long ID = 44L;
    private final static String NAME = "Jeyhun";
    private final static String EMAIL = "r.ceyhun2011@gmail.com";
    private final static boolean isActivated = true;

    @Test
    @DisplayName("Given CustomerDto when saveCustomer then should return customerId")
    void saveCustomer() {
        var customerEntity = CustomerEntity.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .activated(isActivated)
                .build();

        var customerRegisterDto = CustomerRegisterDto.builder()
                .name(NAME)
                .email(EMAIL)
                .build();

        var customerDto = CustomerDto.builder()
                .id(ID)
                .build();

        when(customerRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(customerMapper.toCustomerEntity(any())).thenReturn(customerEntity);
        when(customerRepository.save(any())).thenReturn(customerEntity);
        when(customerMapper.toCustomerDto(any())).thenReturn(customerDto);

        CustomerDto result = customerService.createCustomer(customerRegisterDto);


        verify(customerRepository, times(1)).save(customerEntity);

        assertEquals(ID, result.getId());
    }


    @Test
    @DisplayName("Given exist email when createCustomer then should throw CustomerNotFoundException")
    void givenBadArgument_whenCreateCustomer_thenThrowCustomerNotFoundException() {
        var customerEntity = CustomerEntity.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .build();

        var customerRegisterDto = CustomerRegisterDto.builder()
                .name(NAME)
                .email(EMAIL)
                .build();

        when(customerRepository.findByEmail(any())).thenReturn(Optional.of(customerEntity));

        assertThatExceptionOfType(CustomerIsExistException.class)
                .isThrownBy(() -> customerService.createCustomer(customerRegisterDto))
                .withMessage("Customer is exist");

        verify(customerRepository, times(1)).findByEmail(any());
    }


    @Test
    @DisplayName("Given customerId when getCustomer then should return CustomerDto")
    void getCustomerById() {
        var customerEntity = CustomerEntity.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .build();

        var customerDto = CustomerDto.builder()
                .id(ID)
                .build();

        when(customerRepository.findById(any(Long.class))).thenReturn(Optional.of(customerEntity));

        when(customerMapper.toCustomerDto(any(CustomerEntity.class))).thenReturn(customerDto);

        CustomerDto actual = customerService.getCustomer(ID);

        verify(customerRepository, times(1)).findById(ID);

        assertEquals(ID, actual.getId());
    }

    @Test
    @DisplayName("Given incorrect customerId when getCustomer then throw CustomerNotFoundException")
    void getCustomerByIncorrectId() {
        when(customerRepository.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> customerService.getCustomer(ID))
                .withMessage("Customer not found");

        verify(customerRepository, times(1)).findById(ID);
    }
}