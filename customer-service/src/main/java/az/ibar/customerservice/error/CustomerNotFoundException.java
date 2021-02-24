package az.ibar.customerservice.error;

public class CustomerNotFoundException extends CommonException {

    public CustomerNotFoundException() {
        super("NOT_FOUND", "Customer not found");
    }
}