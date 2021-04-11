package com.rucafe.ui.current_order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CurrentOrderViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CurrentOrderViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is a Current Order fragment!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}