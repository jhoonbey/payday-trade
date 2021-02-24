package az.ibar.customerservice.error.model;

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

    public RestErrorResponse(String uuid, String code, String message) {
        this.uuid = uuid;
        this.code = code;
        this.message = message;
    }
}
