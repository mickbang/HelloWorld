package com.lq.core_networklistener;

import android.view.View;

public interface InternetConnectionListener {
    void onConnected(int source);
    View onDisconnected();
}