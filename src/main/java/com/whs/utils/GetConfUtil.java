package com.whs.utils;


import org.testng.annotations.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class GetConfUtil {

    public static String getUrlFromYaml(){
        Yaml yaml=new Yaml();
        InputStream is =  GetDataProperty.class.getClassLoader().getResourceAsStream("property/config.yml");
        HashMap<String, Object> map=yaml.load(is);
        String currentEnv=map.get("currentEnv").toString();
        String host="";
        HashMap<String, Object> mapEnv = new HashMap<>();

        switch (currentEnv){
            case "test":
                mapEnv = (HashMap<String, Object>) map.get("test");
                break;
            case "online":
                mapEnv = (HashMap<String, Object>) map.get("online");
                break;
        }
        host=mapEnv.get("host").toString();

        System.out.println("---------"+ host);
        return host;
    }


    @Test
    public void test(){
        getUrlFromYaml();
    }
}
