package com.whs.utils;

import io.restassured.response.Response;

public class TestAssert {

public static String assertResult(Response response, String assertdata){

    String assertresult="";

    Object code=response.jsonPath().get("httpStatus");
    Object data=response.jsonPath().get("data");

   // Object acc=response.path("data.acc");
    Object acc=response.jsonPath().get("data.acc");
    System.out.println("acc:"+acc);
    Object name=response.jsonPath().get("data.name");
    System.out.println("name:"+name);

    String codedata=String.valueOf(code);
    String datastr=String.valueOf(data);

    if(assertdata!=null){

        if(codedata.contains(assertdata)){


            assertresult="PASS";

        }
        if(datastr.contains(assertdata)){


            assertresult="PASS";

        }
        else {

            assertresult="FAIL";
        }


    }





    return  assertresult;


}
}
