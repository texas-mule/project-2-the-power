package com.revature.boot.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.boot.domain.Article;
import com.revature.boot.repository.ArticleRepository;

@Service
public class ArticleService {
	@Autowired
	ArticleRepository articleRepository;

	public void setArticleRepository(ArticleRepository ArticleRepository) {
		this.articleRepository = articleRepository;
	}
	
	@Transactional
	public List<Article> getAllArticles() {
		return this.articleRepository.findAll();
	}
	
	@Transactional
	public Article saveNewArticle(Article a) {
		return articleRepository.save(a);
	}
	
	@Transactional
	public void deleteById(Long id) {
		articleRepository.deleteById(id);
	}

}
