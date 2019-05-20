package com.app.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.app.domain.Like;

public interface LikeRepository extends CrudRepository<Like, Long> {

	@Query(value="select v.* from Comment o, Like v where o.Article_ID = ?1 and v.Comment_ID = o.Comment_ID", nativeQuery = true)
	public Iterable<Like> findByArticle(Long ArticleId);
	
}
