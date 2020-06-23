package com.ran.ssm.service.impl;

import com.ran.ssm.dao.BaseDao;
import com.ran.ssm.domain.Item;
import com.ran.ssm.domain.Order;
import com.ran.ssm.domain.User;
import com.ran.ssm.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

//service和dao打交道，需要传个用户dao进来 ,后边的实现是为了后期扩展功能的时候用的，直接在UserService中扩展
@Service("userService")
public class UserServiceImpl  extends BaseServiceImpl<User> implements UserService {
    @Resource(name = "itemDaoImpl")
    private BaseDao<Item> itemDao;

    //在子类中覆盖掉父类的setDao，完成注入
    @Resource(name = "userDaoImpl")
    public void setDao(BaseDao<User> dao) {
        super.setDao(dao);
    }

    /**
     * 长事务测试
     */
    public void longTx() {
        Item it = new Item();
        it.setItemName("item0012");
        Order o = new Order();
        o.setId(3);
        it.setOrder(o);
        itemDao.insert(it);
        this.delete(4);
    }

    public void save(User u){
        if (u.getId()!=null){
            this.update(u);
        }
        else {
            this.insert(u);
        }
    }



}
