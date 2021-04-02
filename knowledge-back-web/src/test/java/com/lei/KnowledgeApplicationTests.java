package com.lei;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lei.admin.entity.Tinymce;
import com.lei.admin.mapper.TinymceMapper;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author LEI
 * @Date 2021/3/16 11:03
 * @Description TODO
 */
@SpringBootTest
public class KnowledgeApplicationTests {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private TinymceMapper tinymceMapper;

    @Test
    void testAddDocument() throws IOException {
        QueryWrapper<Tinymce> wrapper = new QueryWrapper<>();
        List<Tinymce> tinymceList = tinymceMapper.selectList(wrapper);
        for (Tinymce tinymce : tinymceList) {
            IndexRequest request = new IndexRequest("testbbb");
            request.id(String.valueOf(tinymce.getId()));
            request.source(JSON.toJSONString(tinymce), XContentType.JSON);
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        }
    }

    @Test
    void getIkAnalyzeSearchTerms() {
        String searchContent = "Java基础（3）";
        String ikAnalyzer = "ik_max_word";
        // 调用 IK 分词分词
        AnalyzeRequestBuilder ikRequest = new AnalyzeRequestBuilder((ElasticsearchClient) restHighLevelClient,
                AnalyzeAction.INSTANCE, "testaaa", searchContent);

        ikRequest.setTokenizer(ikAnalyzer);
        List<AnalyzeAction.AnalyzeToken> tokenList = ikRequest.execute().actionGet().getTokens();

        // 循环赋值
        List<String> searchTermList = new ArrayList<>();
        tokenList.forEach(ikToken -> {
            searchTermList.add(ikToken.getTerm());
        });
        System.out.println(searchTermList.toString());

    }
}
