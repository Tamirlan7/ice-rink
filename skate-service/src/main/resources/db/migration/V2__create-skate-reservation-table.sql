CREATE TABLE IF NOT EXISTS t_skate_reservation
(
    id             BIGSERIAL PRIMARY KEY,
    skate_id       BIGINT      NOT NULL REFERENCES t_skate (id),
    session_id     BIGINT      NOT NULL,
    ticket_id      BIGINT      NOT NULL,
    status         VARCHAR(20) NOT NULL,
    reserved_until TIMESTAMPTZ,
    created_at     TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX ux_skate_reservation_active
    ON t_skate_reservation (skate_id, session_id)
    WHERE status IN ('RESERVED', 'CONFIRMED');

CREATE INDEX ix_skate_reservation_ticket ON t_skate_reservation (ticket_id);
