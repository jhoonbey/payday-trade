package az.ibar.stockservice.error;

public class StockNotFoundException extends CommonException {

    public StockNotFoundException() {
        super("NOT_FOUND", "Stock not found");
    }
}