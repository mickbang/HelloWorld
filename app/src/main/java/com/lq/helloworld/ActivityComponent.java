package com.lq.helloworld;

import android.app.Activity;

import dagger.Component;

@ActivityScope
@Component(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SecondActivity secondActivity);
}
