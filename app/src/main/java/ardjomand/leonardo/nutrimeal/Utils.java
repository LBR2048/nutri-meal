package ardjomand.leonardo.nutrimeal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import java.text.NumberFormat;
import java.util.Map;

import ardjomand.leonardo.nutrimeal.cart.CartMeal;
import ardjomand.leonardo.nutrimeal.companyorders.CompanyOrder;
import ardjomand.leonardo.nutrimeal.customerorders.CustomerOrder;

public class Utils {

    // Prevents the class from being instantiated
    // https://stackoverflow.com/questions/25223553/how-can-i-create-an-utility-class
    private Utils() {}

    public static String formatOrderKey(Context mContext, String key) {
        return mContext.getString(R.string.order_id, key);
    }

    @NonNull
    public static String formatDeliveryStatus(Context mContext, boolean delivered) {
        return delivered
                ? mContext.getString(R.string.order_delivered)
                : mContext.getString(R.string.order_not_delivered);
    }

    @NonNull
    public static String formatAmount(long amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        return format.format(amount);
    }

    @NonNull
    public static String formatMeals(Context context, Map<String, CartMeal> mealsMap) {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, CartMeal> pair : mealsMap.entrySet()) {
            CartMeal cartMeal = pair.getValue();

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
            String formattedUnitPrice = currencyFormat.format(cartMeal.getUnitPrice());
            String formattedTotalPrice = currencyFormat.format(cartMeal.getTotalPrice());
            String cartMealText = context.getString(R.string.orders_cart_meal_price,
                    cartMeal.getName(), cartMeal.getQuantity(), formattedUnitPrice, formattedTotalPrice);

            builder.append(cartMealText);
            builder.append("\n");
        }

        return builder.toString().trim();
    }

    @NonNull
    public static String formatCompanyOrder(Context context, CompanyOrder companyOrder) {

        return formatOrderKey(context, companyOrder.getKey()) +
                "\n" +
                companyOrder.getCustomerKey() +
                "\n" +
                formatDeliveryStatus(context, companyOrder.isDelivered()) +
                "\n" +
                formatAmount(companyOrder.getAmount()) +
                "\n" +
                formatMeals(context, companyOrder.getMeals());
    }

    @NonNull
    public static String formatCustomerOrder(Context context, CustomerOrder customerOrder) {

        return formatOrderKey(context, customerOrder.getKey()) +
                "\n" +
                formatDeliveryStatus(context, customerOrder.isDelivered()) +
                "\n" +
                formatAmount(customerOrder.getAmount());
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
