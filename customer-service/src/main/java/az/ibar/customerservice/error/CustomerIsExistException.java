package az.ibar.customerservice.error;

public class CustomerIsExistException extends CommonException {

    public CustomerIsExistException() {
        super("DATA_IS_EXIST", "Customer is exist");
    }

    public CustomerIsExistException(String msg) {
        super("DATA_IS_EXIST", msg);
    }
}
