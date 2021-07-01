package com.baizhi.mysqlredis;

import com.baizhi.mysqlredis.dao.WjbDAO;
import com.baizhi.mysqlredis.entity.Food;
import com.baizhi.mysqlredis.service.WjbService;
import org.elasticsearch.ElasticsearchCorruptionException;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MysqlredisApplication.class)
public class MysqlredisApplicationTests {
//    @Autowired
//    private WjbDAO wjbDAO;
//    @Autowired
//    private WjbService wjbService;
//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
//    @Autowired
//    private RedisTemplate redisTemplate;
//    @Test
//    public void contextLoads() {
//        wjbDAO.delete(3);
//    }
    @Test
    public void contextLoads2() {
        HashMap<String,Integer> map = new HashMap<>();
        map.put("1",2);
        map.put("1",3);
        Set<String> set = map.keySet();
        for (String s : set) {
            System.out.println(map.get(s));

        }


//        System.out.println(redisTemplate.opsForValue().get("fod"));
//        System.out.println();
//        List<Food> foods = wjbService.queryEs("1");
//        for (Food food : foods) {
//            System.out.println(food);
//        }
//         NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//        .withIndices("foods")   //指定索引
//        .withTypes("fod")   //指定类型
//        .withQuery(QueryBuilders.matchAllQuery())
//        .build();
//
//    List<Food> products = elasticsearchTemplate.queryForList(nativeSearchQuery, Food.class);
//
//    for (Food product : products) {
//        System.out.println(product);
//    }


    }

}
