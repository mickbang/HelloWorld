package com.client.hotupdatedemo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.client.hotupdatedemo.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        printWithOtherApk();
    }

    private void newPrint() {
        //通过new创建
//        Utils utils = new Utils("三张");
//        utils.shout();
    }

    //反射加载一起打包的类
    private void printWithReflect() {
        try {
            Class<?> cls = Class.forName("com.client.hotupdatedemo.utils.Utils");
            Method method = cls.getDeclaredMethod("shout");
            method.setAccessible(true);
            Constructor<?> constructor = cls.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            Object o = constructor.newInstance("张三");
//            Object o = cls.newInstance();
            method.invoke(o);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                 NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    //插件化原理
    private void printWithOtherApk() {
        String filePath = getCacheDir() + "/hotupdatedemoplug-release-unsigned.apk";
        System.out.println(filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            try (BufferedSource source = Okio.buffer(Okio.source(getAssets().open("hotupdatedemoplug-release-unsigned.apk")));
                 BufferedSink sink = Okio.buffer(Okio.sink(new File(filePath)));) {
                sink.writeAll(source);
            } catch (IOException e) {

            }
        }

        try {
            DexClassLoader dexClassLoader = new DexClassLoader(file.getAbsolutePath(), getCacheDir().getAbsolutePath(), null, null);
//            Class<?> cls = Class.forName("com.client.hotupdatedemo.utils.Utils");
            Class<?> cls = dexClassLoader.loadClass("com.client.hotupdatedemoplug.utils.Utils");
            Method method = cls.getDeclaredMethod("shout");
            method.setAccessible(true);
            Constructor<?> constructor = cls.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            Object o = constructor.newInstance("李四");
//            Object o = cls.newInstance();
            method.invoke(o);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                 NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    private void hotFix(){
        getClassLoader();

        DexClassLoader
    }
}
