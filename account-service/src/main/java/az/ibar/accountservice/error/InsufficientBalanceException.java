package az.ibar.accountservice.error;

public class InsufficientBalanceException extends CommonException {

    public InsufficientBalanceException() {
        super("INSUFFICIENT_BALANCE", "Account balance is not enough");
    }
}