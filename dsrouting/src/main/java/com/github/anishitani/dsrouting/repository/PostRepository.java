package com.github.anishitani.dsrouting.repository;

import com.github.anishitani.dsrouting.domain.Post;

public interface PostRepository {
    Long create(Post post);
    Post get(Long id);
}
