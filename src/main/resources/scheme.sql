CREATE TABLE IF NOT EXISTS posts
(
    id    SERIAL PRIMARY KEY,
    title varchar(256) NOT NULL,
    text  text,
    image bytea,
    likes int DEFAULT 0
);

CREATE TABLE IF NOT EXISTS comments
(
    id      SERIAL PRIMARY KEY,
    post_id bigint NOT NULL,
    text    text   NOT NULL,
    FOREIGN KEY (post_id)
        REFERENCES posts (id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tags
(
    tag varchar(30) PRIMARY KEY
);


CREATE TABLE IF NOT EXISTS post_tags
(
    post_id INTEGER REFERENCES posts ON DELETE CASCADE,
    tag_id  VARCHAR REFERENCES tags,
    PRIMARY KEY (post_id, tag_id)
);