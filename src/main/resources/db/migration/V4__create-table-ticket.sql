CREATE TABLE Ticket (
 id SERIAL PRIMARY KEY,
 person_name VARCHAR(100),
 creation_time TIMESTAMP NOT NULL,
 session_id INT REFERENCES Session(id)
);