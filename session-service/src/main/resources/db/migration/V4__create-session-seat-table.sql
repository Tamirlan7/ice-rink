CREATE TABLE t_session_seat
(
    ticket_id  BIGINT PRIMARY KEY,
    session_id BIGINT      NOT NULL REFERENCES t_session (id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX ix_session_seat_session ON t_session_seat (session_id);
