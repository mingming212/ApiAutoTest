package com.whs.conbow.xiupin;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

public class Xiupin extends DemoFather{

    //获取我的秀拼列表
    public Response xiupinList(int pageNum, int pageSize){
        //TODO：body换成map
        String body_xiupinList="{\"pageNum\":"+pageNum+",\"pageSize\":"+pageSize+"}";

        return getRequestSpecification()
                .body(body_xiupinList)
        .when()
                .post("https://conbow-api.goodaa.com.cn/show-group-api/applet/showGroupUser/myShowGroup")
        .then()
                .log().all()
                .extract().response();

    }

    //我的秀拼详情
    public Response xiupinInfo(int showGroupId){

        return getRequestSpecification()
                .param("showGroupId",showGroupId)
                .param("communityId",20)
        .when()
                .get("https://conbow-api.goodaa.com.cn/show-group-api/applet/showGroupUser/getShowGroupInfo")
        .then()
                .log().all()
                .extract().response();
    }

    //获取我的秀拼详情页上的评论
    public Response xiupinComment(int showGroupId){
        String body_xiupinComment="{\"showGroupId\":"+showGroupId+",\"pageNum\":1,\"pageSize\":3}";
        return getRequestSpecification()
                .body(body_xiupinComment)
        .when()
                .post("https://conbow-api.goodaa.com.cn/show-group-api/applet/showGroupUser/commentOnListInOneShowGroup")
        .then()
                .log().all()
                .extract().response();
    }


}
