package com.pay.dao;

import com.pay.domain.User;
import org.springframework.stereotype.Repository;

/**
 * Created by tolink on 2019-03-28.
 */
@Repository
public interface UserMapper {

    public int insert(User user);

}
