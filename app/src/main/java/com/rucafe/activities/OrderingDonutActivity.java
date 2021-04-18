package com.rucafe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rucafe.MainActivity;
import com.rucafe.R;
import com.rucafe.models.Donut;
import com.rucafe.models.Order;
import com.rucafe.utils.Constants;
import com.rucafe.utils.DonutFlavor;
import com.rucafe.utils.DonutType;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OrderingDonutActivity extends AppCompatActivity {

    // XML References
    private Spinner donutFlavorSpinner;
    private Spinner donutTypeSpinner;
    private Spinner donutQuantity;
    private Button addDonutToCart;
    private Button removeDonutFromCart;
    private Button addCartToOrder;
    private ListView currentCart;
    private TextView currentPrice;

    // All the donuts in our cart
    ArrayList<Donut> donutsInCart;
    // Currently selected properties
    DonutType currentlySelectedType;
    DonutFlavor currentlySelectedFlavor;
    Donut currentlySelectedDonutInCart;

    /**
     * On create lifetime cycle for our Activity
     * @param savedInstanceState The saved instance bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_donut);

        // Initialize members
        donutsInCart = new ArrayList<>();

        // Assign references to variables
        donutFlavorSpinner = (Spinner) findViewById(R.id.donut_flavor_spinner);
        donutTypeSpinner = (Spinner) findViewById(R.id.donut_type_spinner);
        donutQuantity = (Spinner) findViewById(R.id.donut_quantity_spinner);
        addDonutToCart = (Button) findViewById(R.id.add_donut_button);
        removeDonutFromCart = (Button) findViewById(R.id.remove_donut_button);
        addCartToOrder = (Button) findViewById(R.id.add_to_order_donut_button);
        currentCart = (ListView) findViewById(R.id.donut_current_cart_list);
        currentPrice = (TextView) findViewById(R.id.donut_price);

        // Populate spinners
        donutQuantity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.DONUT_QUANTITY_SPINNER_VALUES));
        donutFlavorSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DonutFlavor.values()));
        donutTypeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DonutType.values()));

        // Set cart to be selectable
        currentCart.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        currentCart.setSelector(R.color.primary);
    }

    /**
     * On Start lifetime cycle for our Activity
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Donut Flavor listener
        donutFlavorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentlySelectedFlavor = (DonutFlavor) donutFlavorSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // we don't use this, but we need to have it in order to compile
            }
        });

        // Donut Type listener
        donutTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentlySelectedType = (DonutType) donutTypeSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // we don't use this, but we need to have it in order to compile
            }
        });

        // Add current selection to cart listener
        addDonutToCart.setOnClickListener(v -> {
            this.addDonutSelectionToCart();
            removeDonutFromCart.setEnabled(false);
        });

        // Item in cart selected listener
        currentCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentlySelectedDonutInCart = (Donut) currentCart.getItemAtPosition(position);
                removeDonutFromCart.setEnabled(true);
            }
        });

        // Remove donut from cart listener
        removeDonutFromCart.setOnClickListener(v -> {
            this.removeSelectedDonutFromCart();
        });

        // Add to order listener
        addCartToOrder.setOnClickListener(v -> {
            this.addCartToOrder();
        });

        // render cart
        this.renderCart();
    }

    /**
     * Remove the currently selected donut from the cart
     */
    private void removeSelectedDonutFromCart() {
        // Remove from cart and disable button
        System.out.println(this.currentlySelectedDonutInCart);
        donutsInCart.remove(this.currentlySelectedDonutInCart);
        removeDonutFromCart.setEnabled(false);

        // Re-render cart and recompute price
        this.renderCart();
        this.recomputePrice();
    };

    /**
     * Add the current cart to the order
     */
    private void addCartToOrder() {
        // Add all donuts
        for(Donut donut : this.donutsInCart) {
            Order.getInstance().add(donut);
        }

        // Show toast
        Toast.makeText(getBaseContext(), R.string.successfully_added_to_order, Toast.LENGTH_SHORT).show();

        // Navigate back
        Intent gotoMainActivity = new Intent(this, MainActivity.class);
        startActivity(gotoMainActivity);
    }

    /**
     *  Create a new donut item based on the current selections and add it to the cart.
     */
    private void addDonutSelectionToCart() {
        Donut newDonut = new Donut(
                this.currentlySelectedType,
                this.currentlySelectedFlavor,
                (int) this.donutQuantity.getSelectedItem()
        );
        this.donutsInCart.add(newDonut);

        // Re-render cart and recompute price
        this.renderCart();
        this.recomputePrice();
    }

    /**
     * Re-render the cart list view
     */
    private void renderCart() {
        this.currentCart.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.donutsInCart));
        this.addCartToOrder.setEnabled(!this.donutsInCart.isEmpty());
    }

    /**
     * Recompute the price
     */
    private void recomputePrice() {
        // Add up the price of the cart
        double totalPrice = 0;
        for(Donut d : donutsInCart) {
            totalPrice += d.itemPrice();
        }

        // Update text field with currency format with 2 decimals
        this.currentPrice.setText(String.format(Constants.CURRENCY_FORMAT_STRING, totalPrice));

    }
}