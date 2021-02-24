package az.ibar.customerservice.controller;

import az.ibar.customerservice.model.dto.CustomerDto;
import az.ibar.customerservice.service.CustomerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static az.ibar.customerservice.config.Serializer.convertToJson;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Customer controller unit tests")
@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService customerService;

    private final static Long customerId = 44L;
    private static CustomerDto customerDto;

    @BeforeAll
    public static void setup() {
        customerDto = CustomerDto.builder()
                .name("Test")
                .id(customerId)
                .email("test@test.com")
                .build();
    }

    @Test
    @DisplayName("Given CustomerDto when POST CustomerDto then should return customerId")
    void save() throws Exception {
        var dto = CustomerDto.builder()
                .name("Jeyhun")
                .email("r.ceyhun2011@gmail.com")
                .build();

        when(customerService.createCustomer(any())).thenReturn(customerDto);

        mvc.perform(post("/customers")
                .contentType(APPLICATION_JSON)
                .content(convertToJson(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.data.email").value(equalTo("test@test.com")));
    }

    @Test
    @DisplayName("Given customerId when GET customer then should return CustomerDto")
    void findCustomer() throws Exception {
        when(customerService.getCustomer(any())).thenReturn(customerDto);

        mvc.perform(get("/customers").param("customerId", customerId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(equalTo("test@test.com")));
    }
}