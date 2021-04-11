package com.rucafe.ui.current_order;

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

public class CurrentOrderFragment extends Fragment {

    private CurrentOrderViewModel currentOrderViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        currentOrderViewModel =
                new ViewModelProvider(this).get(CurrentOrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_current_order, container, false);
        final TextView textView = root.findViewById(R.id.text_current_order);
        currentOrderViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}