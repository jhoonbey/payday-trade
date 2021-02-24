package az.ibar.orderservice.error;

public class InsufficientBalanceException extends CommonException {

    public InsufficientBalanceException() {
        super("INSUFFICIENT_BALANCE", "Balance is not enough");
    }

    public InsufficientBalanceException(String msg) {
        super("INSUFFICIENT_BALANCE", msg);
    }
}