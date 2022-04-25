package com.whs.utils;

import org.testng.annotations.Test;

import java.io.*;
import java.util.Properties;

public class PropertyManage {

    @Test
    public void test2(){
        setProperty("./src/main/resources/property/gettoken.properties","token","122");
        String s=getproperdata("./src/main/resources/property/gettoken.properties","token");
        System.out.println(s+"--------------");

    }

    public static String getproperdata(String prourl,String key) {
        Properties prop=new Properties();
        String value="";
        try {
//            InputStream is = PropertyManage.class.getClassLoader().getResourceAsStream(prourl);
            //备注：之所以没用上面getClassLoader这种方法,是因为这个方法在编译时会先把resource文件复制到class路径下，再运行，那如果程序中对resource文件内容有修改，那么用这种方法获取的值，是老值（因为取得是class路径下的资源文件）
            //故：如果程序中对资源文件有修改的话，不能用getClassLoader这种方法，如果是手动对资源文件修改（非程序修改），则可以使用getClassLoader这种方法
            FileInputStream is=new FileInputStream(prourl);
            prop.load(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        value=prop.getProperty(key);
        return value;
    }

    public static void setProperty(String prourl,String key,String value)  {
        Properties prop=new Properties();
        try {
            FileOutputStream os=new FileOutputStream(prourl);
            prop.setProperty(key,value);
            prop.store(os,null);
            os.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

