package com.lq.core_networklistener;

import android.content.Context;

public interface Ping_navigator {
    void timeout(Context context);
    void replied(Context context);
    void ended(Context context);
}
