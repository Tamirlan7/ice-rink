CREATE TABLE IF NOT EXISTS t_user
(
    id           SERIAL PRIMARY KEY,
    phone_number VARCHAR(15) UNIQUE NOT NULL,
    password     VARCHAR(255)       NOT NULL,
    role         VARCHAR(5)         NOT NULL DEFAULT 'USER',
    created_at   TIMESTAMP                   DEFAULT CURRENT_TIMESTAMP NOT NULL

    CONSTRAINT chk_valid_role CHECK ( role IN ('USER', 'ADMIN') )
)
