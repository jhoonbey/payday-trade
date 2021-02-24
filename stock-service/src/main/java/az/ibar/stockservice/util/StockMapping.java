package az.ibar.stockservice.util;

import java.util.HashMap;
import java.util.Map;

public class StockMapping {
    public static Map<Long, String> getStocks() {
        Map<Long, String> map = new HashMap<>();
        map.put(1L, "Apple");
        map.put(2L, "Zoom");
        map.put(3L, "Alibaba");
        return map;
    }
}