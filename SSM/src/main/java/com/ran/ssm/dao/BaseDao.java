package com.ran.ssm.dao;

import java.util.List;

/**
 * 基本dao接口：只有增删改查,供OrderDao和UserDao实现
 * @param <T>
 */
public interface BaseDao<T> {
    public void insert(T t);
    public void update(T t);
    public void delete(Integer id);
    public T selectOne(Integer id);
    public List<T> selectAll();
    public List<T> selectPage(int offset,int len);
    public int selectCount();
}
