package com.whs.conbow.xiupin;

import com.jayway.jsonpath.JsonPath;
import com.whs.conbow.CommonHeader;
import com.whs.utils.GetConfUtil;
import io.restassured.response.Response;

import java.net.MalformedURLException;

public class Xiupin extends CommonHeader {

    String host= GetConfUtil.getUrlFromYaml();
    //获取我的秀拼列表
    public Response xiupinList(int pageNum, int pageSize) throws MalformedURLException {
        String url=host+"/show-group-api/applet/showGroupUser/myShowGroup";
//        String body_xiupinList="{\"pageNum\":"+pageNum+",\"pageSize\":"+pageSize+"}";
        String body_xiupinList=JsonPath.parse(this.getClass().getClassLoader().getResourceAsStream("data/xiupin/myShowGroup.json"))
                .set("pageNum",pageNum)
                .set("pageSize",pageSize)
                .jsonString();

        return getRequestSpecification()
                .body(body_xiupinList)
        .when()
                .post(url)
        .then()
                .log().all()
                .extract().response();
    }

    //我的秀拼详情
    public Response xiupinInfo(int showGroupId) throws MalformedURLException {
        String url=host+"/show-group-api/applet/showGroupUser/getShowGroupInfo";
        return getRequestSpecification()
                .param("showGroupId",showGroupId)
                .param("communityId",20)
        .when()
                .get(url)
        .then()
//                .log().all()
                .extract().response();
    }

    //获取我的秀拼详情页上的评论
    public Response xiupinComment(int showGroupId){
        String url=host+"/show-group-api/applet/showGroupUser/commentOnListInOneShowGroup";
        String body_xiupinComment="{\"showGroupId\":"+showGroupId+",\"pageNum\":1,\"pageSize\":3}";
        return getRequestSpecification()
                .body(body_xiupinComment)
        .when()
                .post(url)
        .then()
                .log().all()
                .extract().response();
    }


}
