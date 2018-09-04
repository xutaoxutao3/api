package com.mtdhb.api.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mtdhb.api.entity.Girl;

/**
 * @author i@huangdenghe.com
 * @date 2018/03/03
 */
public interface GirlRepository extends JpaRepository<Girl, Long> {
	
}
