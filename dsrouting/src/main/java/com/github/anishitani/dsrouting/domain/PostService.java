package com.github.anishitani.dsrouting.domain;

public interface PostService {
    Long create(Post post);
    Post get(Long id);
}
