package com.my.networks.lab1;

import java.util.Random;

public class SocketInfo {
    public static final int DEFAULT_PORT_VALUE = 8888;
    public static final String DEFAULT_HOST_VALUE = "localhost";

    public static float generateRandomFloat() {
        return (float)Math.random() * 1000;
    }

    public static char generateRandomChar() {
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        return c;
    }
}
