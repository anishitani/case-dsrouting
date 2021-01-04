CREATE SCHEMA blog;

CREATE TABLE blog.post (
    id SERIAL PRIMARY KEY,
    author VARCHAR,
    title VARCHAR,
    summary VARCHAR,
    content VARCHAR,
    tags VARCHAR[],
    created_at TIMESTAMP DEFAULT now(),
    modified_at TIMESTAMP DEFAULT now()
);