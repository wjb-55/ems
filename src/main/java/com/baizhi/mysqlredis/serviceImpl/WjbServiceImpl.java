package com.baizhi.mysqlredis.serviceImpl;


import com.baizhi.mysqlredis.dao.WjbDAO;
import com.baizhi.mysqlredis.entity.Food;
import com.baizhi.mysqlredis.service.WjbService;
import com.baizhi.mysqlredis.zhujie.AddRedis;
import com.baizhi.mysqlredis.zhujie.DeleRedis;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service(value = "wjbServiceImpl")
public class WjbServiceImpl implements WjbService {
    @Resource
    private WjbDAO wjbDAO;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @AddRedis
    @Override
    public List<Food> queryAll() {
        return wjbDAO.queryAll();
    }

    @DeleRedis
    @Override
    public void add(Food food) {
        wjbDAO.add(food);
    }

    @DeleRedis
    @Override
    public void delete(Integer id) {
        wjbDAO.delete(id);
    }

    @AddRedis
    @Override
    public Food selectById(Integer id) {
        Food food = wjbDAO.selectById(id);
        return food;
    }

    @DeleRedis
    @Override
    public void update(Food food) {
        wjbDAO.update(food);
    }

    @Override
    public List<Food> queryEs(String message){
        //2.设置高亮的属性
        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        field.preTags("<span style='color:red'>");  //前缀
        field.postTags("</span>");  //后缀
        field.requireFieldMatch(false); //开启多行高亮

        //设置查询条件对象
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("foods")
                .withTypes("fod")
                .withQuery(QueryBuilders.queryStringQuery(message).field("foodName").field("person").field("fileFood").field("foodImporter"))  //bool查询
                .withHighlightFields(field)  //1.开启高亮
                .build();
        AggregatedPage<Food> emps = elasticsearchTemplate.queryForPage(searchQuery, Food.class, new SearchResultMapper() {
            @Override  //匿名内部类     searchResponse:搜索响应对象包含搜索结果
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                //获取搜索命中的对象
                SearchHit[] hits = searchResponse.getHits().getHits();

                ArrayList<Food> empList = new ArrayList<>();
                for (SearchHit hit : hits) {

                    //获取搜索的原始对象  {birthday=1621493552076, name=小石头, sign=我是中国人, id=4, age=12}
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    //System.out.println("获取搜索的原始对象: "+sourceAsMap);


                    String id = sourceAsMap.get("id").toString();
                    String foodName = sourceAsMap.get("foodName").toString();
                    String person = sourceAsMap.get("person").toString();
                    String foodSrc = sourceAsMap.get("foodSrc").toString();
                    String fileFood = sourceAsMap.get("fileFood").toString();
                    String foodImporter = sourceAsMap.get("foodImporter").toString();

                    //将日期由String类型转为long类型
                    long birthdays = Long.parseLong(sourceAsMap.get("foodDate").toString());
                    //将日期由long类型转为Date类型
                    Date birthday = new Date(birthdays);

//                    String sign = sourceAsMap.get("sign").toString();

                    //通过原始数据给对象赋值

                    Food food = new Food(Integer.valueOf(id),foodName,foodSrc,birthday,person,fileFood,foodImporter);

                    /** 处理高亮的数据
                     {
                     sign=[sign],
                     fragments[[我是一个小<span style='color:red'>石头</span>]],
                     name=[name],
                     fragments[[小<span style='color:red'>石头</span>]]}
                     }
                     * */
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    //System.out.println("处理高亮的数据: "+highlightFields);

                    //获取高亮的foodName
                    if(highlightFields.containsKey("foodName")){
                        String name2 = highlightFields.get("foodName").fragments()[0].toString();
                        food.setFoodName(name2); //将高亮的name放入对象
                    }
                    //获取高亮的person
                    if(highlightFields.containsKey("person")){
                        String person1 = highlightFields.get("person").fragments()[0].toString();
                        food.setPerson(person1); //将高亮的signs放入对象

                    }
                    //fileFood
                    if(highlightFields.containsKey("fileFood")){
                        String fileFood2 = highlightFields.get("fileFood").fragments()[0].toString();
                        food.setFileFood(fileFood2); //将高亮的signs放入对象

                    }
                    //foodImporter
                    if(highlightFields.containsKey("foodImporter")){
                        String foodImporter2 = highlightFields.get("foodImporter").fragments()[0].toString();
                        food.setFoodImporter(foodImporter2); //将高亮的signs放入对象

                    }

                    //将对象放入集合
                    empList.add(food);
                }
                //强转 返回
                return new AggregatedPageImpl<T>((List<T>) empList);
            }
        });
        List<Food> empList = emps.getContent();

        return empList;
    }
}
