package com.ran.ssm.service;


import com.ran.ssm.domain.User;

import java.util.List;

/**
 * 当用户增加新功能的时候在这里添加
 */
public interface UserService extends BaseService<User>{
    //目前还不加新方法是还没有新功能要添加
    public void longTx();//添加一个长事务的方法
    public void save(User u);
}
