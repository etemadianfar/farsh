package com.alireza.farsh;

import java.util.List;

public class singleton {

    public attr_carpet[] finall;
    public int count;

    private static singleton instance = null;

    protected singleton() {
        // Exists only to defeat instantiation.
    }

    public static singleton getInstance() {
        if (instance == null) {
            instance = new singleton();
        }
        return instance;
    }
}
