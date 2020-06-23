package com.ran.ssm.service;

import com.ran.ssm.domain.Person;
import com.ran.ssm.domain.User;

import java.util.List;

/**
 *
 */
public interface PersonService extends BaseService<Person> {
    public String selectNameByPhone(String phone);
}
