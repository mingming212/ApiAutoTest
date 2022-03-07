package com.whs.utils;

import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetDataProperty {

    @Test
    public void test(){
        getproperdata("property/data.properties","host_xiupin");

    }

    public static String getproperdata(String prourl,String key) {
        Properties prop=new Properties();
        String value="";

        try {
            InputStream is = GetDataProperty.class.getClassLoader().getResourceAsStream(prourl);
            prop.load(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        value=prop.getProperty(key);
        return value;
    }

}

