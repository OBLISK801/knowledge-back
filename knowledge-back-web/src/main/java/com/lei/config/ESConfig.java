package com.lei.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author LEI
 * @Date 2021/3/15 23:42
 * @Description TODO
 */
@Configuration
public class ESConfig {
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
    }

}

