package com.whs.conbow.login;

import com.whs.conbow.CommonHeader;
import com.whs.utils.PropertyManage;
import io.restassured.response.Response;

/**
 * login()方法已调通，可以执行以下场景，所以把这个方法故意写错了（url换成错的），以后再调试用到的时候再改回来
 *
 * 非真实业务场景，演示：
 * 1.登录接口，获取token，存放在config文件中。
 * 2.若登录失败，直接使用config文件中上次调用成功的值
 * 3.登录接口需要在所有case执行之前执行一次
 * 备注：由于小程序上的case不需要token（使用header里的sig、时间戳、openID校验身份），所以这里只是一个演示
 */
public class Login extends CommonHeader {
    /**
     * 这个方法故意写的执行失败
     */
    public Response login() {
        //调login接口，获取返回结果中的token，存到config，使header能获取最新的token，使后面的case用新的header
//        String url="https://api.iconbow.com/user/app/login";//这个是正确的URL
        String url="https://baidu.com";//这个是故意写错的URL

        String body="{\"jpushToken\":\"1634174145230\",\"authCode\":\"8911\",\"mobile\":\"18001367612\"}";
        Response response=
                getRequestSpecification()
                        .header("token","")
                        .header("app-id","6ff90214d53b15a829af")
                        .header("imei","7763BA4575E74AC995C9519C9E3F0075")
                        .body(body)
                .when()
                        .post(url)
                .then()
                        .log().all()
                .extract().response();
        String token = response.path("data.token");
        System.out.println("-----------------" + token);

        //token存到config，使以后用新的header
        if(token!=null) {
            PropertyManage.setProperty("./src/main/resources/property/gettoken.properties", "token", token);
        }
        return response;
    }

}
