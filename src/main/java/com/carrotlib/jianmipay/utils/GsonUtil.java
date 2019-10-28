package com.carrotlib.jianmipay.utils;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class GsonUtil {

    private static Gson gson = new Gson();

    private static JsonParser parser = new JsonParser();

    public static Gson getInstance() {
        return gson;
    }

    public static JsonParser getParser() {
        return parser;
    }
}
