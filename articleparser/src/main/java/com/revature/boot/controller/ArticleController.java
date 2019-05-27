package com.revature.boot.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.boot.domain.Article;
import com.revature.boot.service.ArticleService;

@RestController
@RequestMapping("/artists")
public class ArticleController {
	@Autowired
	ArticleService articleService;
	
	@GetMapping
	public List<Article> getAll() {
		return articleService.getAllArticles();
	}
	
	@GetMapping("/savedqueries/{userid}")
	public void getSavedQueries(@PathVariable("userid") String userid){
		
	}
	
	@GetMapping("/searchcompany/{companyname}")
	public void searchCompany(@PathVariable("companyname") String companyname){
		
	}
	
	@PostMapping("/searchkeywords")
	public void searchKeywords(){
		
	}
	
	@PostMapping
	public Article add(@RequestBody @Valid Article a, Errors errors) {
		if(errors.hasErrors()) return null;
		return articleService.saveNewArticle(a);
	}
	
	@DeleteMapping("/{id}")
	public String deleteById(@PathVariable("id") Long id) {
		articleService.deleteById(id);
		return "deleted!";
	}
	
	@GetMapping("/oops")
	public void oops() throws IOException {
		throw new IOException();
	}
	
	@ExceptionHandler(IOException.class)
	@ResponseStatus(value=HttpStatus.I_AM_A_TEAPOT)
	public String ioProblem() {
		return "oops!";
	}
}
