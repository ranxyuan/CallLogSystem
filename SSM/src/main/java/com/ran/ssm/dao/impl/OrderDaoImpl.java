package com.ran.ssm.dao.impl;

import com.ran.ssm.dao.BaseDao;
import com.ran.ssm.domain.Order;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl extends SqlSessionDaoSupport implements BaseDao<Order> {

    public void insert(Order order) {
        getSqlSession().insert("orders.insert",order);
    }

    public void update(Order order) {
        getSqlSession().update("orders.update",order);

    }

    public void delete(Integer id) {
        getSqlSession().delete("orders.delete",id);
    }

    public Order selectOne(Integer id) {
        return getSqlSession().selectOne("orders.selectOne",id);
    }

    public List<Order> selectAll() {
        return getSqlSession().selectList("orders.selectAll");
    }

    public List<Order> selectPage(int offset, int len) {
        return null;
    }

    public int selectCount() {
        return 0;
    }
}
