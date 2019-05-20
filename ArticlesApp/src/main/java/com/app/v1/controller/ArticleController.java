package com.app.v1.controller;

import java.net.URI;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.domain.Article;
import com.app.dto.error.ErrorDetail;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.ArticleRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController("ArticleControllerV1")
@RequestMapping("/v1/")
@Api(value = "Articles", description = "Article API")
public class ArticleController {

	@Inject
	private ArticleRepository ArticleRepository;
	
	@RequestMapping(value="/Articles", method=RequestMethod.POST)
	@ApiOperation(value = "Creates a new Article", notes="The newly created Article Id will be sent in the location response header", 
					response = Void.class)
	@ApiResponses(value = {@ApiResponse(code=201, message="Article Created Successfully", response=Void.class),  
			@ApiResponse(code=500, message="Error creating Article", response=ErrorDetail.class) } )
	public ResponseEntity<Void> createArticle(@Valid @RequestBody Article Article) {
		Article = ArticleRepository.save(Article);
		
		// Set the location header for the newly created resource
		HttpHeaders responseHeaders = new HttpHeaders();
		URI newArticleUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Article.getId()).toUri();
		responseHeaders.setLocation(newArticleUri);
		
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value="/Articles/{ArticleId}", method=RequestMethod.GET)
	@ApiOperation(value = "Retrieves given Article", response=Article.class)
	@ApiResponses(value = {@ApiResponse(code=200, message="", response=Article.class),  
			@ApiResponse(code=404, message="Unable to find Article", response=ErrorDetail.class) } )
	public ResponseEntity<?> getArticle(@PathVariable Long ArticleId) {
		verifyArticle(ArticleId);
		Article p = ArticleRepository.findOne(ArticleId);
		return new ResponseEntity<> (p, HttpStatus.OK);
	}
	
	@RequestMapping(value="/Articles", method=RequestMethod.GET)
	@ApiOperation(value = "Retrieves all the Articles", response=Article.class, responseContainer="List")
	public ResponseEntity<Iterable<Article>> getAllArticles() {
		Iterable<Article> allArticles = ArticleRepository.findAll();
		return new ResponseEntity<>(allArticles, HttpStatus.OK);
	}

	@RequestMapping(value="/Articles/{ArticleId}", method=RequestMethod.PUT)
	@ApiOperation(value = "Updates given Article", response=Void.class)
	@ApiResponses(value = {@ApiResponse(code=200, message="", response=Void.class),  
			@ApiResponse(code=404, message="Unable to find Article", response=ErrorDetail.class) } )
	public ResponseEntity<Void> updateArticle(@RequestBody Article Article, @PathVariable Long ArticleId) {
		verifyArticle(ArticleId);
		ArticleRepository.save(Article);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/Articles/{ArticleId}", method=RequestMethod.DELETE)
	@ApiOperation(value = "Deletes given Article", response=Void.class)
	@ApiResponses(value = {@ApiResponse(code=200, message="", response=Void.class),  
			@ApiResponse(code=404, message="Unable to find Article", response=ErrorDetail.class) } )
	public ResponseEntity<Void> deleteArticle(@PathVariable Long ArticleId) {
		verifyArticle(ArticleId);
		ArticleRepository.delete(ArticleId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	protected void verifyArticle(Long ArticleId) throws ResourceNotFoundException {
		Article Article = ArticleRepository.findOne(ArticleId);
		if(Article == null) {
			throw new ResourceNotFoundException("Article with id " + ArticleId + " not found"); 
		}
	}
}
