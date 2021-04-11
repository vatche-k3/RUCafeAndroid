package com.rucafe.ui.order_coffee;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OrderingCoffeeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OrderingCoffeeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Ordering Coffee fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}