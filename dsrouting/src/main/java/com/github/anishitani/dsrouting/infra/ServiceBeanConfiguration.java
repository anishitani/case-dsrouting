package com.github.anishitani.dsrouting.infra;

import com.github.anishitani.dsrouting.domain.PostService;
import com.github.anishitani.dsrouting.domain.PostServiceImpl;
import com.github.anishitani.dsrouting.repository.PostRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeanConfiguration {
    @Bean
    PostService postService(PostRepository postRepository) {
        return new PostServiceImpl(postRepository);
    }
}
