package com.aslan.module.utils;

import com.aslan.module.core.DiffusionException;

import java.io.InputStream;

public class Files {

    public static String getContent(String file) {
        try {
            InputStream stream = Class.class.getResourceAsStream(file);
            byte[] buf = new byte[stream.available()];
            stream.read(buf);
            return new String(buf);
        } catch (Exception e) {
            throw new DiffusionException("none");
        }
    }
}
