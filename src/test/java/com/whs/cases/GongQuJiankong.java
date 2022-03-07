package com.whs.cases;

import com.jayway.jsonpath.JsonPath;
import com.whs.utils.GetDataProperty;
import com.whs.utils.PostOrGetMethod;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Test(groups = "NoRun")
public class GongQuJiankong {
    PostOrGetMethod request=new PostOrGetMethod();

    String propPath = "property/data.properties";
    String host = GetDataProperty.getproperdata(propPath, "host");
    String header="header_public";
    String cameraIndexCode="";

    /**
     * 关联接口：先获取公区监控列表，再访问监控列表里的第一个监控详情
     */
    @Test
    public void guanlianTest() throws Exception {
        String url_cameraList="/hik/app/camera/getList";
        String url_cameraDetail="/hik/app/camera/getCameraPreviewUrl?cameraIndexCode=109xxw21";
        //先执行接口：获取公区监控列表
        Response response=request.get(host,url_cameraList, header,"", "200","","");
        List<Object> list=response.path("data");
//        System.out.println("-----"+list.toString());
//        System.out.println("-----"+list.size());
        Assert.assertNotEquals(list.size(),0);

        if(list.size()>0){//监控列表中返回的监控数量>0
            cameraIndexCode=response.path("data[0].cameraIndexCode").toString();
            System.out.println("------"+cameraIndexCode);
        }

        //再执行接口：公区监控详情
        String param="{\"cameraIndexCode\":\"109xxw21\"";
        Response response2=request.get(host,url_cameraDetail, header,param, "200","","");
        Assert.assertEquals(response2.path("msg"),"success");


    }
}
