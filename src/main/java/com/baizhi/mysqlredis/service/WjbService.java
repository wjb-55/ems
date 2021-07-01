package com.baizhi.mysqlredis.service;



import com.baizhi.mysqlredis.entity.Food;
import java.util.List;

public interface WjbService {
    //查询全部
    List<Food> queryAll();
    //添加食品
    void add(Food food);
    //删除根据id
    void delete(Integer id);
    //根据id查询一个
    Food selectById(Integer id);
    //修改
    void update(Food food);
    //高亮查询返回得集合
    List<Food> queryEs(String message);
}
