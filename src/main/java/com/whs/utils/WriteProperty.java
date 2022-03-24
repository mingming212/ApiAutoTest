package com.whs.utils;

import java.io.*;
import java.util.Properties;

public class WriteProperty {

    public static void writeProperty(Object value) throws IOException {


        Properties pro = new Properties();
        String writepriperty = "property/gettoken.properties";



        StringBuffer buf=new StringBuffer();
        buf.append(value);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writepriperty), "UTF-8"));
        bw.write("token="+buf.toString());
        bw.close();


/*
        FileOutputStream out = new FileOutputStream(writepriperty);
        pro.setProperty("token", String.valueOf(value));
        pro.store(out,"写入token");
        out.flush();
        out.close();

        */

    }


}
