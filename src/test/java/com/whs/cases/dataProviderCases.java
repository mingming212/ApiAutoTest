package com.whs.cases;

import com.whs.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class dataProviderCases {
    public static void main(String[] args){

    }


    /*
    将excel中数据参数化
     */
    @DataProvider(name="cases")
    public Object[][] providerData() throws Exception {
        List<String[]> list= ReadExcel.readExcel();
        Object cases[][]= ChangeType.changeList(list);

        return cases;
    }

    //测试数据
    @Test (dataProvider = "cases")
    public void testcase001(String id,String des,String host,String url, String method, String header,String param, String assert_code,String assert_contain,String assert_body,String commons) throws Exception {
        PostOrGetMethod request=new PostOrGetMethod();
        if(method.equals("get")){
            request.get(host,url, header,param, assert_code,assert_contain,assert_body);
        }else if(method.equals("post")){
            request.post(host,url, header,param, assert_code,assert_contain,assert_body);
        }




    }
}
