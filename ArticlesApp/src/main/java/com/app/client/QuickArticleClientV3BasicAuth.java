package com.app.client;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class QuickArticleClientV3BasicAuth {

	private static final String QUICK_Article_URI_V3 = "http://localhost:8080/v3/Articles";
	private RestTemplate restTemplate = new RestTemplate();
	
	public void deleteArticle(Long ArticleId) {
		HttpHeaders authenticationHeaders = getAuthenticationHeader("admin", "admin");
		restTemplate.exchange(QUICK_Article_URI_V3 + "/{ArticleId}", 
						HttpMethod.DELETE, new HttpEntity<Void>(authenticationHeaders), Void.class, ArticleId);
	}
	
	// Basic Authentication
	private HttpHeaders getAuthenticationHeader(String username, String password) {
			
		String credentials = username + ":" + password;
		byte[] base64CredentialData = Base64.encodeBase64(credentials.getBytes());
			
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Basic " + new String(base64CredentialData));
		
		return headers;
	}
	
	public static void main(String[] args) {
		QuickArticleClientV3BasicAuth client = new QuickArticleClientV3BasicAuth();
		client.deleteArticle(4L);
	}
		
}
