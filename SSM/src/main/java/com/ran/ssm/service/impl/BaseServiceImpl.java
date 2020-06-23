package com.ran.ssm.service.impl;

import com.ran.ssm.dao.BaseDao;
import com.ran.ssm.service.BaseService;

import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
    //超类中无法写Resource注入，只有子类才能决定具体情况，利用setDao方法在子类中覆盖父类中的方法，完成赋值
    private BaseDao<T> dao;

    public void setDao(BaseDao<T> dao) {
        this.dao = dao;
    }

    public BaseDao<T> getDao() {
        return dao;
    }

    public void insert(T t) {
        dao.insert(t);
    }

    public void update(T t) {
        dao.update(t);
    }

    public void delete(Integer id) {
        dao.delete(id);
    }

    public T selectOne(Integer id) {
        return dao.selectOne(id);
    }

    public List<T> selectAll() {
        return dao.selectAll();
    }

    public List<T> selectPage(int offset, int len) {
        return dao.selectPage(offset,len);
    }

    public int selectCount() {
        return dao.selectCount();
    }
}
