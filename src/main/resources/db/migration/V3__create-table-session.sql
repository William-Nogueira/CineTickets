CREATE TABLE Session (
  id SERIAL PRIMARY KEY,
  movie_id INT REFERENCES Movie(id),
  room_id INT REFERENCES Room(id),
  start_time TIMESTAMP NOT NULL,
  duration INT
);