package com.app.dto;

import java.util.Collection;

public class LikeResult {

	private int totalLikes;
	private Collection<CommentCount> results;

	public int getTotalLikes() {
		return totalLikes;
	}
	public void setTotalLikes(int totalLikes) {
		this.totalLikes = totalLikes;
	}
	public Collection<CommentCount> getResults() {
		return results;
	}
	public void setResults(Collection<CommentCount> results) {
		this.results = results;
	}
}
