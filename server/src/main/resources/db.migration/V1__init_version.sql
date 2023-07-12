
DROP TABLE IF EXISTS `client`;

CREATE TABLE client
(
    id               INTEGER GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    chat_id          INTEGER,
    start_bot_time   TIMESTAMP WITHOUT TIME ZONE,
    last_action_time TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_client PRIMARY KEY (id)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;