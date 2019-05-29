package com.revature.boot.controller;

import java.io.IOException;
import java.util.ArrayList;
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
@RequestMapping("/articles")
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
	public String searchCompany(@PathVariable("companyname") String companyname){
		ArrayList<Article> articles = new ArrayList();
		articles = (ArrayList<Article>) articleService.getAllArticles();
		String json = "";
		
		json += "{\"searched\":[";
		for(int i = 0; i < articles.size(); i++){
			
			json += "{\"title\":\"" + articles.get(i).getTitle() + "\",";
			json += "\"author\":\"" + articles.get(i).getAuthor() + "\",";
			json += "\"publisher\":\"" + articles.get(i).getPublisher() + "\",";
			json += "\"link\":\"" + articles.get(i).getLink() + "\",";
			json += "\"date_created\":\"" + articles.get(i).getDate_created() + "\",";
			json +=  "\"results\":" + articles.get(i).textSearch(articles.get(i).getBody(), companyname) + ", ";
			
		}
		
		json = json.substring(0, json.length() - 2);
		json += "]}";
		
		return json;
	}
	
	@GetMapping("/searchkeyword/{keyword}")
	public String searchKeyword(@PathVariable("keyword") String keyword){
		ArrayList<Article> articles = new ArrayList();
		articles = (ArrayList<Article>) articleService.getAllArticles();
		String json = "";
		
		json += "{\"searched\":[";
		for(int i = 0; i < articles.size(); i++){
			
			json += "{\"title\":\"" + articles.get(i).getTitle() + "\",";
			json += "\"author\":\"" + articles.get(i).getAuthor() + "\",";
			json += "\"publisher\":\"" + articles.get(i).getPublisher() + "\",";
			json += "\"link\":\"" + articles.get(i).getLink() + "\",";
			json += "\"date_created\":\"" + articles.get(i).getDate_created() + "\",";
			json +=  "\"results\":" + articles.get(i).textSearch(articles.get(i).getBody(), keyword) + ", ";
			
		}
		
		json = json.substring(0, json.length() - 2);
		json += "]}";
		
		return json;
	}
	
	@GetMapping("/getifkeywordmatch/{keyword}")
	public String getIfKeywordMatch(@PathVariable("keyword") String keyword) {
		ArrayList<Article> articles = new ArrayList();
		articles = (ArrayList<Article>) articleService.getAllArticles();

		ArrayList<Article> matchedArticles = new ArrayList();
		for (Article article : articles) {
			String body = article.getBody();
			String title = article.getTitle();
			String summary = article.getSummary();

			if (article.textSearch(body, keyword) != "no hits")
				matchedArticles.add(article);
			else if (article.textSearch(title, keyword) != "no hits")
				matchedArticles.add(article);
			else if (article.textSearch(summary, keyword) != "no hits")
				matchedArticles.add(article);
		}

		String json = "";

		json += "{\"searched\":[";
		for (int i = 0; i < matchedArticles.size(); i++) {

			json += "{\"title\":\"" + matchedArticles.get(i).getTitle() + "\",";
			json += "\"author\":\"" + matchedArticles.get(i).getAuthor() + "\",";
			json += "\"publisher\":\"" + matchedArticles.get(i).getPublisher() + "\",";
			json += "\"link\":\"" + matchedArticles.get(i).getLink() + "\",";
			json += "\"date_created\":\"" + matchedArticles.get(i).getDate_created() + "\",";
			json += "\"summary\":\"" + matchedArticles.get(i).getSummary() + "\",";
		}

		json = json.substring(0, json.length() - 2);
		json += "]}";

		return json;
	}
	
	@GetMapping("/getarticle/{id}")
	public Article getArticle(@PathVariable("id") Long id){
		return articleService.getArticleById(id);
	}
	
	@PostMapping
	public Article add(@RequestBody @Valid Article a, Errors errors) {
		if(errors.hasErrors()) return null;
		return articleService.saveNewArticle(a);
	}
	/*
	@DeleteMapping("/{id}")
	public String deleteById(@PathVariable("id") Long id) {
		articleService.deleteById(id);
		return "deleted!";
	}*/
	
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
