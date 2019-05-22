package com.app.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.app.domain.Article;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

}
