package az.ibar.stockservice.util;

import java.math.BigDecimal;

public interface StockUtil {
    String ORDER_LIST_KEY = "order_list_key";
    String STOCK_LIST_KEY = "stock_list_key";
    BigDecimal DEFAULT_PRICE_MIN = BigDecimal.valueOf(1000);
    BigDecimal DEFAULT_PRICE_MAX = BigDecimal.valueOf(2000);
    String ORDER_COMPLETE_KEY = "order_complete_key";
    String ORDER_CREATE_KEY = "order_create_key";
    String ACCOUNT_STOCK_UPDATE_KEY = "account_stock_update_key";
    String ORDER_COMPLETE_NOTIFICATION_KEY = "order_complete_notification_key";
}
