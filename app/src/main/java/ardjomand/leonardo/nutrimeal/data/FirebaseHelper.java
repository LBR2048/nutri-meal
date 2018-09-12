package ardjomand.leonardo.nutrimeal.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ardjomand.leonardo.nutrimeal.auth.User;
import ardjomand.leonardo.nutrimeal.cart.CartMeal;
import ardjomand.leonardo.nutrimeal.companyorders.CompanyOrder;
import ardjomand.leonardo.nutrimeal.customerorders.CustomerOrder;

public class FirebaseHelper {

    private static final String NODE_ORDERS = "orders";
    private static final String NODE_CUSTOMER_CART = "customer-cart";
    private static final String NODE_MEALS = "meals";
    private static final String NODE_CUSTOMER_ORDERS = "customer-orders";
    private static final String NODE_USERS = "users";

    private final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

    FirebaseHelper() {

    }

    private DatabaseReference getUsersRef() {
        return databaseRef.child(NODE_USERS);
    }

    private DatabaseReference getOrdersRef() {
        return databaseRef.child(NODE_ORDERS);
    }

    private DatabaseReference getCustomerCartRef() {
        if (firebaseUser != null) {
            return databaseRef.child(NODE_CUSTOMER_CART).child(firebaseUser.getUid()).child(NODE_MEALS);
        } else {
            return null;
        }
    }

    private DatabaseReference getCustomerOrdersRef() {
        if (firebaseUser != null) {
            return databaseRef.child(NODE_CUSTOMER_ORDERS).child(firebaseUser.getUid());
        } else {
            return null;
        }
    }

    // TODO this is not very object-oriented, but is it worth having several repositories
    // instead of a generic one just to avoid these if-else clauses?
    public DatabaseReference getItemsRef(Class<?> clazz) {
        if (clazz == User.class) {
            return getUsersRef();

        } else if (clazz == CompanyOrder.class) {
            return getOrdersRef();

        } else if (clazz == CartMeal.class) {
            return getCustomerCartRef();

        } else if (clazz == CustomerOrder.class) {
            return getCustomerOrdersRef();

        } else {
            return null;
        }
    }

}
