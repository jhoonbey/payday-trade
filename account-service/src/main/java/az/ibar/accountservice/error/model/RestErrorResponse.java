package az.ibar.accountservice.error.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestErrorResponse {
    private String uuid;
    private String code;
    private String message;
    private Map<String, Object> properties;

}
