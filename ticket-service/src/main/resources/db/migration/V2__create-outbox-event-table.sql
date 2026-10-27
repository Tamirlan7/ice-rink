CREATE TABLE t_outbox_event
(
    id         UUID PRIMARY KEY,
    topic      VARCHAR(100) NOT NULL,
    event_key  VARCHAR(100) NOT NULL,
    payload    TEXT         NOT NULL,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT now(),
    sent_at    TIMESTAMPTZ
);

CREATE INDEX ix_outbox_event
ON t_outbox_event (created_at)
WHERE sent_at IS NULL;
