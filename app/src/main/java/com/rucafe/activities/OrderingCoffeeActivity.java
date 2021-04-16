package com.rucafe.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.rucafe.R;
import com.rucafe.utils.Constants;

import java.lang.reflect.Array;

public class OrderingCoffeeActivity extends AppCompatActivity {

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
    private CheckBox whippedCreamCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_coffee);

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
        whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCreamCheckBox);

        // Populate spinners
        creamQuantity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.COFFEE_ADDIN_SPINNER_VALUES));
        syrupQuantity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.COFFEE_ADDIN_SPINNER_VALUES));
        milkQuantity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.COFFEE_ADDIN_SPINNER_VALUES));
        caramelQuantity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.COFFEE_ADDIN_SPINNER_VALUES));
        whippedCreamQuantity.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Constants.COFFEE_ADDIN_SPINNER_VALUES));

    }
}