package az.ibar.accountservice.error;

public class AccountNotFoundException extends CommonException {

    public AccountNotFoundException() {
        super("NOT_FOUND", "Account not found");
    }
}