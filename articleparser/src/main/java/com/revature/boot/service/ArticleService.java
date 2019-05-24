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
	ArticleRepository artistRepository;

	public void setArtistRepository(ArticleRepository artistRepository) {
		this.artistRepository = artistRepository;
	}
	
	@Transactional
	public List<Article> getAllArtists() {
		return this.artistRepository.findAll();
	}
	
	@Transactional
	public Article saveNewArtist(Article a) {
		return artistRepository.save(a);
	}
	
	@Transactional
	public void deleteById(Long id) {
		artistRepository.deleteById(id);
	}

}
