package com.client.hotupdatedemo.hook;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.annotation.NonNull;

public class HookResources extends Resources {
    /**
     * Create a new Resources object on top of an existing set of assets in an
     * AssetManager.
     *
     *                selecting/computing resource values.
     *                selecting/computing resource values (optional).
     * @deprecated Resources should not be constructed by apps.
     * See {@link Context#createConfigurationContext(Configuration)}.
     */
    public HookResources(Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
    }

    @NonNull
    @Override
    public String getString(int id) throws NotFoundException {
        if (id == 123){
            return "hook string for id 123";
        }
        return super.getString(id);
    }


}
