CREATE TABLE IF NOT EXISTS t_ticket
(
    id               BIGSERIAL PRIMARY KEY,
    user_id          BIGINT                              NOT NULL,
    session_id       BIGINT                              NOT NULl,
    price            NUMERIC(2, 10)                      NOT NULL,
    pricing_category VARCHAR(20)                         NOT NULL,
    ice_skate_id     BIGINT                              NOT NULL,

    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
)
