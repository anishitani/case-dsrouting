package com.github.anishitani.dsrouting.repository;

import com.github.anishitani.dsrouting.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PostRepositoryImplIT {

    private PostRepository repository;

    @Autowired
    PostRepositoryImplIT(PostRepository repository) {
        this.repository = repository;
    }

    @Test
    public void givenValidPostEntity_whenInserting_thenExpectSuccess(){
        Post post = new Post();
        post.setAuthor("author");
        post.setTitle("title");
        post.setSummary("summary");
        post.setBody("body");
        post.setTags(Arrays.asList("tag1", "tag2"));
        Long id = repository.create(post);
        assertNotNull(id);
    }

    @Sql(statements = {
            " INSERT INTO post(id,author,title,summary,body,tags) VALUES (1,'author','title','summary','body','{\"tag1\",\"tag2\"}')"
    })
    @Test
    public void givenValidId_whenInserting_thenExpectValidPost(){
        Long id = 1L;
        Post post = repository.get(id);
        assertEquals(1L, post.getId());
        assertEquals("author", post.getAuthor());
        assertEquals("title", post.getTitle());
        assertEquals("summary", post.getSummary());
        assertEquals("body", post.getBody());
        assertArrayEquals(new String[]{"tag1","tag2"}, post.getTags().toArray(String[]::new));
    }
}