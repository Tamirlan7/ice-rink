CREATE TABLE IF NOT EXISTS t_session
(
    id BIGSERIAL PRIMARY KEY,
    template_id INTEGER REFERENCES t_session_template (id),
    date DATE NOT NULL,
    start_time
)