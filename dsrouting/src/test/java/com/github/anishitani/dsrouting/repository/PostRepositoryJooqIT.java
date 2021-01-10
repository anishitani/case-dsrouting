package com.github.anishitani.dsrouting.repository;

import com.github.anishitani.dsrouting.domain.Post;
import com.github.anishitani.dsrouting.support.AbstractIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class PostRepositoryJooqIT extends AbstractIT {

    private PostRepository repository;

    @Autowired
    PostRepositoryJooqIT(PostRepository repository) {
        this.repository = repository;
    }

    @Test
    public void givenValidPostEntity_whenInserting_thenExpectSuccess(){
        Post post = new Post();
        post.setAuthor("author");
        post.setTitle("title");
        post.setSummary("summary");
        post.setContent("body");
        post.setTags(Arrays.asList("tag1", "tag2"));
        Long id = repository.create(post);
        assertNotNull(id);
    }

    @Transactional(readOnly = true)
    @Test
    public void givenValidPostEntity_whenInsertingInReadOnlyTransaction_thenExpectFail(){
        Post post = new Post();
        post.setAuthor("author");
        post.setTitle("title");
        post.setSummary("summary");
        post.setContent("body");
        post.setTags(Arrays.asList("tag1", "tag2"));
        assertThrows(
                RuntimeException.class,
                () -> repository.create(post)
        );
    }

    @Sql(statements = {
            " INSERT INTO blog.post(id,author,title,summary,content,tags) VALUES (1,'author','title','summary','content','{\"tag1\",\"tag2\"}')"
    })
    @Test
    public void givenValidId_whenInserting_thenExpectValidPost(){
        Long id = 1L;
        Post post = repository.get(id);
        assertEquals(1L, post.getId());
        assertEquals("author", post.getAuthor());
        assertEquals("title", post.getTitle());
        assertEquals("summary", post.getSummary());
        assertEquals("content", post.getContent());
        assertArrayEquals(new String[]{"tag1","tag2"}, post.getTags().toArray(String[]::new));
    }
}