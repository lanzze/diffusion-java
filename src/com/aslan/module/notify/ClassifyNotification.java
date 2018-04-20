package com.aslan.module.notify;

public interface ClassifyNotification extends Notification {

	void addNotifier(String name, Notifier notifier, boolean global);

    void removeNotifier(String name, Notifier notifier, boolean global);
}