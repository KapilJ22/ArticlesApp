package com.app.v3.controller;

import javax.inject.Inject;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.domain.Like;
import com.app.repository.LikeRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController("LikeControllerV3")
@RequestMapping("/v3/")
@Api(value = "Likes", description = "Likes API")
public class LikeController {

	@Inject
	private LikeRepository LikeRepository;
	
	@RequestMapping(value="/Articles/{ArticleId}/Likes", method=RequestMethod.POST)
	@ApiOperation(value = "Casts a new Like for a given Article", notes="The newly created Like Id will be sent in the location response header", 
	response = Void.class)
	@ApiResponses(value = {@ApiResponse(code=201, message="Like Created Successfully", response=Void.class) })
	public ResponseEntity<Void> createLike(@PathVariable Long ArticleId, @RequestBody Like Like) {
		Like = LikeRepository.save(Like);
		
		// Set the headers for the newly created resource
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(Like.getId()).toUri());
		
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/Articles/{ArticleId}/Likes", method=RequestMethod.GET)
	@ApiOperation(value = "Retrieves all the Likes", response=Like.class, responseContainer="List")
	public Iterable<Like> getAllLikes(@PathVariable Long ArticleId, Pageable pageable) {
		return LikeRepository.findByArticle(ArticleId);
	}	
}
