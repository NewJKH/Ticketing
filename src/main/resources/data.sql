INSERT INTO concert (id, title, description, date, place, artist)
VALUES (1, 'First Concert', 'This is the one and only concert', '2025-07-15', 'Zep', 'IU');

INSERT INTO concert_sector (name, concert_id, remain)
VALUES ('A', 1, 50),
       ('B', 1, 100),
       ('C', 1, 150);

-- 비밀번호는 password를 bcrypt 인코딩한 문자열
-- 스프링 시큐리티 DelegatingPasswordEncoder 적용 시
-- 맨 앞에 {bcrypt}) 추가 필요
INSERT INTO member(email, name, password)
VALUES ('user@email.com', 'name', '$2a$10$6o1Y4VAS5KC8Vg9yJ8wPGepLO.9Awlold6nqWFkpqSQO3/wR3r7Qi');