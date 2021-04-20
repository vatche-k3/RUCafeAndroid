package com.rucafe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rucafe.MainActivity;
import com.rucafe.R;
import com.rucafe.models.MenuItem;
import com.rucafe.models.Order;
import com.rucafe.utils.Constants;

/**
 * Current Order Activity. List the details of the current order, and lets you remove items from the order before placing.
 *
 * @author Reagan McFarland, Vatche Kafafian
 */
public class CurrentOrderActivity extends AppCompatActivity {

    //XML References
    private TextView totalPriceTextView;
    private TextView salesTaxPriceTextView;
    private TextView subTotalPriceTextView;
    private ListView currentOrderListView;
    private Button removeSelectedOrderButton;
    private Button placeOrderButton;

    private Order currentOrder;
    private MenuItem currentlySelectedItem;
    double totalPrice;
    double subTotalPrice;
    double salesTaxPrice;

    /**
     * On Create life cycle method for our Activity
     * @param savedInstanceState The saved instance bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_order);

        currentlySelectedItem = null;
        currentOrder = Order.getInstance();

        // Assign references
        totalPriceTextView = (TextView) findViewById(R.id.total_current_order_amount);
        subTotalPriceTextView = (TextView) findViewById(R.id.sub_total_current_order_amount);
        salesTaxPriceTextView = (TextView) findViewById(R.id.sales_tax_current_order_amount);
        currentOrderListView = (ListView) findViewById(R.id.current_order_list_view);
        placeOrderButton = (Button) findViewById(R.id.place_order_current_order_button);
        removeSelectedOrderButton = (Button) findViewById(R.id.remove_item_current_order_button);

        // Set order list view to be selectable
        currentOrderListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        currentOrderListView.setSelector(R.color.primary);
    }

    /**
     * On Start life cycle method for our Activity
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Order list view on item click listener
        currentOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentlySelectedItem = (MenuItem) currentOrderListView.getItemAtPosition(position);
                removeSelectedOrderButton.setEnabled(true);
            }
        });

        // Remove item from order listener
        removeSelectedOrderButton.setOnClickListener(v -> {
            this.removeItemFromOrder();
        });

        // Place order listener
        placeOrderButton.setOnClickListener(v -> {
            this.placeOrder();
        });

        // Update UI and recompute price
        this.updateUI();
        this.recomputePrice();
    }

    /**
     * Remove selected item from order
     */
    private void removeItemFromOrder() {
        currentOrder.remove(this.currentlySelectedItem);
        // Update UI and recompute price
        this.updateUI();
        this.recomputePrice();
    }

    /**
     * Update UI, clear selections and repopulate order listview. Also check if placeOrder should be disabled
     */
    private void updateUI() {
        // Update UI
        currentOrderListView.clearChoices();
        currentOrderListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currentOrder.getItemsInOrder()));
        this.removeSelectedOrderButton.setEnabled(false);

        // Check if place order button should be disabled
        placeOrderButton.setEnabled(!this.currentOrder.getItemsInOrder().isEmpty());
    }

    /**
     * Recompute all the prices and update text boxes
     */
    private void recomputePrice() {
        // reset subtotal and recalc
        subTotalPrice = 0;
        for(MenuItem menuItem : this.currentOrder.getItemsInOrder()) {
            subTotalPrice += menuItem.itemPrice();
        }
        // Update subtotal formatted as currency
        subTotalPriceTextView.setText(String.format(Constants.CURRENCY_FORMAT_STRING, subTotalPrice));

        // reset sales tax and recalc
        salesTaxPrice = subTotalPrice * Constants.SALES_TAX_RATE;
        // Update sales tax formatted as currency
        salesTaxPriceTextView.setText(String.format(Constants.CURRENCY_FORMAT_STRING, salesTaxPrice));

        // reset total price and recalc
        totalPrice = subTotalPrice + salesTaxPrice;
        // Update total formatted as currency
        totalPriceTextView.setText(String.format(Constants.CURRENCY_FORMAT_STRING, totalPrice));
    }

    /**
     * Place the order, updating StoreOrders.
     */
    private void placeOrder() {
        // finalize the store order, which adds it to StoreOrders
        currentOrder.finalizeOrder(this.totalPrice);

        // Show toast
        Toast.makeText(getBaseContext(), R.string.successfully_placed_order, Toast.LENGTH_SHORT).show();

        // Navigate back
        Intent gotoMainActivity = new Intent(this, MainActivity.class);
        startActivity(gotoMainActivity);
    }
}