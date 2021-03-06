package com.lei.obtain.utils;

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

import java.util.List;

/**
 * @Author LEI
 * @Date 2021/4/8 14:00
 * @Description TODO
 */
public class RecommenderUtils {

    public static int[] recommendByUser(long userId) throws ClassNotFoundException, TasteException {
        // 根据用户协同过滤，根据用户的相似度，推荐相应的item
        Class.forName("com.mysql.cj.jdbc.Driver");
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("root");
        dataSource.setPassword("sql2008");
        dataSource.setDatabaseName("knowledge-dev");
        dataSource.setUrl("jdbc:mysql://localhost:3306/knowledge-dev?characterEncoding=utf-8&serverTimezone=Asia/Shanghai");
        //获取模型
        JDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource,"user_score","user_id","tinymce_id","score","time");
        DataModel model = dataModel;
        //计算相似度
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        //计算阈值,选择邻近的2个用户
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(2 ,similarity,model);
        //推荐集合
        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
        //推荐数量 为n的一个合集,这里数量可以修改
        List<RecommendedItem> recommendedItems = recommender.recommend(userId,10);
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
        return kl_idArray;
    }
}
