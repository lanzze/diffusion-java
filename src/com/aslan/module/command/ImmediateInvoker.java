package com.aslan.module.command;

import com.aslan.config.Lookup;
import com.aslan.module.notify.NotifyEvent;

public class ImmediateInvoker implements Invoker {

    public void invoke(Command command) {
        try {
            command.execute();
        } catch (Exception e) {
            Lookup.notification().notify(new NotifyEvent("command.error", e));
        }
    }
}