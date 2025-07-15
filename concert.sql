CREATE TABLE `Ticket`
(
    `number` VARCHAR(30) NULL,
    `sector` VARCHAR(5) NULL,
    `purchase_date` DATE NULL,
    `email` VARCHAR(30) NOT NULL,
    `concert_id` Long NOT NULL
);

CREATE TABLE `Concert`
(
    `id` Long NOT NULL,
    `title` VARCHAR(20) NULL,
    `description` VARCHAR(100) NULL,
    `date` DATE NULL,
    `place` VARCHAR(40) NULL,
    `artist` VARCHAR(20) NULL
);

CREATE TABLE `Member`
(
    `email` VARCHAR(30) NOT NULL,
    `name` VARCHAR(20) NULL,
    `password` VARCHAR(100) NULL
);

CREATE TABLE `Concert_Sector`
(
    `name` VARCHAR(5) NOT NULL,
    `concert_id` Long    NOT NULL,
    `remain`     Integer NULL
);

ALTER TABLE `Ticket`
    ADD CONSTRAINT `PK_TICKET` PRIMARY KEY (`number`);

ALTER TABLE `Concert`
    ADD CONSTRAINT `PK_CONCERT` PRIMARY KEY (`id`);

ALTER TABLE `Member`
    ADD CONSTRAINT `PK_MEMBER` PRIMARY KEY (`email`);

ALTER TABLE `Concert_Sector`
    ADD CONSTRAINT `PK_CONCERT_SECTOR` PRIMARY KEY (`name`);

ALTER TABLE `Ticket`
    ADD CONSTRAINT `FK_Member_TO_Ticket_1` FOREIGN KEY (`email`) REFERENCES `Member` (`email`);

ALTER TABLE `Ticket`
    ADD CONSTRAINT `FK_Concert_TO_Ticket_1` FOREIGN KEY (`concert_id`) REFERENCES `Concert` (`id`);

ALTER TABLE `Concert_Sector`
    ADD CONSTRAINT `FK_Concert_TO_Concert_Sector_1` FOREIGN KEY (`concert_id`) REFERENCES `Concert` (`id`);

