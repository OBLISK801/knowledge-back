package com.lei;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lei.admin.entity.Tinymce;
import com.lei.admin.mapper.TinymceMapper;
import com.lei.obtain.utils.PdfUtils;
import com.lei.obtain.vo.PdfVO;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
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
        wrapper.eq("is_article",1).eq("is_public",1);
        List<Tinymce> tinymceList = tinymceMapper.selectList(wrapper);
        for (Tinymce tinymce : tinymceList) {
            IndexRequest request = new IndexRequest("testone");
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

    @Test
    public void recommendByUser() throws ClassNotFoundException, TasteException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("root");
        dataSource.setPassword("sql2008");
        dataSource.setDatabaseName("knowledge-dev");
        dataSource.setUrl("jdbc:mysql://localhost:3306/knowledge-dev?characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        JDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource,"user_score","user_id","tinymce_id","score","time");
        DataModel model = dataModel;
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(2 ,similarity,model);
        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        List<RecommendedItem> recommendedItems = recommender.recommend(206,5);
        int[] kl_idArray = new int[recommendedItems.size()];
        for (int i=0;i<recommendedItems.size();i++){
            kl_idArray[i] = (int) recommendedItems.get(i).getItemID();
        }
        //下面是测试用的代码
        for (RecommendedItem recommendation : recommendedItems) {
            System.out.println(recommendation);
        }
        System.out.println("-------------");

        for (int j : kl_idArray) {
            System.out.println(j);
        }
    }

    @Test
    public void testPdfBox() throws IOException {
        String path = "D:\\GraduationProject\\StageOne\\knowledge-back\\upload\\preview";
        File file = new File(path);
        String[] fileLists = file.list();
        assert fileLists != null;
        List<PdfVO> pdfVOS = new ArrayList<>();
        for (String fileList : fileLists) {
            pdfVOS.add(PdfUtils.READPDF(path+File.separator+fileList));
        }
        for (int i = 0; i < pdfVOS.size(); i++) {
            IndexRequest request = new IndexRequest("testtwo");
            request.id(String.valueOf(i));
            request.source(JSON.toJSONString(pdfVOS.get(i)), XContentType.JSON);
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        }
    }




}
