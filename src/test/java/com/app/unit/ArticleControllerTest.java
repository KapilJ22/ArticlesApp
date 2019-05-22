package com.app.unit;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.app.QuickArticleApplication;
import com.app.domain.Article;
import com.app.repository.ArticleRepository;
import com.app.v1.controller.ArticleController;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuickArticleApplication.class)
@ContextConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class ArticleControllerTest {
	@Mock
	private ArticleRepository ArticleRepository;
	
	@InjectMocks
	ArticleController ArticleController;

	private MockMvc mockMvc;
	
    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
    	mockMvc = standaloneSetup(ArticleController).build();
    }
    
    @Test
    public void testGetAllArticles() throws Exception {
    	when(ArticleRepository.findAll()).thenReturn(new ArrayList<Article>());
    	mockMvc.perform(get("/v1/Articles"))
    			.andExpect(status().isOk())
    			.andExpect(content().string("[]")); 
    }
}
