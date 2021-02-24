package az.ibar.customerservice.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRegisterDto {

    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String passwordAgain;
}