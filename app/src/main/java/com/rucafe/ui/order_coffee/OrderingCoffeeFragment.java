package com.rucafe.ui.order_coffee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.rucafe.R;

public class OrderingCoffeeFragment extends Fragment {

    private OrderingCoffeeViewModel orderingCoffeeViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderingCoffeeViewModel =
                new ViewModelProvider(this).get(OrderingCoffeeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_order_coffee, container, false);
        final TextView textView = root.findViewById(R.id.text_order_coffee);
        orderingCoffeeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}