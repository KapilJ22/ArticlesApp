package com.app.client;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.app.domain.Comment;
import com.app.domain.Article;

public class QuickArticleClient {

	private static final String QUICK_Article_URI_V1 = "http://localhost:8080/v1/Articles";
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public Article getArticleById(Long ArticleId) {
		return restTemplate.getForObject(QUICK_Article_URI_V1 + "/{ArticleId}", Article.class, ArticleId);
	}

	public List<Article> getAllArticles() {
		ParameterizedTypeReference<List<Article>> responseType = new ParameterizedTypeReference<List<Article>>() {};
		ResponseEntity<List<Article>> responseEntity = restTemplate.exchange(QUICK_Article_URI_V1, HttpMethod.GET, null, responseType);
		List<Article> allArticles = responseEntity.getBody();

		//	Other possible implementations
		//	List allArticles =  restTemplate.getForObject(QUICK_Article_URI_V1, List.class);
		//	Article[] allArticles = restTemplate.getForObject(QUICK_Article_URI_V1, Article[].class);

		return allArticles;
	}
	
	public URI createArticle(Article Article) {
		return restTemplate.postForLocation( QUICK_Article_URI_V1, Article);
	}
	
	public void updateArticle(Article Article) {
		restTemplate.put(QUICK_Article_URI_V1 + "/{ArticleId}",  Article, Article.getId()); 
	}
	
	public void deleteArticle(Long ArticleId) {
		restTemplate.delete(QUICK_Article_URI_V1 + "/{ArticleId}",  ArticleId);
	}
	
	public static void main(String[] args) {
		QuickArticleClient client = new QuickArticleClient();
		
		// Test GetArticle
		Article Article = client.getArticleById(1L);
		System.out.println(Article);
		
		// Test GetAllArticles
		List<Article> allArticles = client.getAllArticles();
		System.out.println(allArticles);
		
		// Test Create Article
		Article newArticle = new Article();
		newArticle.setArticleText("What is your favourate color?");
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
		newComment.setValue("The Incredibles");
		ArticleForId6.getComments().add(newComment);
		
		client.updateArticle(ArticleForId6);
		
		ArticleForId6 = client.getArticleById(6L);
		System.out.println("Updated Article has " + ArticleForId6.getComments().size() + " Comments");
		
		// Test Delete
		client.deleteArticle(5L);
	}
	
}
