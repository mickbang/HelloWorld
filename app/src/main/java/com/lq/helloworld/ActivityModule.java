package com.lq.helloworld;

import com.lq.helloworld.data.IRestory;
import com.lq.helloworld.data.RestoryImpl;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ActivityModule {

    @Binds
    public abstract IRestory provideIRestory(RestoryImpl restory);
}
