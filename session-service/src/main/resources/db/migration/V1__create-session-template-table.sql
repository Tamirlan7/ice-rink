CREATE TABLE IF NOT EXISTS t_session_template
(
    id                      SERIAL PRIMARY KEY,
    start_time              TIME        NOT NULL,
    end_time                TIME        NOT NULL,
    allowed_people_quantity SMALLINT    NOT NULL DEFAULT 300,
    day_of_week             VARCHAR(20) NOT NULL,
    is_active               BOOLEAN     NOT NULL DEFAULT TRUE,

    CONSTRAINT chk_session_template_time CHECK ( end_time > start_time),
    CONSTRAINT chk_session_template_quantity CHECK ( allowed_people_quantity > 0 ),
    CONSTRAINT uq_template_day_start UNIQUE (day_of_week, start_time),
    CONSTRAINT chk_session_template_day CHECK ( day_of_week IN
                                                ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY',
                                                 'SUNDAY') )
)
