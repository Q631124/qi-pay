package com.pay.service.impl;

import com.pay.dao.UserMapper;
import com.pay.domain.User;
import com.pay.service.IUserService;
import com.pay.util.identity.IDGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Qi on 2020/6/7.
 */
@Service
public class UserServiceImpl implements IUserService{
    @Autowired
    private UserMapper userMapper;


    @Override
    public int insert(User user) {
        user.setId(IDGeneratorUtil.getID("ID"));
        user.setCreatedDate(new Date());
        return userMapper.insert(user);
    }
}
