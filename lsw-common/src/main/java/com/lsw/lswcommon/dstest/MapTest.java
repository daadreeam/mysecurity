package com.lsw.lswcommon.dstest;

import java.util.HashMap;

public class MapTest {
    public static void main(String[] args) {
        HashMap<String, Object> hashMap = new HashMap<>(13);
        hashMap.put("key","v");
        Object key = hashMap.get("key");
    }
}
