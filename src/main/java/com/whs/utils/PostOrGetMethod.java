package com.whs.utils;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.useRelaxedHTTPSValidation;

public class PostOrGetMethod {
//    String prourllogin="property/data.properties";
//    String gettokenurl="./src/main/resources/property/gettoken.properties";
//    String host=GetDataProperty.getproperdata(prourllogin,"testhost");
//    String url=GetDataProperty.getproperdata(prourllogin,"apitakepicurl");

    WriteExcel writeExcel=new WriteExcel();
    Response response =null;
    RequestSpecification requestSpecification=header_public();


    public RequestSpecification header_public(){
        useRelaxedHTTPSValidation();//信任https的任何证书

        requestSpecification=
                given()
                    .contentType("application/json")
                    .header("openid", "onhlt5WyKrkdCfPqFawxf2sCu7Hc")
                    .header("sig", "5464f75d73b76712237521774187c2109fc66d34")
                    .header("timestamp", "1643891071493")
                    .log().all();
        return requestSpecification;

    }

    public void assertUtil(String assert_code,String assert_contain,String assert_body){
        if(!assert_code.equals("")){
            response.then().statusCode(Integer.parseInt(assert_code));
        }


        if(!assert_body.equals("")){
            String s[]=assert_body.split("=");
//            System.out.println("s[0]    "+s[0]);
//            System.out.println("s[1]    "+s[1]);

            //判断=后面的值，是否带双引号（即：是否是String类型）
            if(String.valueOf(s[1].charAt(0)).equals("\"")&String.valueOf(s[1].charAt(s[1].length()-1)).equals("\"")){
                System.out.println("带双引号");
                //将双引号去掉
                s[1]=s[1].substring(1,s[1].length()-1);
            }
            //todo actual、expected去空格


            Assert.assertEquals(response.path(s[0]).toString(),s[1]);

        }

    }



    public Response get(String host, String url,String header, String param, String assert_code,String assert_contain,String assert_body) throws Exception {
        header_special(header);
        //todo:param不应是传map格式，这里只是为了快速实现，套用了json2Map方法
        // todo: 并且param这里只实现了一个param的情况，多个就获取不到了
        if(!param.isEmpty()){
            Map<String, Object>  map = ChangeType.json2Map(param);
            requestSpecification.param(map.keySet().stream().collect(Collectors.toList()).get(0),map.get(0).toString());

        }

        response = requestSpecification
                        .get(host+url)
                .then()
                        .log().all()
                        .extract().response();

        //allure报告中，以文本类型附件的形式，展示请求和响应信息
        allureAtta("get", host, url, header, param, assert_code, assert_contain, assert_body);

        assertUtil(assert_code,assert_contain,assert_body);

        return response;
    }


    @Test
    public void test() throws Exception {

        String url_xiupinInfo="/show-group-api/applet/showGroupUser/getShowGroupInfo?showGroupId="+"0"+"&communityId=20";

        String param_xiupinInfo="";
        String header="header_public";
        String propPath="./src/main/resources/property/data.properties";
        String host= PropertyManage.getproperdata(propPath,"host_xiupin");

        Response response=get(host, url_xiupinInfo, header, "", "200", "", "");
        int return_showGroupId=response.path("data.showGroupId");
        Assert.assertEquals(return_showGroupId,883);

    }


    public Response post(String host, String url,String header, String param, String assert_code,String assert_contain,String assert_body) throws Exception {
        header_special(header);
//        Map<String ,Object> param=new HashMap<String,Object>();
//        param.put("groupId","00A7C0A25800465C8BC7");
//这里的param是否有必要改成body？目前认为：一个接口中，要么是param，要么是body，所以body也写成param应该没问题
        Map<String, Object>  map = ChangeType.json2Map(param);

        response = requestSpecification
                .body(map)
//                .param("groupId","00A7C0A25800465C8BC7")
                .post(host+url)
                .then()
                .log().all()
                .extract().response();

        //allure报告中，以文本类型附件的形式，展示请求和响应信息
        allureAtta("post", host, url, header, param, assert_code, assert_contain, assert_body);

        assertUtil(assert_code,assert_contain,assert_body);
        return response;


/*

        Response response2;
        response2 = given()
                // 设置request Content-Type
                .contentType("application/json;charset=utf-8")
                .body(param)
                .header("token",token)
                .post(host+url);

        long usingtime=response2.time();
      //  response2.print();

        Object code=response2.jsonPath().get("httpStatus");
        Object data=response2.jsonPath().get("data");
        System.out.println("code:"+code);
        System.out.println("data:"+data);
        String nowtime=GetNowTime.getTime();
        String testresult=TestAssert.assertResult(response2,assertdata);

        String result[]=new String[8];
        result[0]=id;
        result[1]=picurl;
        result[2]=assertdata;
        result[3]=String.valueOf(code);
        result[4]=String.valueOf(data);
        result[5]=String.valueOf(usingtime);
        result[6]=nowtime;
        result[7]=testresult;

        writeExcel.write(result);
*/


    }

    /**
     * 定制化header
     * @param header
     */
    public void header_special(String header){
        if(!header.equals("header_public")){//若Excel中的case里，header值!=header_public,代表此case的header定制化，此请求需要追加header
            if(!header.isEmpty()){
                //todo 这里只考虑了传一个header的情况，还需要考虑一次传入多个header的情况，多个header可以用逗号分割
                String s[]=header.split(":");

                //TODO header.split转到arrylist比较合适,另外需要考虑：传入的header中，冒号后面是空（且不是null，也不是""）的情况下，怎么设置header
                List<String> list=new ArrayList<>();
                list= Arrays.asList(s);

//                System.out.println("s[0]    "+s[0]);
//                System.out.println("s[1]    "+s[1]);
                header_public().header(s[0], s[1]);
            }
        }
    }


    /**
     * allure报告中，以文本类型附件的形式，展示请求和响应信息
     */
    public void allureAtta(String method, String host, String url,String header, String param, String assert_code,String assert_contain,String assert_body){
        String req="<host>:   "+host+"\n";
        req=req+"<url>:   "+url+"\n";
        req=req+"<method>:   "+method+"\n";
        req=req+"<Headers>:   "+header+"\n";
        req=req+"<param>:   "+param+"\n";
        req=req+"<assert_code>:   "+assert_code+"\n";
        req=req+"<assert_contain>:   "+assert_contain+"\n";
        req=req+"<assert_body>:   "+assert_body+"\n";
        Allure.addAttachment("日志-请求",req);
//        Allure.addAttachment("日志-相应",response.print());
        Allure.addAttachment("日志-响应",response.prettyPrint());
//        System.out.println("--------------请求----------"+req);


    }


}
