package az.ibar.orderservice.error;

public class OrderNotFoundException extends CommonException {

    public OrderNotFoundException() {
        super("NOT_FOUND", "Order not found");
    }
}