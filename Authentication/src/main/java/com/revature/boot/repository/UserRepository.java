package com.revature.boot.repository;

import com.revature.boot.domain.User;

import java.util.List;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	User findByUsername(String name);
	
	@Transactional
	List<User> findByIdBetween(Integer min, Integer max);
}
