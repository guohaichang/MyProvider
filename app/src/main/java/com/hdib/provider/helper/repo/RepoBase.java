package com.hdib.provider.helper.repo;

import com.hdib.provider.helper.ThreadSwitcher;
import com.hdib.provider.helper.callback.Callback;

public class RepoBase {

    private final ThreadSwitcher threadSwitcher;

    public RepoBase() {
        threadSwitcher = new ThreadSwitcher();
    }

    protected void childPost(Runnable runnable) {
        threadSwitcher.childHandler().post(runnable);
    }

    protected void mainPost(Runnable runnable) {
        threadSwitcher.mainHandler().post(runnable);
    }

    protected void commonDeal(final Callback callback, final int value) {
        if (callback == null) {
            return;
        }
        mainPost(new Runnable() {
            @Override
            public void run() {
                callback.callback(value);
            }
        });
    }

    public void quit() {
        if (threadSwitcher != null) {
            threadSwitcher.quit();
        }
    }
}
