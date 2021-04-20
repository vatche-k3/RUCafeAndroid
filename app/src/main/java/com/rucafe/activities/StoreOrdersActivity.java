package com.rucafe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.rucafe.R;
import com.rucafe.models.MenuItem;
import com.rucafe.models.Order;
import com.rucafe.models.StoreOrders;

import java.util.ArrayList;

/**
 * Store Orders Activity. Views all of the orders that have been placed
 *
 * @author Reagan McFarland, Vatche Kafafian
 */
public class StoreOrdersActivity extends AppCompatActivity {

    // XML References
    private ListView storeOrdersView;
    private Button removeSelectedOrder;

    private Order currentlySelectedOrder;

    /**
     * On Create life cycle method for our Activity
     * @param savedInstanceState The saved instance bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);

        currentlySelectedOrder = null;

        // Assign references
        storeOrdersView = (ListView) findViewById(R.id.store_orders_list_view);
        removeSelectedOrder = (Button) findViewById(R.id.remove_store_order_button);

        // Set list view to be selectable
        storeOrdersView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        storeOrdersView.setSelector(R.color.primary);
    }

    /**
     * On Start life cycle method for our Activity
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Build cards
        buildOrderList();

        // Reset selected order to be disabled
        removeSelectedOrder.setEnabled(false);

        // on selection listener
        storeOrdersView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find which order it is from string
                String selectedItemString = (String) storeOrdersView.getItemAtPosition(position);
                for(Order order : StoreOrders.getInstance().getOrders()) {
                    // we just want to compare the first item in the new line split, since that will be the order string
                    String[] componentsOfOrder = order.toString().split("\n");
                    if(order.toString().equals(componentsOfOrder[0])) {
                        // This is the same order!
                        currentlySelectedOrder = order;
                        removeSelectedOrder.setEnabled(true);
                        return;
                    }
                }
            }
        });

        // Remove selected order listener
        removeSelectedOrder.setOnClickListener(v -> {
            this.removeSelectedOrder();
        });
    }

    /**
     * Remove an order from the store orders.
     */
    private void removeSelectedOrder() {
        System.out.println(this.currentlySelectedOrder);
        // Remove order, reset currently selected order, reset order ids, and rebuild order list
        StoreOrders.getInstance().remove(currentlySelectedOrder);
        currentlySelectedOrder = null;
        StoreOrders.getInstance().resetOrderIds();
        buildOrderList();

        // Show toast
        Toast.makeText(getBaseContext(), R.string.successfully_removed_order, Toast.LENGTH_SHORT).show();

        // Disable remove selected order button
        removeSelectedOrder.setEnabled(false);
    }

    /**
     * Build the order cards to populate the view
     */
    private void buildOrderList() {
        ArrayList<Order> orders = StoreOrders.getInstance().getOrders();
        ArrayList<String> parsedOrderContentItems = new ArrayList<>();
        // Build a card for each order
        for(Order order : orders) {
            String orderTitle = order.toString() + "\n";

            // Add each menu item to the string
            String orderContents = "";
            for(MenuItem item : order.getItemsInOrder()) {
                orderContents += item.toString() + "\n";
            }

            // Build new card
            parsedOrderContentItems.add(orderTitle + orderContents);
        }

        // set list view content
        storeOrdersView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, parsedOrderContentItems));
    }
}