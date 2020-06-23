package com.ran.ssm.service;

import com.ran.ssm.domain.User;

import java.util.List;

/**
 * 当需要改变业务的时候是在这里添加:这里只放每个service的公共的部分
 * 不同的业务模块可能会随时改变，那么增加的业务方法就不能放在公共的service接口中
 * 需要单独为UserService创建接口
 * @param <T>
 */
//公共接口
public interface BaseService<T> {
    public void insert(T t);
    public void update(T t);
    public void delete(Integer id);
    public T selectOne(Integer id);
    public List<T> selectAll();

    /**
     * 分页查询
     * @param offset
     * @param len
     * @return
     */
    public List<T> selectPage(int offset, int len);
    public int selectCount();

}
