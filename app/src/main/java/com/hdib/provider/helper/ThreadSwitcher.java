/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hdib.provider.helper;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * Global executor pools for the whole application.
 * <p>
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
public class ThreadSwitcher {
    private Handler mainHandler;
    private Handler childHandler;
    private final HandlerThread handlerThread;

    public ThreadSwitcher() {
        handlerThread = new HandlerThread("childThread");
        handlerThread.start();
        this.childHandler = new Handler(handlerThread.getLooper());
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public Handler mainHandler() {
        return mainHandler;
    }

    public Handler childHandler() {
        return childHandler;
    }

    public void quit() {
        if (handlerThread != null && handlerThread.isAlive()) {
            handlerThread.quitSafely();
        }
    }
}
