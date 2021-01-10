package com.github.anishitani.dsrouting.repository.jooq;

import com.github.anishitani.dsrouting.domain.Post;
import com.github.anishitani.dsrouting.repository.PostRepository;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DefaultDSLContext;

import javax.sql.DataSource;

import static com.github.anishitani.dsrouting.generated.Tables.POST;

public class PostRepositoryJooq extends BaseJooqRepository implements PostRepository {


    private DSLContext jooq;

    public PostRepositoryJooq(DataSource dataSource) {
        super(dataSource);
        this.jooq = new DefaultDSLContext(this.configuration);
    }

    @Override
    public Long create(Post post) {
        Record record = jooq.insertInto(POST)
                .set(POST.AUTHOR, post.getAuthor())
                .set(POST.TITLE, post.getTitle())
                .set(POST.SUMMARY, post.getSummary())
                .set(POST.CONTENT, post.getContent())
                .set(POST.TAGS, post.getTags().toArray(String[]::new))
                .returning(POST.ID)
                .fetchOne();
        return record.get(POST.ID, Long.class);
    }

    @Override
    public Post get(Long id) {
        return jooq
                .selectFrom(POST)
                .where(POST.ID.eq(id.intValue()))
                .fetchOneInto(Post.class);
    }
}
