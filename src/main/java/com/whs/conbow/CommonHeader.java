package com.whs.conbow;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CommonHeader {

    public RequestSpecification getRequestSpecification(){
        return given()
                .contentType("application/json")
                .header("openid", "onhlt5WyKrkdCfPqFawxf2sCu7Hc")
                .header("sig", "5464f75d73b76712237521774187c2109fc66d34")
                .header("timestamp", "1643891071493")
                .log().all();

    }
}
