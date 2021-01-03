package com.github.anishitani.dsrouting.domain;

import com.github.anishitani.dsrouting.repository.PostRepository;

public class PostServiceImpl implements PostService {
    private PostRepository repository;

    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Long create(Post post) {
        return repository.create(post);
    }

    @Override
    public Post get(Long id) {
        return repository.get(id);
    }
}
