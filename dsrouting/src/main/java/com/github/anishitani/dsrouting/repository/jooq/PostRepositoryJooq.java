package com.github.anishitani.dsrouting.repository.jooq;

import com.github.anishitani.dsrouting.domain.Post;
import com.github.anishitani.dsrouting.repository.PostRepository;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DefaultDSLContext;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class PostRepositoryJooq implements PostRepository {


    private DSLContext jooq;

    public PostRepositoryJooq(DefaultDSLContext ctx) {
        this.jooq = ctx;
    }

    @Override
    public Long create(Post post) {
        Record record = jooq.insertInto(table("blog.post"))
                .set(field("author"), post.getAuthor())
                .set(field("title"), post.getTitle())
                .set(field("summary"), post.getSummary())
                .set(field("content"), post.getContent())
                .set(field("tags"), post.getTags().toArray(String[]::new))
                .returning(field("id"))
                .fetchOne();
        return record.get("id", Long.class);
    }

    @Override
    public Post get(Long id) {
        return jooq
                .selectFrom(table("blog.post"))
                .where(field("id").eq(id))
                .fetchOneInto(Post.class);
    }
}
