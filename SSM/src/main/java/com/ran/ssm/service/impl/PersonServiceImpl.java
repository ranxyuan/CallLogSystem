package com.ran.ssm.service.impl;

import com.ran.ssm.dao.BaseDao;
import com.ran.ssm.dao.impl.PersonDaoImpl;
import com.ran.ssm.domain.Item;
import com.ran.ssm.domain.Order;
import com.ran.ssm.domain.Person;
import com.ran.ssm.domain.User;
import com.ran.ssm.service.PersonService;
import com.ran.ssm.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 */
@Service("personService")
public class PersonServiceImpl extends BaseServiceImpl<Person> implements PersonService{

    @Resource(name="personDao")
    public void setDao(BaseDao<Person> dao) {
        super.setDao(dao);
    }

    public String selectNameByPhone(String phone){
        return ((PersonDaoImpl)getDao()).selectNameByPhone(phone) ;
    }
}