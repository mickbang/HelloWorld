package com.lq.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.lq.helloworld.data.IRestory;

import javax.inject.Inject;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";


    @Inject
    IRestory restory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerActivityComponent.builder().build().inject(this);
        setContentView(R.layout.activity_second);


        Log.d(TAG, "onCreate: " + restory.getDogs());
    }
}