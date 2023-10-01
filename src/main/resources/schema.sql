CREATE TABLE IF NOT EXISTS users
(
    user_id         BIGINT NOT NULL,
    billing_address VARCHAR(255),
    full_name       VARCHAR(255),
    login           VARCHAR(255),
    password        VARCHAR(255),
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS items
(
    item_id       BIGINT           NOT NULL,
    bid_increment DOUBLE PRECISION NOT NULL,
    buy_it_now    BOOLEAN          NOT NULL,
    description   VARCHAR(255),
    start_date    DATE,
    start_price   DOUBLE PRECISION NOT NULL,
    stop_date     DATE,
    title         VARCHAR(255),
    user_id       BIGINT,
    PRIMARY KEY (item_id)
);

CREATE TABLE IF NOT EXISTS bids
(
    bid_id    BIGINT           NOT NULL,
    bid_date  DATE,
    bid_value DOUBLE PRECISION NOT NULL,
    item_id   BIGINT,
    user_id   BIGINT,
    PRIMARY KEY (bid_id)
);

ALTER TABLE bids
    ADD CONSTRAINT fk_bids_users
        FOREIGN KEY (user_id)
            REFERENCES users (user_id);

ALTER TABLE bids
    ADD CONSTRAINT fk_bids_items
        FOREIGN KEY (item_id)
            REFERENCES items (item_id);

ALTER TABLE items
    ADD CONSTRAINT fk_items_users
        FOREIGN KEY (user_id)
            REFERENCES users (user_id);