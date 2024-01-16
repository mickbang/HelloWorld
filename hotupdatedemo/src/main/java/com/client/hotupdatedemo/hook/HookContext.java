package com.client.hotupdatedemo.hook;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;

public class HookContext extends ContextWrapper {
    HookResources hookResources = null;

    public HookContext(Context base) {
        super(base);
    }


    @Override
    public Resources getResources() {
        Resources originalResources = super.getResources();
        if (hookResources == null) {
            hookResources =
                    new HookResources(originalResources);
        }
        return hookResources;
    }
}
