package com.github.anishitani.dsrouting.infra;

import com.github.anishitani.dsrouting.repository.PostRepository;
import com.github.anishitani.dsrouting.repository.jooq.PostRepositoryJooq;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryBeanConfiguration {

    @Bean
    PostRepository postRepository(
            DefaultDSLContext ctx
    ) {
        return new PostRepositoryJooq(ctx);
    }

}
