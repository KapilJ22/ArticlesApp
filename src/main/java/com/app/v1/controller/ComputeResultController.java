package com.app.v1.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.domain.Like;
import com.app.dto.CommentCount;
import com.app.dto.LikeResult;
import com.app.repository.LikeRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController("computeResultControllerV1")
@RequestMapping("/v1/")
@Api(value = "computeresult", description = "Compute Results API")
public class ComputeResultController {
	
	@Inject
	private LikeRepository LikeRepository;

	
	@RequestMapping(value="/computeresult", method=RequestMethod.GET)
	@ApiOperation(value = "Computes the results of a given Article", response = LikeResult.class)
	public ResponseEntity<?> computeResult(@RequestParam Long ArticleId) {
		LikeResult LikeResult = new LikeResult();
		Iterable<Like> allLikes = LikeRepository.findByArticle(ArticleId);
		
		// Algorithm to count Likes
		int totalLikes = 0;
		Map<Long, CommentCount> tempMap = new HashMap<Long, CommentCount>();
		for(Like v : allLikes) {
			totalLikes ++;
			// Get the CommentCount corresponding to this Comment
			CommentCount CommentCount = tempMap.get(v.getComment().getId());
			if(CommentCount == null) {
				CommentCount = new CommentCount();
				CommentCount.setCommentId(v.getComment().getId());
				tempMap.put(v.getComment().getId(), CommentCount);
			}
			CommentCount.setCount(CommentCount.getCount()+1);
		}
		
		LikeResult.setTotalLikes(totalLikes);
		LikeResult.setResults(tempMap.values());
		
		return new ResponseEntity<LikeResult>(LikeResult, HttpStatus.OK);
	}
	
}