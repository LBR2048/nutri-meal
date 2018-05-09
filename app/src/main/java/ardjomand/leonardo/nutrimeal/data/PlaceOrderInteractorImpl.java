package ardjomand.leonardo.nutrimeal.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ardjomand.leonardo.nutrimeal.cart.CartPresenter;

public class PlaceOrderInteractorImpl implements PlaceOrderInteractor.Interactor {

    public static final String NODE_CUSTOMER_ORDERS = "customer-orders";
    public static final String NODE_SELECTED_MEALS = "selected-meals";

    // TODO add current customer ID
    private final String customerId = "customer1";

    // TODO add current order ID
    private final String orderId = "order1";

    private final CartPresenter cartPresenter;
    private final DatabaseReference ordersRef;

    public PlaceOrderInteractorImpl(CartPresenter presenter) {
        cartPresenter = presenter;

        ordersRef = FirebaseDatabase.getInstance().getReference()
                .child(NODE_CUSTOMER_ORDERS)
                .child(customerId)
                .child(orderId)
                .child(OrdersRepositoryImpl.NODE_SELECTED_MEALS);
    }

    @Override
    public void placeOrder() {
        // Copy cart to a new order
    }

}
