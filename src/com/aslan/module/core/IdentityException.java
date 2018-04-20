package com.aslan.module.core;

public class IdentityException extends RuntimeException {
    private String identity = null;
    private Object[] arguments;

    public IdentityException(String identity, Object... args) {
        this.identity = identity;
        this.arguments = args;
    }

    public String getIdentity() {
        return identity;
    }

    public Object[] getArguments() {
        return arguments;
    }
}