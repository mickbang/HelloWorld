package com.client.hotupdatedemo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        hotFix();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @SuppressLint("DiscouragedPrivateApi")
    private void hotFix() {
        String filePath = getCacheDir() + "/hotfix.dex";
        System.out.println(filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            try (BufferedSource source = Okio.buffer(Okio.source(getAssets().open("hotfix.dex")));
                 BufferedSink sink = Okio.buffer(Okio.sink(new File(filePath)));) {
                sink.writeAll(source);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }


        try {
            ClassLoader originalClassLoader = getClassLoader();
            DexClassLoader classLoader = new DexClassLoader(file.getAbsolutePath(), getCacheDir().getAbsolutePath(), null, null);

            //获取BaseDexClassLoader
            Class<?> loaderClass = BaseDexClassLoader.class;
            //获取pathList属性
            Field pathListField = loaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);

            Object pathListObject = pathListField.get(classLoader);
            //获取DexPathList类
            Class<?> pathListClass = pathListObject.getClass();
            //获取dexElements属性
            Field elementsField = pathListClass.getDeclaredField("dexElements");
            elementsField.setAccessible(true);

            //新加载的elements
            Object dexElements = elementsField.get(pathListObject);

            Object originalPathlistobject = pathListField.get(originalClassLoader);
            //老的elements
            Object originalDexElements = elementsField.get(originalPathlistobject);

            int oldLength = Array.getLength(originalDexElements);
            int newLength = Array.getLength(dexElements);
            Object concatDexElements = Array.newInstance(dexElements.getClass().getComponentType(), oldLength + newLength);
            for (int i = 0; i < newLength; i++) {
                Array.set(concatDexElements, i, Array.get(dexElements, i));
            }

            for (int i = 0; i < oldLength; i++) {
                Array.set(concatDexElements, i + newLength, Array.get(originalDexElements, i));
            }
            elementsField.set(originalPathlistobject, concatDexElements);
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }


    }
}
