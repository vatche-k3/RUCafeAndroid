package com.rucafe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rucafe.MainActivity;
import com.rucafe.R;
import com.rucafe.models.Coffee;
import com.rucafe.models.Order;
import com.rucafe.utils.CoffeeAddin;
import com.rucafe.utils.CoffeeSize;
import com.rucafe.utils.Constants;

import java.lang.reflect.Array;

public class OrderingCoffeeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // XML References
    private Spinner creamQuantity;
    private Spinner syrupQuantity;
    private Spinner milkQuantity;
    private Spinner caramelQuantity;
    private Spinner whippedCreamQuantity;
    private CheckBox creamCheckbox;
    private CheckBox syrupCheckbox;
    private CheckBox milkCheckbox;
    private CheckBox caramelCheckbox;
    private CheckBox whippedCreamCheckbox;
    private TextView coffeePrice;
    private Spinner coffeeQuantity;
    private Spinner coffeeSize;
    private Button addCoffeeToOrder;

    // Current coffee
    Coffee currentCoffee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_coffee);

        currentCoffee = new Coffee();

        // Assign references to variables
        creamQuantity = (Spinner) findViewById(R.id.cream_quantity);
        syrupQuantity = (Spinner) findViewById(R.id.syrup_quantity);
        milkQuantity = (Spinner) findViewById(R.id.milk_quantity);
        caramelQuantity = (Spinner) findViewById(R.id.caramel_quantity);
        whippedCreamQuantity = (Spinner) findViewById(R.id.whipped_cream_quantity);

        creamCheckbox = (CheckBox) findViewById(R.id.creamCheckBox);
        syrupCheckbox = (CheckBox) findViewById(R.id.syrupCheckBox);
        milkCheckbox = (CheckBox) findViewById(R.id.milkCheckBox);
        caramelCheckbox = (CheckBox) findViewById(R.id.caramelCheckBox);
        whippedCreamCheckbox = (CheckBox) findViewById(R.id.whippedCreamCheckBox);

        coffeePrice = (TextView) findViewById(R.id.coffeePrice);
        coffeeQuantity = (Spinner) findViewById(R.id.coffee_quantity);
        coffeeSize = (Spinner) findViewById(R.id.coffee_size);
        addCoffeeToOrder = (Button) findViewById(R.id.add_coffee_order_button);

        // Populate spinners
        creamQuantity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.COFFEE_ADDIN_SPINNER_VALUES));
        syrupQuantity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.COFFEE_ADDIN_SPINNER_VALUES));
        milkQuantity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.COFFEE_ADDIN_SPINNER_VALUES));
        caramelQuantity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.COFFEE_ADDIN_SPINNER_VALUES));
        whippedCreamQuantity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.COFFEE_ADDIN_SPINNER_VALUES));
        coffeeQuantity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.QUANTITY_SPINNER_VALUES));
        coffeeSize.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, CoffeeSize.values()));

        // Disable spinners by default
        creamQuantity.setEnabled(false);
        syrupQuantity.setEnabled(false);
        milkQuantity.setEnabled(false);
        caramelQuantity.setEnabled(false);
        whippedCreamQuantity.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // OnChange listener for quantity spinners
        creamQuantity.setOnItemSelectedListener(this);
        syrupQuantity.setOnItemSelectedListener(this);
        milkQuantity.setOnItemSelectedListener(this);
        caramelQuantity.setOnItemSelectedListener(this);
        whippedCreamQuantity.setOnItemSelectedListener(this);

        currentCoffee.setSize(CoffeeSize.GRANDE);

        // OnClick listener for creamCheckbox
        creamCheckbox.setOnClickListener(view -> {
            boolean isChecked = ((CheckBox)view).isChecked();
            if(isChecked) {
                creamQuantity.setEnabled(true);
            } else {
                creamQuantity.setEnabled(false);
            }

            // Handle state change and recompute price
            handleCoffeeAddinCheckbox(CoffeeAddin.CREAM, isChecked, creamQuantity);
            recomputeItemPrice();
        });

        // OnClick listener for syrupCheckbox
        syrupCheckbox.setOnClickListener(view -> {
            boolean isChecked = ((CheckBox)view).isChecked();
            if(isChecked) {
                syrupQuantity.setEnabled(true);
            } else {
                syrupQuantity.setEnabled(false);
            }

            // Handle state change and recompute price
            handleCoffeeAddinCheckbox(CoffeeAddin.SYRUP, isChecked, syrupQuantity);
            recomputeItemPrice();
        });

        /// OnClick listener for milkCheckbox
        milkCheckbox.setOnClickListener(view -> {
            boolean isChecked = ((CheckBox)view).isChecked();
            if(isChecked) {
                milkQuantity.setEnabled(true);
            } else {
                milkQuantity.setEnabled(false);
            }

            // Handle state change and recompute price
            handleCoffeeAddinCheckbox(CoffeeAddin.MILK, isChecked, milkQuantity);
            recomputeItemPrice();
        });

        // OnClick listener for caramelCheckbox
        caramelCheckbox.setOnClickListener(view -> {
            boolean isChecked = ((CheckBox)view).isChecked();
            if(isChecked){
                caramelQuantity.setEnabled(true);
            } else {
                caramelQuantity.setEnabled(false);
            }

            // Handle state change and recompute price
            handleCoffeeAddinCheckbox(CoffeeAddin.CARAMEL, isChecked, caramelQuantity);
            recomputeItemPrice();
        });

        // OnClick listener for whippedCreamCheckbox
        whippedCreamCheckbox.setOnClickListener(view -> {
            boolean isChecked = ((CheckBox)view).isChecked();
            if(isChecked) {
                whippedCreamQuantity.setEnabled(true);
            } else {
                whippedCreamQuantity.setEnabled(false);
            }

            // Handle state change and recompute price
            handleCoffeeAddinCheckbox(CoffeeAddin.WHIPPED_CREAM, isChecked, whippedCreamQuantity);
            recomputeItemPrice();
        });

        // Coffee quantity listener
        coffeeQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the quantity and update coffee
                Spinner spinner = (Spinner) parent;
                currentCoffee.updateQuantity((int)spinner.getSelectedItem());
                recomputeItemPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // We don't use this, but need to have it in order to compile
            }
        });

        // Coffee Size lisetener
        coffeeSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the size from the spinner recompute
                Spinner spinner = (Spinner) parent;
                currentCoffee.setSize((CoffeeSize) spinner.getSelectedItem());
                recomputeItemPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // We don't use this, but need to have it in order to compile
            }
        });

        // Attach handler for onClick for the Add coffee to order button
        addCoffeeToOrder.setOnClickListener(v -> {
            this.addCoffeeToOrder(v);
        });

        // Recompute item price
        recomputeItemPrice();
    }

    /**
     * Adds the current Coffee order to the current order. Called by the "Add to Order" button
     *
     * @param v the view that calls this handler
     */
    public void addCoffeeToOrder(View v) {
        // Add to order
        Order.getInstance().add(currentCoffee);

        // Show toast
        Toast.makeText(getBaseContext(), R.string.successfully_added_to_order, Toast.LENGTH_SHORT).show();

        // Navigate back
        Intent gotoMainActivity = new Intent(this, MainActivity.class);
        startActivity(gotoMainActivity);
    }

    /**
     * onItemSelected handler for all of the addin spinners.
     * @param parent AdapterView parent
     * @param view View that had it's item selected
     * @param position Position in the list that was selected
     * @param id id of the selected item
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Check if spinner and cast
        if(parent instanceof Spinner) {
            Spinner spinner = (Spinner) parent;
            // Branch depending on the addin
            // Note: Since android calls this function in the lifecycle when the checkbox's are not actually triggered, we have to check
            int selectedAmount = (Integer)spinner.getSelectedItem();
            CoffeeAddin addin = null;
            if(spinner.equals(this.creamQuantity)) {
                if(!this.creamCheckbox.isChecked()) return;
                addin = CoffeeAddin.CREAM;
            } else if(spinner.equals(this.syrupQuantity)) {
                if(!this.syrupCheckbox.isChecked()) return;
                addin = CoffeeAddin.SYRUP;
            } else if(spinner.equals(this.milkQuantity)) {
                if(!this.milkCheckbox.isChecked()) return;
                addin = CoffeeAddin.MILK;
            } else if(spinner.equals(this.caramelQuantity)) {
                if(!this.caramelCheckbox.isChecked()) return;
                addin = CoffeeAddin.CARAMEL;
            } else {
                if(!this.whippedCreamCheckbox.isChecked()) return;
                addin = CoffeeAddin.WHIPPED_CREAM;
            }

            // remove all of that particular add in and add back the number of that particular add in the user has selected
            while(currentCoffee.remove(addin));
            for(int i = 0; i < selectedAmount; i++) {
                currentCoffee.add(addin);
            }

            recomputeItemPrice();
        }
    }

    /**
     * onNothingSelected handler for all the addin spinners. We actually don't use this function but we must implement it in order to compile
     * @param parent parent view that called this function
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing, but we have to have this definition in order to compile
    }

    /**
     * Handle a coffee addin checkbox state change.
     *
     * @param addin The addin we are dealing with
     * @param isAdd If this param is true, we are adding the addin. If this param is false, then we are removing this addin.
     */
    private void handleCoffeeAddinCheckbox(CoffeeAddin addin, boolean isAdd, Spinner source) {
        if(isAdd) {
            // To ensure no data races, we remove all the addins and add back
            while(currentCoffee.remove(addin));
            for(int i = 0; i < (int)source.getSelectedItem(); i++) {
                currentCoffee.add(addin);
            }
        } else {
            // We need to remove all addins from the object that match the addin
            while(currentCoffee.remove(addin));

            // We also need to reset the corresponding spinner back to 1
            switch(addin) {
                case CREAM:
                    creamQuantity.setSelection(Constants.FIRST_SPINNER_ITEM);
                    break;
                case MILK:
                    milkQuantity.setSelection(Constants.FIRST_SPINNER_ITEM);
                    break;
                case WHIPPED_CREAM:
                    whippedCreamQuantity.setSelection(Constants.FIRST_SPINNER_ITEM);
                    break;
                case SYRUP:
                    syrupQuantity.setSelection(Constants.FIRST_SPINNER_ITEM);
                    break;
                case CARAMEL:
                    caramelQuantity.setSelection(Constants.FIRST_SPINNER_ITEM);
                    break;
            }
        }
    }

    /**
     * Recompute and render new item price
     */
    private void recomputeItemPrice() {
        double newPrice = 0;
        try {
            newPrice = currentCoffee.itemPrice() * currentCoffee.getQuantity();
        } catch (IllegalStateException e) {
            // We haven't selected size yet, so price is zero because we don't know what size we are dealing with yet
            newPrice = 0.0;
        } finally {
            coffeePrice.setText(String.format(Constants.CURRENCY_FORMAT_STRING, newPrice));
        }
    }

}