INSERT INTO room (room_id, type)
SELECT * FROM (SELECT '1' AS room_id, 'SerenitasStandard' AS type) AS temp
WHERE NOT EXISTS (SELECT * FROM room WHERE room_id = '1');

INSERT INTO room (room_id, type)
SELECT * FROM (SELECT '2' AS room_id, 'SerenitasSweet' AS type) AS temp
WHERE NOT EXISTS (SELECT * FROM room WHERE room_id = '2');

INSERT INTO room (room_id, type)
SELECT * FROM (SELECT '3' AS room_id, 'SerenitasDeluxe' AS type) AS temp
WHERE NOT EXISTS (SELECT * FROM room WHERE room_id = '3');