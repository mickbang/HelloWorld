package com.lq.helloworld;


import androidx.appcompat.app.AppCompatActivity;

import dagger.Module;

@Module
public class ViewModelMoudle {
    AppCompatActivity activity;

    public ViewModelMoudle(AppCompatActivity activity) {
        this.activity = activity;
    }

}
