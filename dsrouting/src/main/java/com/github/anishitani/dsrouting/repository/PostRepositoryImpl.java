package com.github.anishitani.dsrouting.repository;

import com.github.anishitani.dsrouting.domain.Post;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class PostRepositoryImpl implements PostRepository {
    private final DataSource dataSource;
    private final DSLContext sqlCtx;

    public PostRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        this.sqlCtx = DSL.using(dataSource, SQLDialect.POSTGRES);
    }

    @Override
    public Long create(Post post) {
        Record record = sqlCtx.insertInto(table("post"))
                .set(field("author"), post.getAuthor())
                .set(field("title"), post.getTitle())
                .set(field("summary"), post.getSummary())
                .set(field("body"), post.getBody())
                .set(field("tags"), post.getTags().toArray(String[]::new))
                .returning(field("id"))
                .fetchOne();
        return record.get("id", Long.class);
    }

    @Override
    public Post get(Long id) {
        return sqlCtx.selectFrom(table("post")).where(field("id").eq(id)).fetchOneInto(Post.class);
    }
}
