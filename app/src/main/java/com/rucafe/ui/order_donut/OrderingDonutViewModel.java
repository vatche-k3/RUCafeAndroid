package com.rucafe.ui.order_donut;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OrderingDonutViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OrderingDonutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Ordering Donut changed fragment!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}