package com.whs.conbow.kkf;

import com.whs.conbow.kkf.Point;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.hamcrest.Description;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@Feature("慷慷分")
public class PointTest {
    Point point=new Point();

//    @Story("查看我的积分明细")
    @Test(description = "查看我的积分明细")
    public void getMyPointList(){
        Response response=point.myPointList(1,20,1);
        response.then().statusCode(200).body("data.content.size",greaterThanOrEqualTo(1));
        response.then().statusCode(200).body("data.content.size",equalTo(20));
        response.then().statusCode(200).body("data.content[0].name",equalTo("开宝箱"));

        response=point.myPointList(2,20,1);
        response.then().statusCode(200).body("data.content.size",equalTo(20));


    }
}
