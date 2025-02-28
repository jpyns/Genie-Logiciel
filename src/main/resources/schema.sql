DROP TABLE IF EXISTS collection_books;
DROP TABLE IF EXISTS collections;
DROP TABLE IF EXISTS books;

CREATE TABLE books (
  id SERIAL PRIMARY KEY,
  author VARCHAR(255) NOT NULL,
  title VARCHAR(255) NOT NULL,
  year INT CHECK (year > 0),
  genre VARCHAR(100) NOT NULL,
  country VARCHAR(100) NOT NULL
);

CREATE TABLE collections (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  jaro_winkler_distance DOUBLE PRECISION,
  jaccard_distance DOUBLE PRECISION
);

CREATE TABLE collection_books (
  collection_id INT REFERENCES collections(id) ON DELETE CASCADE,
  book_id INT REFERENCES books(id) ON DELETE CASCADE,
  PRIMARY KEY (collection_id, book_id)
);
