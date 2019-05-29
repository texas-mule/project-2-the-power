package com.revature.boot.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.boot.domain.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{
	Article findById(Integer id);
	
	
	@Transactional
	List<Article> findByIdBetween(Integer min, Integer max);
}
