package com.lsh.mall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Liu Shaohua
 * Create at 2021/3/11
 */
@Configuration
public class MallEsConfig {

    public static final RequestOptions COMMON_OPTIONS;
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient esRestClient(@Value("${spring.elasticsearch.rest.uris}") List<String> uris){
        //TODO 修改为线上的地址
        RestClientBuilder builder = RestClient.builder(HttpHost.create(uris.get(0)));
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }
}
