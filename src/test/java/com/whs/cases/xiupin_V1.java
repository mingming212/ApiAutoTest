package com.whs.cases;

import com.whs.utils.GetDataProperty;
import com.whs.utils.PostOrGetMethod;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 第一版，实现多接口调用，还没有case的概念
 * 关联接口：先获取我的秀拼列表，再访问列表中第一个秀拼详情，再获这个秀拼详情页上的评论
 */
@Test(groups = "NoRun")
public class xiupin_V1 {
    PostOrGetMethod request=new PostOrGetMethod();

    String propPath="property/data.properties";
    String host= GetDataProperty.getproperdata(propPath,"host_xiupin");
    String header="header_public";

    @Test
    public void testXiupin() throws Exception {
        int showGroupId=getXiupinList();
        getXiupinInfo(showGroupId);
        getXiupinComment(showGroupId);

    }

    public int getXiupinList() throws Exception {
        String url_xiupinList="/show-group-api/applet/showGroupUser/myShowGroup";

        String body_xiupinList="{\"pageNum\":1,\"pageSize\":10}";
        Response response = request.post(host, url_xiupinList, header, body_xiupinList, "200", "", "");
        int showGroupId=response.path("data.content[0].showGroupId");
        System.out.println("-----------------"+showGroupId);

        return showGroupId;
    }

    public void getXiupinInfo(int showGroupId) throws Exception {
        //todo get请求的param处理：需要将param放在get请求中发送，而不是放在URL里
        String url_xiupinInfo="/show-group-api/applet/showGroupUser/getShowGroupInfo?showGroupId="+showGroupId+"&communityId=20";

        String param_xiupinInfo="";
        Response response=request.get(host, url_xiupinInfo, header, "", "200", "", "");
        int return_showGroupId=response.path("data.showGroupId");
        Assert.assertEquals(return_showGroupId,showGroupId);
    }

    public void getXiupinComment(int showGroupId) throws Exception {
        String url_xiupinComment="/show-group-api/applet/showGroupUser/commentOnListInOneShowGroup";
        String body_xiupinComment="{\"showGroupId\":883,\"pageNum\":1,\"pageSize\":3}";

        Response response=request.post(host, url_xiupinComment, header, body_xiupinComment, "200", "", "");
        Assert.assertEquals(response.path("msg"),"success");
    }
}
