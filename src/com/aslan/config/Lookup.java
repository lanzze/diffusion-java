package com.aslan.config;

import com.aslan.module.command.ImmediateInvoker;
import com.aslan.module.command.Invoker;
import com.aslan.module.notify.HashClassifyNotification;
import com.aslan.module.notify.Notification;
import com.aslan.module.random.SeedFactory;
import com.aslan.module.random.SourceSeedFactory;

public class Lookup {

    static Invoker _invoker = null;

    public static Invoker invoker() {
        if (Lookup._invoker == null) {
            Lookup._invoker = new ImmediateInvoker();
        }
        return Lookup._invoker;
    }

    static Notification _notification = null;

    public static Notification notification() {
        if (Lookup._notification == null) {

            Lookup._notification = new HashClassifyNotification();
        }
        return Lookup._notification;
    }

    static SeedFactory _seedSourceFactory = null;

    public static SeedFactory seedFactory() {
        if (Lookup._seedSourceFactory == null) {
            Lookup._seedSourceFactory = new SourceSeedFactory();
        }
        return Lookup._seedSourceFactory;
    }

}


