package com.app.client;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.app.domain.Comment;
import com.app.domain.Article;

public class QuickArticleClientV2 {

	private static final String QUICK_Article_URI_2 = "http://localhost:8080/v2/Articles";
	private RestTemplate restTemplate = new RestTemplate();
	
	public PageWrapper<Article> getAllArticles(int page, int size) {
		ParameterizedTypeReference<PageWrapper<Article>> responseType = new ParameterizedTypeReference<PageWrapper<Article>>() {};
		UriComponentsBuilder builder = UriComponentsBuilder
										.fromHttpUrl(QUICK_Article_URI_2)
										.queryParam("page", page)
										.queryParam("size", size);
		
		ResponseEntity<PageWrapper<Article>> responseEntity = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, null, responseType);
		PageWrapper<Article> allArticles = responseEntity.getBody();
		return allArticles;
	}
	
	public Article getArticleById(Long ArticleId) {
		return restTemplate.getForObject(QUICK_Article_URI_2 + "/{ArticleId}", Article.class, ArticleId);
	}
	
	public URI createArticle(Article Article) {
		return restTemplate.postForLocation( QUICK_Article_URI_2, Article);
	}
	
	public void updateArticle(Article Article) {
		restTemplate.put(QUICK_Article_URI_2 + "/{ArticleId}",  Article, Article.getId()); 
	}
	
	public void deleteArticle(Long ArticleId) {
		restTemplate.delete(QUICK_Article_URI_2 + "/{ArticleId}",  ArticleId);
	}
	
	public static void main(String[] args) {
		QuickArticleClientV2 client = new QuickArticleClientV2();
		
		// Test GetArticle
		Article Article = client.getArticleById(1L);
		System.out.println(Article);
		
		// Test getAllArticles
		PageWrapper<Article> allArticles = client.getAllArticles(2, 3);
		System.out.println(allArticles);
		
		// Test Create Article
		Article newArticle = new Article();
		newArticle.setArticleText("What is your favourate color 2?");
		Set<Comment> Comments = new HashSet<>();
		newArticle.setComments(Comments);
		
		Comment Comment1 = new Comment();
		Comment1.setValue("Red");
		Comments.add(Comment1);
		
		Comment Comment2 = new Comment();
		Comment2.setValue("Blue");
		Comments.add(Comment2);
		URI ArticleLocation = client.createArticle(newArticle);
		System.out.println("Newly Created Article Location " + ArticleLocation);
		
		// Test Update Article with Id 6
		Article ArticleForId6 = client.getArticleById(6L);
		// Add a new Comment
		Comment newComment = new Comment();
		newComment.setValue("The Incredibles 2");
		ArticleForId6.getComments().add(newComment);
		
		client.updateArticle(ArticleForId6);
		
		ArticleForId6 = client.getArticleById(6L);
		System.out.println("Updated Article has " + ArticleForId6.getComments().size() + " Comments");
		
		// Test Delete
		client.deleteArticle(9L);
	}
	
	
}
