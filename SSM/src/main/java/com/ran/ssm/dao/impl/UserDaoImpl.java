package com.ran.ssm.dao.impl;

import com.ran.ssm.dao.BaseDao;
import com.ran.ssm.domain.User;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends SqlSessionDaoSupport implements BaseDao<User> {

    public void insert(User user) {
        getSqlSession().insert("users.insert",user);
    }

    public void update(User user) {
        getSqlSession().update("users.update",user);

    }

    public void delete(Integer id) {
        getSqlSession().delete("users.delete",id);
    }

    public User selectOne(Integer id) {
        return getSqlSession().selectOne("users.selectOne",id);
    }

    public List<User> selectAll() {
        return getSqlSession().selectList("users.selectAll");
    }

    public List<User> selectPage(int offset, int len) {
        return getSqlSession().selectList("users.selectPage",new RowBounds(offset,len));
    }

    public int selectCount() {
        return getSqlSession().selectOne("users.selectCount");
    }
}
