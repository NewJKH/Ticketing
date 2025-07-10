DELETE
FROM concert_sector
WHERE concert_id = 1
  AND name IN ('A', 'B', 'C');

DELETE
FROM concert
WHERE id = 1;