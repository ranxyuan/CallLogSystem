package com.ran.ssm.service.impl;

import com.ran.ssm.dao.BaseDao;
import com.ran.ssm.domain.Item;
import com.ran.ssm.service.ItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//service和dao打交道，需要传个用户dao进来 ,后边的实现是为了后期扩展功能的时候用的，直接在UserService中扩展
@Service("itemService")
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {
    //在子类中覆盖掉父类的setDao，完成注入
    @Resource(name = "itemDaoImpl")
    public void setDao(BaseDao<Item> dao) {
        super.setDao(dao);
    }

}
