package com.whs.cases;

import com.whs.utils.GetDataProperty;
import com.whs.utils.PostOrGetMethod;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * 第二版：第一次实现case的概念，且增加了allure的标签
 *
 */
@Test(groups = "NoRun")
@Feature("秀拼")
public class xiupin_V2 {
    PostOrGetMethod request=new PostOrGetMethod();

    String propPath="property/data.properties";
    String host= GetDataProperty.getproperdata(propPath,"host_xiupin");
    String header="header_public";
    int showGroupId=0;




    @Story("获取我的秀拼列表")
    @Test(description = "成功返回秀拼列表")
    public void getXiupinList() throws Exception {
        String url_xiupinList="/show-group-api/applet/showGroupUser/myShowGroup";

        String body_xiupinList="{\"pageNum\":1,\"pageSize\":10}";
        Response response = request.post(host, url_xiupinList, header, body_xiupinList, "200", "", "");
        int showGroupId=response.path("data.content[0].showGroupId");
        System.out.println("-----------------"+showGroupId);

        this.showGroupId=showGroupId;
    }

    @Story("获取我的秀拼列表")
    @Test(description = "我的秀拼列表，翻页")
    public void getXiupinList_nextPage(){

    }

    @Story("获取我的秀拼列表")
    @Test(description = "我的秀拼列表，最后一页")
    public void getXiupinList_lastPage(){

    }

    @Story("获取我的秀拼列表")
    @Test(description = "我的秀拼列表，数量符合数据库记录")
    public void getXiupinList_db(){

    }


    @Story("我的秀拼详情")
    @Test(description = "获取我的第一个秀拼详情",dependsOnMethods = {"getXiupinList"})
    public void getXiupinInfo() throws Exception {
        //todo get请求的param处理：需要将param放在get请求中发送，而不是放在URL里
        String url_xiupinInfo="/show-group-api/applet/showGroupUser/getShowGroupInfo?showGroupId="+showGroupId+"&communityId=20";

        String param_xiupinInfo="";
        Response response=request.get(host, url_xiupinInfo, header, "", "200", "", "");
        int return_showGroupId=response.path("data.showGroupId");
        Assert.assertEquals(return_showGroupId,showGroupId);
    }


    /**
     * 关联接口：先获取我的秀拼列表，再访问列表中第一个秀拼详情，再获取这个秀拼详情页上的评论
     */
    @Story("评论")
    @Test(description = "获取第一个秀拼的评论",dependsOnMethods = {"getXiupinList","getXiupinInfo"})
    public void getXiupinComment() throws Exception {
        String url_xiupinComment="/show-group-api/applet/showGroupUser/commentOnListInOneShowGroup";
        String body_xiupinComment="{\"showGroupId\":"+showGroupId+",\"pageNum\":1,\"pageSize\":3}";

        Response response=request.post(host, url_xiupinComment, header, body_xiupinComment, "200", "", "");
        Assert.assertEquals(response.path("msg"),"success");
    }
}
