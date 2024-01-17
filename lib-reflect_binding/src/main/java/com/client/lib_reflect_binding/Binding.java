package com.client.lib_reflect_binding;

import android.app.Activity;

import java.lang.reflect.Field;

public class Binding {
    public static void bind(Activity activity) {
        Class<?> activityClass = activity.getClass();
        for (Field declaredField : activityClass.getDeclaredFields()) {
            declaredField.setAccessible(true);
            BindView bindView = declaredField.getAnnotation(BindView.class);
            if (bindView != null) {
                try {
                    declaredField.set(activity, activity.findViewById(bindView.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
