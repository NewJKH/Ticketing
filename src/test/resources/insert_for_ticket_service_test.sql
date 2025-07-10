INSERT INTO concert (id, title, description, date, place, artist)
VALUES (1, 'first concert', 'this is the one and only concert', '2025-07-10', 'Zep', 'JKH');

INSERT INTO concert_sector (name, concert_id, remain)
VALUES ('A', 1, 2),
       ('B', 1, 2),
       ('C', 1, 2);