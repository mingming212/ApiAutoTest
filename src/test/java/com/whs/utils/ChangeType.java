package com.whs.utils;

//import com.google.common.reflect.TypeToken;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.ss.formula.functions.T;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChangeType {

    //String类型的json，转换成Map
    public static Map<String, Object>  json2Map(String json){
        //todo 使用HashMap时，第5个case报错，用Map不报错，为啥
        Map<String, Object>  map = (Map) JSONObject.parse(json);
        return map;
    }

    //List<String[]>转换成数组
    public static Object[][] changeList(List<String[]> param) throws Exception {


        //  List<String> liststr=rw.readCsv();
        Object listobject[][] = new Object[param.size()][];
        for (int i = 0; i < param.size(); i++) {

            Object str1[] = param.get(i);

            listobject[i] = str1;

        }


        return listobject;


    }

    //

    /*
    String类型转行成map类型
     */

    public static Map<String, Object> StrToMap(String str1) {

        Map<String, Object> strtomap = null;

        Gson gson = new Gson();
        strtomap = gson.fromJson(str1, new TypeToken<Map<String, Object>>() {
        }.getType());


        //  System.out.println(strtomap);


        return strtomap;


    }



     /*
    String转换成json，且有多级套用,使用于json中包含数组类型
     */

    public static Map StringToJsonToMap(String str1) {

        JSONObject jasonObject = JSONObject.parseObject(str1);
        Map data = (Map) jasonObject;
        return data;

    }

    //Object转换成list,用户获取data中的结果
    public static List<String> ObjectToList(Object obj) {
        List<String> result = new ArrayList<String>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(String.class.cast(o));
            }

        }

        return result;

    }
    //精准的除法
    public  static  String Chufa(int a,int b){

        DecimalFormat df=new DecimalFormat("0.000000");
        return df.format((float)a/b);

    }
}