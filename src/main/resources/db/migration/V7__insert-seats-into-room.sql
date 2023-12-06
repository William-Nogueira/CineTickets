DO $$
DECLARE
 room_id INT;
BEGIN
 FOR room_id IN 1..3 LOOP
   FOR i IN 1..50 LOOP
     INSERT INTO Seat (room_id) VALUES (room_id);
   END LOOP;
 END LOOP;
END $$;