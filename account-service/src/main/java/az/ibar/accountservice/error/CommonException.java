package az.ibar.accountservice.error;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CommonException extends RuntimeException {

    private final String errorUuid;
    private final String errorCode;
    private final String errorMessage;

    public CommonException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorUuid = uuid();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    private String uuid() {
        return UUID.randomUUID().toString();
    }
}
