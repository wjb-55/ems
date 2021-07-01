package com.baizhi.mysqlredis.dao;


import com.baizhi.mysqlredis.entity.Food;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface WjbDAO {
    //查询全部
    List<Food> queryAll();
    //添加食品
    void add(Food food);
    //删除根据id
    void delete(int id);
    //根据id查询一个
    Food selectById(int id);
    //修改
    void update(Food food);
}
