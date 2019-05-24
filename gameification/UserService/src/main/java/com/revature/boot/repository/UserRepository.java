package com.revature.boot.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.boot.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByName(String name);
	
	@Transactional
	List<User> findByIdBetween(Integer min, Integer max);
}