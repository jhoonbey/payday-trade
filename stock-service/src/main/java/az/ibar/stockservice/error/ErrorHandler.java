package az.ibar.stockservice.error;

import az.ibar.stockservice.error.model.RestErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({StockNotFoundException.class})
    public RestErrorResponse handleStockNotFoundException(StockNotFoundException ex) {
        var errorResponse = RestErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.name())
                .message(ex.getErrorMessage())
                .uuid(ex.getErrorUuid())
                .build();
        return errorResponse;
    }
}