package com.chanchuan.frame;

public class ServerAddressConfig {
    public static final int API_TYPE = 3;
    public static String BASE_URL = "";

    static {
        switch (API_TYPE) {
            case 1:
            case 2:
                BASE_URL = "";
                break;
            case 3:
                BASE_URL = "http://static.owspace.com/";
                break;

        }
    }
}
