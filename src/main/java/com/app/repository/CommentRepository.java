package com.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {

}
