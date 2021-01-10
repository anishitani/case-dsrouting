package com.github.anishitani.dsrouting.infra;

import com.github.anishitani.dsrouting.repository.PostRepository;
import com.github.anishitani.dsrouting.repository.jooq.PostRepositoryJooq;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RepositoryBeanConfiguration {

    @Bean
    PostRepository postRepository(DataSource dataSource) {
        return new PostRepositoryJooq(dataSource);
    }

}
