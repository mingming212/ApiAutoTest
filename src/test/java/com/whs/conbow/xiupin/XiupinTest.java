package com.whs.conbow.xiupin;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import static org.hamcrest.Matchers.equalTo;


/**
 * 第3版，拆分case和接口调用，接口调用挪到main下面，这里只包含case的实现部分
 */
@Feature("秀拼")
public class XiupinTest{
    Xiupin xiupn=new Xiupin();

    @Story("获取我的秀拼列表")
    @Test(description = "成功返回秀拼列表")
    public void getXiupinList() throws Exception {
        //将上面的请求全部写出来，再重新封装，不用原来的方法了
/*
        given()
                .contentType("application/json")
                .header("openid", "onhlt5WyKrkdCfPqFawxf2sCu7Hc")
                .header("sig", "5464f75d73b76712237521774187c2109fc66d34")
                .header("timestamp", "1643891071493")
        getRequestSpecification()
                .body(body_xiupinList)
        .when()
                .post("https://conbow-api.goodaa.com.cn/show-group-api/applet/showGroupUser/myShowGroup")
        .then()
                .body("data.size",equalTo(10))
                .log().all();
*/

        Response response=xiupn.xiupinList(1,10);
        response.then().statusCode(200).body("data.size",equalTo(10));
    }

    @Story("获取我的秀拼列表")
    @Test(description = "我的秀拼列表，翻页")
    public void getXiupinList_nextPage(){

    }

    @Story("获取我的秀拼列表")
    @Test(description = "我的秀拼列表，最后一页")
    public void getXiupinList_lastPage(){

    }

    @Story("获取我的秀拼列表")
    @Test(description = "我的秀拼列表，数量符合数据库记录")
    public void getXiupinList_db(){

    }


    @Story("我的秀拼详情")
    @Test(description = "获取我的第一个秀拼详情",dependsOnMethods = {"getXiupinList"})
    public void getXiupinInfo() throws MalformedURLException {
        int showGroupId=xiupn.xiupinList(1,10).path("data.content[0].showGroupId");
        Response response = xiupn.xiupinInfo(showGroupId);
        response.then().statusCode(200).body("data.showGroupId",equalTo(showGroupId));

    }


    @Story("评论")
    @Test(description = "获取第一个秀拼的评论",dependsOnMethods = {"getXiupinList","getXiupinInfo"})
    public void getXiupinComment() throws Exception {
        int showGroupId=xiupn.xiupinList(1,10).path("data.content[0].showGroupId");
        Response response=xiupn.xiupinComment(showGroupId);
        response.then().statusCode(200).body("msg",equalTo("success"));

    }
}
