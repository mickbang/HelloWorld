package com.client.annotationprocessingdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.client.lib_reflect_binding.BindView;
import com.client.lib_reflect_binding.Binding;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.textView)
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Binding.bind(this);
        textView.setText("ZhangSan");
    }
}