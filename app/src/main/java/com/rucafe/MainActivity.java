package com.rucafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.rucafe.activities.CurrentOrderActivity;
import com.rucafe.activities.StoreOrdersActivity;
import com.rucafe.activities.OrderingCoffeeActivity;
import com.rucafe.activities.OrderingDonutActivity;

/**
 * Main Menu and entry point for the entire application.
 * @author Reagan McFarland, Vatche Kafafian
 */
public class MainActivity extends AppCompatActivity {

    // XML References
    private Button orderCoffeeButton;
    private Button orderDonutButton;
    private Button currentOrderButton;
    private Button storeOrdersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign buttons by id
        orderCoffeeButton = (Button) findViewById(R.id.order_coffee);
        orderDonutButton = (Button) findViewById(R.id.order_donut);
        currentOrderButton = (Button) findViewById(R.id.current_order);
        storeOrdersButton = (Button) findViewById(R.id.store_orders);

        // Add on click handler for order coffee button
        orderCoffeeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToOrderingCoffee();
            }
        });

        // Add on click listener for order donut button
        orderDonutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToOrderingDonut();
            }
        });

        // Add on click listener for current order button
        currentOrderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToCurrentOrder();
            }
        });

        // Add on click listener for store orders button
        storeOrdersButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToStoreOrders();
            }
        });
    }

    /**
     * Navigate to the OrderingCoffeeActivity
     */
    void navigateToOrderingCoffee() {
        Intent gotoOrderCoffee = new Intent(this, OrderingCoffeeActivity.class);
        startActivity(gotoOrderCoffee);
    }

    /**
     * Navigate to the OrderingDonutActivity
     */
    void navigateToOrderingDonut() {
        Intent gotoOrderDonut = new Intent(this, OrderingDonutActivity.class);
        startActivity(gotoOrderDonut);
    }

    /**
     * Navigate to the CurrentOrderActivity
     */
    void navigateToCurrentOrder() {
        Intent gotoCurrentOrder = new Intent(this, CurrentOrderActivity.class);
        startActivity(gotoCurrentOrder);
    }

    /**
     * Navigate to the StoreOrdersActivity
     */
    void navigateToStoreOrders() {
        Intent gotoStoreOrders = new Intent(this, StoreOrdersActivity.class);
        startActivity(gotoStoreOrders);
    }
}
