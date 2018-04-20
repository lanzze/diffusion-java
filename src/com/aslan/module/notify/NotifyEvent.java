package com.aslan.module.notify;

public class NotifyEvent {
    private String name;
    private boolean stopPropagation;
    private boolean stopImmediatePropagation;
    private Object attachment;

    public NotifyEvent(String name) {
        this.name = name;
    }

    public NotifyEvent(String name, Object attachment) {
        this.name = name;
        this.attachment = attachment;
    }

    public String getName() {
        return name;
    }

    public void stopPropagation() {
        this.stopPropagation = true;
    }

    public void stopImmediatePropagation() {
        this.stopImmediatePropagation = true;
    }

    public boolean isStopPropagation() {
        return stopPropagation;
    }

    public boolean isStopImmediatePropagation() {
        return stopImmediatePropagation;
    }

}
