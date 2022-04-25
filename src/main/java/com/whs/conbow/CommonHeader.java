package com.whs.conbow;

import com.whs.conbow.login.Login;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.whs.utils.PropertyManage.getproperdata;
import static io.restassured.RestAssured.given;

public class CommonHeader {

    public RequestSpecification getRequestSpecification(){
        String token=getToken();

        return given()
                .contentType("application/json")
                .header("openid", "onhlt5WyKrkdCfPqFawxf2sCu7Hc")
                .header("sig", "5464f75d73b76712237521774187c2109fc66d34")
                .header("timestamp", "1643891071493")
                .header("token",token)//实际没用到这个参数，仅演示beforeTest的用法
                .log().all();

    }

    public String getToken(){
        String token=getproperdata("./src/main/resources/property/gettoken.properties","token");
        if(token!=null) {
            return token;
        }else
            return "";
    }
}
