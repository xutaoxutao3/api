package com.mtdhb.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.mtdhb.api.entity.User;

/**
 * @author i@huangdenghe.com
 * @date 2017/12/02
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByMail(String mail);

    User findByToken(String token);

}
