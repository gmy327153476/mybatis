package com.soft.io;

import java.io.InputStream;

public class Resources {

    public static InputStream getResourceAsStream(String path) {
        InputStream inputStream = Resources.class.getClassLoader().getResourceAsStream(path);
        return inputStream;
    }
}
