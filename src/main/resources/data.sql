INSERT INTO books (title, author, year, genre, country) VALUES
  ('Contes', 'Hans Christian Andersen', 1837, 'Romanesque (recueil de contes)', 'Danemark'),
  ('Orgueil et Préjugés', 'Jane Austen', 1813, 'Romanesque (roman)', 'Royaume-Uni'),
  ('Le Père Goriot', 'Honoré de Balzac', 1835, 'Romanesque (roman)', 'France'),
  ('Trilogie : Molloy, Malone meurt, Innommable', 'Samuel Beckett', 1953, 'Romanesque (trilogie romanesque)', 'Irlande'),
  ('Décaméron', 'Boccace', 1353, 'Romanesque (recueil de nouvelles)', 'Italie');

INSERT INTO collections (name, jaro_winkler_distance, jaccard_distance) VALUES
  ('Classiques Européens', 0.87, 0.79),
  ('Romans et Nouvelles Romanesques', 0.88, 0.80);

INSERT INTO collection_books (collection_id, book_id) VALUES
  (1, 2), -- Orgueil et Préjugés
  (1, 3), -- Le Père Goriot
  (1, 4), -- Trilogie de Beckett
  (2, 1), -- Contes
  (2, 5); -- Décaméron
