package com.whs.conbow.kkf;

import com.whs.conbow.CommonHeader;
import com.whs.utils.GetConfUtil;
import io.restassured.response.Response;

import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

/**
 * 赚分任务相关
 */
public class Task extends CommonHeader {
    String host= GetConfUtil.getWechatUrlFromYaml();

    //获取签到任务
    public Response getSignTask(){
        useRelaxedHTTPSValidation();//信任https的任何证书

        String url=host+"/applet/task/getSignTask.json";
        return getRequestSpecification()
                .when()
                .post(url)
                .then()
                .log().all()
                .extract().response();
    }

    //获取任务列表
    public Response getTaskList(){
        useRelaxedHTTPSValidation();//信任https的任何证书

        String url=host+"/applet/task/getTaskList.json";
        return getRequestSpecification()
                .when()
                .post(url)
                .then()
                .log().all()
                .extract().response();
    }
}
