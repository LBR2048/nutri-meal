package ardjomand.leonardo.nutrimeal.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ardjomand.leonardo.nutrimeal.cart.CartMeal;
import ardjomand.leonardo.nutrimeal.companyorders.CompanyOrder;
import ardjomand.leonardo.nutrimeal.customerorders.CustomerOrder;

public class FirebaseHelper {

    private static final String NODE_ORDERS = "orders";
    private static final String NODE_CUSTOMER_CART = "customer-cart";
    private static final String NODE_MEALS = "meals";
    private static final String NODE_CUSTOMER_ORDERS = "customer-orders";

    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseHelper() {

    }

    public DatabaseReference getOrdersRef() {
        if (firebaseUser != null) {
            return FirebaseDatabase.getInstance().getReference().child(NODE_ORDERS);
        } else {
            return null;
        }
    }

    public DatabaseReference getCustomerCartRef() {
        if (firebaseUser != null) {
            return FirebaseDatabase.getInstance().getReference()
                    .child(NODE_CUSTOMER_CART).child(firebaseUser.getUid()).child(NODE_MEALS);
        } else {
            return null;
        }
    }

    public DatabaseReference getCustomerOrdersRef() {
        if (firebaseUser != null) {
            return FirebaseDatabase.getInstance().getReference()
                    .child(NODE_CUSTOMER_ORDERS).child(firebaseUser.getUid());
        } else {
            return null;
        }
    }

    public DatabaseReference getItemsRef(Class<?> clazz) {
        if (clazz == CartMeal.class) {
            return getCustomerCartRef();
        } else if (clazz == CompanyOrder.class) {
            return getOrdersRef();
        } else if (clazz == CustomerOrder.class) {
            return getCustomerOrdersRef();
        } else {
            return null;
        }
    }

}
