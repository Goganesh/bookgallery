drop table if exists authors;
drop table if exists books;
drop table if exists genres;
drop table if exists book_genre;
drop table if exists authorities;
drop table if exists users;


create table authors(
    id bigint auto_increment,
    name varchar(255),
    primary key(id)
);

create table books (
    id bigint auto_increment,
    name varchar(255),
    author_id bigint references authors(id) on delete cascade,
    primary key(id)
);

create table genres(
    id bigint auto_increment,
    name varchar(255),
    primary key(id)
);

create table book_genre(
    book_id bigint references books(id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (book_id, genre_id)
);

CREATE TABLE users (
  id bigint auto_increment,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (id)
);

CREATE TABLE authorities (
  id bigint auto_increment,
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
  --FOREIGN KEY (username) REFERENCES users(username)
);