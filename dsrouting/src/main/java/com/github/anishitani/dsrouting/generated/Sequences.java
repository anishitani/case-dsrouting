/*
 * This file is generated by jOOQ.
 */
package com.github.anishitani.dsrouting.generated;


import org.jooq.Sequence;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;


/**
 * Convenience access to all sequences in blog.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>blog.post_id_seq</code>
     */
    public static final Sequence<Integer> POST_ID_SEQ = Internal.createSequence("post_id_seq", Blog.BLOG, SQLDataType.INTEGER.nullable(false), null, null, null, null, false, null);
}