package com.app.unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.app.domain.Article;
import com.app.repository.ArticleRepository;
import com.app.v1.controller.ArticleController;
import com.google.common.collect.Lists;

public class ArticleControllerTestMock {

	@Mock
	private ArticleRepository ArticleRepository;
	
	@Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testGetAllArticles() {
		ArticleController ArticleController  = new ArticleController();
    	ReflectionTestUtils.setField(ArticleController, "ArticleRepository", ArticleRepository);
    	
    	when(ArticleRepository.findAll()).thenReturn(new ArrayList<Article>());
		ResponseEntity<Iterable<Article>> allArticlesEntity = ArticleController.getAllArticles();
		verify(ArticleRepository, times(1)).findAll();
		assertEquals(HttpStatus.OK, allArticlesEntity.getStatusCode());
		assertEquals(0, Lists.newArrayList(allArticlesEntity.getBody()).size());
	}
	
}
