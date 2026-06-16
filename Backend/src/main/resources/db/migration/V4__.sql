ALTER TABLE posts
    DROP CONSTRAINT fk_posts_on_user;

ALTER TABLE posts
    DROP COLUMN user_id;

ALTER TABLE posts
    DROP COLUMN location;

DROP SEQUENCE categories_id_seq CASCADE;

ALTER TABLE posts
    ADD location GEOMETRY(Point, 4326);