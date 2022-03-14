package com.whs.cases;

import com.whs.utils.*;
import java.util.List;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class dataProviderCases {


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
