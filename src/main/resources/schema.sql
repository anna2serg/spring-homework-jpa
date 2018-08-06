DROP TABLE IF EXISTS public.books_authors;
DROP TABLE IF EXISTS public.books;
DROP TABLE IF EXISTS public.authors;
DROP TABLE IF EXISTS public.genres;

DROP SEQUENCE IF EXISTS public.genre_seq;
DROP SEQUENCE IF EXISTS public.author_seq;
DROP SEQUENCE IF EXISTS public.book_seq;

CREATE SEQUENCE public.genre_seq;
CREATE SEQUENCE public.author_seq;
CREATE SEQUENCE public.book_seq;

ALTER SEQUENCE public.genre_seq RESTART WITH 100;
ALTER SEQUENCE public.author_seq RESTART WITH 100;
ALTER SEQUENCE public.book_seq RESTART WITH 100;

CREATE TABLE public.genres
(
    genre_id integer NOT NULL,
    name character varying(255),
    CONSTRAINT genres_pkey PRIMARY KEY (genre_id)
);

CREATE TABLE public.authors
(
    author_id integer NOT NULL,
    firstname character varying(255),
    middlename character varying(255),
    surname character varying(255),
    CONSTRAINT authors_pkey PRIMARY KEY (author_id)
);

CREATE TABLE public.books
(
    book_id integer NOT NULL,
    name character varying(255),
    genre_id integer,
    CONSTRAINT books_pkey PRIMARY KEY (book_id),
    CONSTRAINT fk_genre FOREIGN KEY (genre_id)
        REFERENCES public.genres (genre_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.books_authors
(
    book_id integer NOT NULL,
    author_id integer NOT NULL,
    CONSTRAINT books_authors_pkey PRIMARY KEY (book_id, author_id),
    CONSTRAINT fk_book FOREIGN KEY (book_id)
        REFERENCES public.books (book_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_author FOREIGN KEY (author_id)
        REFERENCES public.authors (author_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);