package com.app.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import com.app.domain.Article;

public class QuickArticleClientV3OAuth {

	private static final String QUICK_Article_URI_V3 = "http://localhost:8080/oauth2/v3/Articles";
	
	public Article getArticleById(Long ArticleId) {
		OAuth2RestTemplate restTemplate = restTemplate();
		return restTemplate.getForObject(QUICK_Article_URI_V3 + "/{ArticleId}", Article.class, ArticleId);
	}

	private OAuth2RestTemplate restTemplate() {
		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setGrantType("password");
		resourceDetails.setAccessTokenUri("http://localhost:8080/oauth/token");
		resourceDetails.setClientId("quickArticleiOSClient");
		resourceDetails.setClientSecret("top_secret");
		
		// Set scopes
		List<String> scopes = new ArrayList<>();
		scopes.add("read"); scopes.add("write");
		resourceDetails.setScope(scopes);
		
		// Resource Owner details
		resourceDetails.setUsername("mickey");
		resourceDetails.setPassword("cheese");

		return new OAuth2RestTemplate(resourceDetails);
	}
	
	public static void main(String[] args) {
		QuickArticleClientV3OAuth client = new QuickArticleClientV3OAuth();
		Article Article = client.getArticleById(1L);
		System.out.println("Article: " + Article);
	}
}
