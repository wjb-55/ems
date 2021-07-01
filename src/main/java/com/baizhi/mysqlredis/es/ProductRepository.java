package com.baizhi.mysqlredis.es;

import com.baizhi.mysqlredis.entity.Food;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepository extends ElasticsearchRepository<Food,Integer> {
}
