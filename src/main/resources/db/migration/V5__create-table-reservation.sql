CREATE TABLE Reservation (
 id SERIAL PRIMARY KEY,
 person_name VARCHAR(100),
 session_id INT REFERENCES Session(id),
 seat_id int REFERENCES Seat(id)
);