package com.lsh.mall.search;

import com.alibaba.fastjson.JSON;
import com.lsh.mall.search.config.MallEsConfig;
import lombok.Data;
import org.apache.catalina.User;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallSearchApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    public void testClient(){
        System.out.println(client);
    }

    @Test
    public void indexData() throws Exception{
        IndexRequest indexRequest = new IndexRequest("users");
        indexRequest.id("1");

        User user = new User();
        user.setAge(1);
        user.setUserName("张三");
        user.setGender("男");

        String string = JSON.toJSONString(user);
        indexRequest.source(string, XContentType.JSON);
        IndexResponse index = client.index(indexRequest, MallEsConfig.COMMON_OPTIONS);
        System.out.println(index);
    }


    @Data
    class User{
        private String userName;
        private String gender;
        private Integer age;

    }

}
