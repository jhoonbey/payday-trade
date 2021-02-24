package az.ibar.accountservice.util;

public interface AccountUtil {

    static String generateNumericString(int n) {
        String numericString = "0123456789";
        // create StringBuffer size of NumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            // generate a random number between 0 to NumericString variable length
            int index
                    = (int) (numericString.length()
                    * Math.random());
            // add Character one by one in end of sb
            sb.append(numericString.charAt(index));
        }

        return sb.toString();
    }

    String UPDATE_ACCOUNT_STOCK = "account_stock_update_key";

    String STOCKS = "stockList";
}
