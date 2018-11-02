package com.aslan.module.notify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashClassifyNotification implements ClassifyNotification {
    Map<String, List<Notifier>> statics = new HashMap();
    Map<String, List<Notifier>> content = new HashMap<>();

    public void addNotifier(String name, Notifier notifier, boolean global) {
        Map<String, List<Notifier>> map = global ? statics : this.content;
        List<Notifier> list = map.computeIfAbsent(name, (key) -> new ArrayList<>());
        list.add(notifier);
    }

    public void removeNotifier(String name, Notifier notifier, boolean global) {
        Map<String, List<Notifier>> map = global ? statics : this.content;
        List<Notifier> list = map.get(name);
        if (list != null) {
            list.remove(notifier);
        }
    }

    public void notify(NotifyEvent event) {
        doNotify(event, statics.get(event.getName()));
        if (!event.isStopPropagation()) {
            doNotify(event, this.content.get(event.getName()));
        }
    }

    protected void doNotify(NotifyEvent event, List<Notifier> list) {
        int size = list == null ? 0 : list.size();
        for (int i = 0; i < size; i++) {
            try {
                list.get(i).action(event);
                if (event.isStopImmediatePropagation()) break;
            } catch (Exception e) {

            }
        }
    }
}