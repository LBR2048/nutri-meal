package ardjomand.leonardo.nutrimeal.data.common;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ardjomand.leonardo.nutrimeal.data.pojos.CartMeal;
import ardjomand.leonardo.nutrimeal.data.pojos.CompanyOrder;
import ardjomand.leonardo.nutrimeal.data.pojos.CustomerOrder;
import ardjomand.leonardo.nutrimeal.data.pojos.Meal;
import ardjomand.leonardo.nutrimeal.data.pojos.User;

public class FirebaseHelper {

    private static final String NODE_ORDERS = "orders";
    private static final String NODE_CUSTOMER_CART = "customer-cart";
    private static final String NODE_MEALS = "meals";
    private static final String NODE_AMOUNT = "amount";
    private static final String NODE_CUSTOMER_ORDERS = "customer-orders";
    private static final String NODE_USERS = "users";

    private final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();

    public FirebaseHelper() {

    }


    private DatabaseReference getUsersRef() {
        return databaseRef.child(NODE_USERS);
    }

    public DatabaseReference getOrdersRef() {
        return databaseRef.child(NODE_ORDERS);
    }

    public DatabaseReference getMealsRef() {
        return databaseRef.child(NODE_MEALS);
    }

    public DatabaseReference getCustomerCartMealsRef() {
        if (firebaseUser != null) {
            return databaseRef.child(NODE_CUSTOMER_CART).child(firebaseUser.getUid())
                    .child(NODE_MEALS);
        } else {
            return null;
        }
    }

    public DatabaseReference getCustomerCartAmountRef() {
        if (firebaseUser != null) {
            return databaseRef.child(NODE_CUSTOMER_CART).child(firebaseUser.getUid())
                    .child(NODE_AMOUNT);
        } else {
            return null;
        }
    }

    public DatabaseReference getCustomerOrdersRef() {
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

        } else if (clazz == Meal.class) {
            return getMealsRef();

        } else if (clazz == CartMeal.class) {
            return getCustomerCartMealsRef();

        } else if (clazz == CustomerOrder.class) {
            return getCustomerOrdersRef();

        } else {
            return null;
        }
    }


    private DatabaseReference getMealRef(String id) {
        return databaseRef.child(NODE_MEALS).child(id);
    }

    public DatabaseReference getItemRef(Class<?> clazz, String id) {
        if (clazz == Meal.class) {
            return getMealRef(id);

        } else if (clazz == CartMeal.class) {
            return getCustomerCartMealsRef();

        } else {
            return null;
        }
    }

}
