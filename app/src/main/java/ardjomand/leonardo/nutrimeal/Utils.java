package ardjomand.leonardo.nutrimeal;

import android.content.Context;
import android.support.annotation.NonNull;

import java.text.NumberFormat;
import java.util.Map;

import ardjomand.leonardo.nutrimeal.cart.CartMeal;
import ardjomand.leonardo.nutrimeal.companyorders.CompanyOrder;
import ardjomand.leonardo.nutrimeal.customerorders.CustomerOrder;

public class Utils {

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

        StringBuilder builder = new StringBuilder();

        builder.append(formatOrderKey(context, companyOrder.getKey()));
        builder.append("\n");

        builder.append(companyOrder.getCustomerKey());
        builder.append("\n") ;

        builder.append(formatDeliveryStatus(context, companyOrder.isDelivered()));
        builder.append("\n");

        builder.append(formatAmount(companyOrder.getAmount()));
        builder.append("\n");

        builder.append(formatMeals(context, companyOrder.getMeals()));

        return builder.toString();
    }

    @NonNull
    public static String formatCustomerOrder(Context context, CustomerOrder customerOrder) {

        StringBuilder builder = new StringBuilder();

        builder.append(formatOrderKey(context, customerOrder.getKey()));
        builder.append("\n");

        builder.append(formatDeliveryStatus(context, customerOrder.isDelivered()));
        builder.append("\n");

        builder.append(formatAmount(customerOrder.getAmount()));

        return builder.toString();
    }
}
