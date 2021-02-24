package az.ibar.customerservice.mapper;

import az.ibar.customerservice.model.dto.CustomerDto;
import az.ibar.customerservice.model.dto.CustomerRegisterDto;
import az.ibar.customerservice.model.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Customer mapper tests")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerMapperImpl.class})
class CustomerMapperTest {

    @Autowired
    private CustomerMapper customerMapper;
    private static CustomerEntity customerEntity;

    @BeforeAll
    public static void setUp() {
        customerEntity = CustomerEntity.builder()
                .name("Jeyhun")
                .email("r.ceyhun2011@gmail.com")
                .build();
    }

    @Test
    @DisplayName("Given CustomerRegisterDto when map to customer entity then should return CustomerEntity")
    void givenCustomerRegisterDto_whenToCustomerEntity_thenReturnCustomerEntity() {
        var dto = CustomerRegisterDto.builder()
                .name("Jeyhun")
                .email("r.ceyhun2011@gmail.com")
                .build();

        CustomerEntity actual = customerMapper.toCustomerEntity(dto);

        assertThat(actual).isEqualToComparingFieldByField(customerEntity);
    }

    @Test
    @DisplayName("Given CustomerEntity when map to CustomerDto then should return CustomerDto")
    void givenCustomerEntity_whenToCustomerDto_thenReturnCustomerDto() {
        var dto = CustomerDto.builder()
                .name("Jeyhun")
                .email("r.ceyhun2011@gmail.com")
                .build();

        CustomerDto actual = customerMapper.toCustomerDto(customerEntity);

        assertThat(actual).isEqualToComparingFieldByField(dto);
    }
}