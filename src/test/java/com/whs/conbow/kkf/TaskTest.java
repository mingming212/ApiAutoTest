package com.whs.conbow.kkf;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.withArgs;
import static org.hamcrest.Matchers.*;

@Feature("任务")
public class TaskTest {
    Task task=new Task();

    @Story("获取任务")
    @Test(description = "获取签到任务详情")
    public void testGetSignTask() {
        Response response=task.getSignTask();
        String name=response.path("data.taskInfoDto.name");
        Assert.assertTrue(name.contains("每日签到")|name.contains("明日签到"));
        response.then().statusCode(200);

    }

    @Story("获取任务")
    @Test(description = "获取每日任务列表")
    public void testGetTaskList(){
        Response response=task.getTaskList();
        response.then().statusCode(200).body("data.dailyTasks.name.size()",greaterThan(1));

    }

    @Story("业务流_任务逻辑流程")
    @Test(description = "已签到，则任务列表接口不展示签到任务")
    public void testProcess_TaskBasedOnSign(){
        //数据库中查找今天是否有每日签到得分
        //TODO 数据库连接查询
        String sql="";//createTime=今天，得分类型=每日签到
        int sign=1;//1代表今天已签到，0代表没有查找到今日签到记录

        //判断任务列表接口是否展示签到任务
        Response response_taskList=task.getTaskList();
        Response response_signTask=task.getSignTask();

        if(sign==1){//1代表今天已签到
            response_taskList.then().statusCode(200).body("data.dailyTasks.name",hasItem("明日签到"));
            response_signTask.then().statusCode(200).body("data.taskInfoDto.name",equalTo("明日签到"));

        }else if(sign==0){
            response_taskList.then().statusCode(200).body("data.dailyTasks.name",hasItem("每日签到"));
            response_signTask.then().statusCode(200).body("data.taskInfoDto.name",equalTo("每日签到"));
        }



    }


}