package com.ekocbiyik.javafx.configuration;

import com.ekocbiyik.javafx.enums.ContextParam;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by enbiya on 11.07.2017.
 */
public class StbConfiguration {


    public Map<String, String> execute(String serialNumber) {

        Map<String, String> stbInfo = new HashMap<>();
        stbInfo.put(ContextParam.STB_SERIAL_NO, serialNumber);
        stbInfo.put(ContextParam.STB_BRAND, "IKON");
        stbInfo.put(ContextParam.STB_MODEL, "9000 IP");
        stbInfo.put(ContextParam.STB_MAC, "00020247E1D5");

        return stbInfo;
    }

}
