CREATE TABLE Movie (
  id SERIAL PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  genre VARCHAR(50),
  director VARCHAR(50),
  duration INT,
  active BOOLEAN DEFAULT TRUE
);