CREATE TABLE `Ticket`
(
    `number`        VARCHAR(20) NULL,
    `place`         VARCHAR(20) NULL,
    `purchase_date` DATE        NULL,
    `email`         VARCHAR(20) NOT NULL,
    `Key`           Long        NOT NULL
);

CREATE TABLE `Concert`
(
    `id`          BIGINT       NOT NULL,
    `title`       VARCHAR(20)  NULL,
    `description` VARCHAR(100) NULL,
    `date`        DATE         NULL,
    `place`       VARCHAR(40)  NULL,
    `artist`      VARCHAR(20)  NULL
);

CREATE TABLE `Member`
(
    `email`    VARCHAR(20) NOT NULL,
    `name`     VARCHAR(10) NULL,
    `password` VARCHAR(20) NULL
);

CREATE TABLE `Concert_Sector`
(
    `name`       VARCHAR(20) NOT NULL,
    `concert_id` BIGINT      NOT NULL,
    `remain`     Integer     NULL
);

ALTER TABLE `Ticket`
    ADD CONSTRAINT `PK_TICKET` PRIMARY KEY (
                                            `number`
        );

ALTER TABLE `Concert`
    ADD CONSTRAINT `PK_CONCERT` PRIMARY KEY (
                                             `id`
        );

ALTER TABLE `Member`
    ADD CONSTRAINT `PK_MEMBER` PRIMARY KEY (
                                            `email`
        );

ALTER TABLE `Concert_Sector`
    ADD CONSTRAINT `PK_CONCERT_SECTOR` PRIMARY KEY (
                                                    `name`
        );

