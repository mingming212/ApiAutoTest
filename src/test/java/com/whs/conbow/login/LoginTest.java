package com.whs.conbow.login;

import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class LoginTest {
    Login login=new Login();
    Response response;
    String token="";

    //BeforeSuite里不要有断言，否则万一断言失败，则后面所有的case都不执行了
    /**
     * 所有case执行之前执行一次：登录接口
     */
    @BeforeTest(alwaysRun = true)
    public void getToken() {
        try {
            response = login.login();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("BeforeTest执行失败，获取登录token失败");
        }
    }

    @Story("登录")
    @Test(description = "返回token")
    public void testLogin(){
        token = response.path("data.token");
        response.then().statusCode(200).body("data.token",notNullValue());
    }
}
