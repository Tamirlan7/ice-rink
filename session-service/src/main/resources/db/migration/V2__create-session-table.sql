CREATE TABLE IF NOT EXISTS t_session
(
    id          BIGSERIAL PRIMARY KEY,
    template_id INTEGER REFERENCES t_session_template (id),
    date        DATE NOT NULL,
    start_time  TIME NOT NULL,
    end_time    TIME NOT NULL,
    allowed_people_quantity SMALLINT NOT NULL,
    currently_people_quantity SMALLINT NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
    created_at TIMESTAMP NOT NULL DEFAULT now(),

    CONSTRAINT chk_session_time CHECK (end_time > start_time),
    CONSTRAINT chk_session_quantity CHECK (allowed_people_quantity > 0),
    CONSTRAINT chk_session_current_quantity CHECK (
        currently_people_quantity >= 0
            AND currently_people_quantity <= allowed_people_quantity
        ),
    CONSTRAINT chk_session_status CHECK (status IN ('SCHEDULED', 'CANCELLED', 'COMPLETED')),
    CONSTRAINT uq_session_date_start UNIQUE (date, start_time)
)