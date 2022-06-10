package com.example.appgastos.ui.PaginaPrincipal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PaginaPrincipalViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public PaginaPrincipalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
