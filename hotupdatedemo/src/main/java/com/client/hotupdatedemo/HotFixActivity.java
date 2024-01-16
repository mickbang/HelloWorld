package com.client.hotupdatedemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class HotFixActivity extends AppCompatActivity {
    TextView tvTitle;
    Button btnShowTitle;
    Button btnHotFix;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_fix);
        tvTitle = findViewById(R.id.tvTitle);
        btnShowTitle = findViewById(R.id.btnShowTitle);
        btnHotFix = findViewById(R.id.btnHotFix);

        btnShowTitle.setOnClickListener(v -> {
            Title title = new Title();
            tvTitle.setText(title.showTitle());
        });

        btnHotFix.setOnClickListener(v -> {
//            hotFix();
        });

    }


    private void hotFix() {
        String filePath = getCacheDir() + "/hotfix.apk";
        System.out.println(filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            try (BufferedSource source = Okio.buffer(Okio.source(getAssets().open("hotfix.apk")));
                 BufferedSink sink = Okio.buffer(Okio.sink(new File(filePath)));) {
                sink.writeAll(source);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }


        try {
            DexClassLoader dexClassLoader = new DexClassLoader(file.getAbsolutePath(), getCacheDir().getAbsolutePath(), null, null);
            Class<?> cls = BaseDexClassLoader.class;
            Field field = cls.getDeclaredField("pathList");
            field.setAccessible(true);
            Object dexLoaderDexList = field.get(dexClassLoader);

            Class<?> dexList = dexLoaderDexList.getClass();
            Field dexElementsField = dexList.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);
            //这里获取到新classloader的dexElements
            Object dexElements = dexElementsField.get(dexLoaderDexList);

            Object sysDexLoaderDexList = field.get(getClassLoader());


            dexElementsField.set(sysDexLoaderDexList, dexElements);
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }


    }


}