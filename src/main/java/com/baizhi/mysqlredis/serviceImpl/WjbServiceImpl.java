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
        //2.?????????????????????
        HighlightBuilder.Field field = new HighlightBuilder.Field("*");
        field.preTags("<span style='color:red'>");  //??????
        field.postTags("</span>");  //??????
        field.requireFieldMatch(false); //??????????????????

        //????????????????????????
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("foods")
                .withTypes("fod")
                .withQuery(QueryBuilders.queryStringQuery(message).field("foodName").field("person").field("fileFood").field("foodImporter"))  //bool??????
                .withHighlightFields(field)  //1.????????????
                .build();
        AggregatedPage<Food> emps = elasticsearchTemplate.queryForPage(searchQuery, Food.class, new SearchResultMapper() {
            @Override  //???????????????     searchResponse:????????????????????????????????????
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {

                //???????????????????????????
                SearchHit[] hits = searchResponse.getHits().getHits();

                ArrayList<Food> empList = new ArrayList<>();
                for (SearchHit hit : hits) {

                    //???????????????????????????  {birthday=1621493552076, name=?????????, sign=???????????????, id=4, age=12}
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    //System.out.println("???????????????????????????: "+sourceAsMap);


                    String id = sourceAsMap.get("id").toString();
                    String foodName = sourceAsMap.get("foodName").toString();
                    String person = sourceAsMap.get("person").toString();
                    String foodSrc = sourceAsMap.get("foodSrc").toString();
                    String fileFood = sourceAsMap.get("fileFood").toString();
                    String foodImporter = sourceAsMap.get("foodImporter").toString();

                    //????????????String????????????long??????
                    long birthdays = Long.parseLong(sourceAsMap.get("foodDate").toString());
                    //????????????long????????????Date??????
                    Date birthday = new Date(birthdays);

//                    String sign = sourceAsMap.get("sign").toString();

                    //?????????????????????????????????

                    Food food = new Food(Integer.valueOf(id),foodName,foodSrc,birthday,person,fileFood,foodImporter);

                    /** ?????????????????????
                     {
                     sign=[sign],
                     fragments[[???????????????<span style='color:red'>??????</span>]],
                     name=[name],
                     fragments[[???<span style='color:red'>??????</span>]]}
                     }
                     * */
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    //System.out.println("?????????????????????: "+highlightFields);

                    //???????????????foodName
                    if(highlightFields.containsKey("foodName")){
                        String name2 = highlightFields.get("foodName").fragments()[0].toString();
                        food.setFoodName(name2); //????????????name????????????
                    }
                    //???????????????person
                    if(highlightFields.containsKey("person")){
                        String person1 = highlightFields.get("person").fragments()[0].toString();
                        food.setPerson(person1); //????????????signs????????????

                    }
                    //fileFood
                    if(highlightFields.containsKey("fileFood")){
                        String fileFood2 = highlightFields.get("fileFood").fragments()[0].toString();
                        food.setFileFood(fileFood2); //????????????signs????????????

                    }
                    //foodImporter
                    if(highlightFields.containsKey("foodImporter")){
                        String foodImporter2 = highlightFields.get("foodImporter").fragments()[0].toString();
                        food.setFoodImporter(foodImporter2); //????????????signs????????????

                    }

                    //?????????????????????
                    empList.add(food);
                }
                //?????? ??????
                return new AggregatedPageImpl<T>((List<T>) empList);
            }
        });
        List<Food> empList = emps.getContent();

        return empList;
    }
}
