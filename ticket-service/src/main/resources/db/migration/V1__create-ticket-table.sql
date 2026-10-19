CREATE TABLE IF NOT EXISTS t_ticket
(
    id               BIGSERIAL PRIMARY KEY,
    user_id          BIGINT                              NOT NULL,
    session_id       BIGINT                              NOT NULl,
    skate_id         BIGINT,
    price            NUMERIC(10, 2)                      NOT NULL,
    status           VARCHAR(20)                         NOT NULL,
    expires_at       TIMESTAMPTZ,
    pricing_category VARCHAR(20)                         NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL

    CONSTRAINT chk_ticket_status CHECK ( status IN ('PENDING', 'PAID', 'CANCELLED', 'EXPIRED') )
);

CREATE INDEX IF NOT EXISTS ix_ticket_pending_expires
    ON t_ticket (expires_at)
    WHERE status = 'PENDING';
