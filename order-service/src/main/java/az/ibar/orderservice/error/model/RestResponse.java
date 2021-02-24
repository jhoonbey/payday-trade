package az.ibar.orderservice.error.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base response class for Rest requests
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> {

    private T data;
}
