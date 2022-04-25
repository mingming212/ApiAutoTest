package com.whs.conbow.kkf;

import com.jayway.jsonpath.JsonPath;
import com.whs.conbow.CommonHeader;
import com.whs.utils.GetConfUtil;
import io.restassured.response.Response;

import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

/**
 * 用户慷慷分相关
 */
public class Point extends CommonHeader {
    String host= GetConfUtil.getUrlFromYaml();

    /**
     * @param type 收入接口"type": 1，是int型；支出接口"type": "2",type是字符串
     * @param pageSize
     * @param pageNum
     * @return
     */
    public Response myPointList(int type, int pageSize, int pageNum){
        useRelaxedHTTPSValidation();//信任https的任何证书
        String url=host+"/omp-api/applet/point/queryListPage";
        String body=
                JsonPath.parse(this.getClass().getClassLoader().getResourceAsStream("data/point/queryListPage.json"))
                .set("type",type)
                .set("pageSize",pageSize)
                .set("pageNum",pageNum)
                .jsonString();
        if(type==2){
            body=JsonPath.parse(Point.class.getClassLoader().getResourceAsStream("data/point/queryListPage.json"))
                .set("type",Integer.toString(type))//传参的时候，收入接口"type": 1，是int型；支出接口"type": "2",type是字符串
                .set("pageSize",pageSize)
                .set("pageNum",pageNum)
                .jsonString();
        }
        return getRequestSpecification()
                .body(body)
                .when()
                .post(url)
                .then()
                .log().all()
                .extract().response();

    }
}
