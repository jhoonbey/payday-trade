package az.ibar.orderservice.error;

import az.ibar.orderservice.error.model.RestErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({OrderNotFoundException.class})
    public RestErrorResponse handleOrderNotFoundException(OrderNotFoundException ex) {
        var errorResponse = RestErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.name())
                .message(ex.getErrorMessage())
                .uuid(ex.getErrorUuid())
                .build();

        log.error("Request parameter invalid : {}, code: {}, message: {}",
                errorResponse.getUuid(), errorResponse.getCode(), errorResponse.getMessage());

        return errorResponse;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InsufficientBalanceException.class})
    public RestErrorResponse handleInsufficientBalanceException(InsufficientBalanceException ex) {
        var errorResponse = RestErrorResponse.builder()
                .code(ex.getErrorCode())
                .message(ex.getErrorMessage())
                .uuid(ex.getErrorUuid())
                .build();

        log.error("Request parameter invalid : {}, code: {}, message: {}",
                errorResponse.getUuid(), errorResponse.getCode(), errorResponse.getMessage());

        return errorResponse;
    }
}