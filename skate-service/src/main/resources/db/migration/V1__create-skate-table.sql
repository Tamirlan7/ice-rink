CREATE TABLE IF NOT EXISTS t_skate
(
    id                    SERIAL PRIMARY KEY,
    skate_identity_number BIGINT UNIQUE NOT NULL,
    size                  SMALLINT      NOT NULL,
    sex                   VARCHAR(6)    NOT NULL,
    is_available          BOOLEAN       NOT NULL DEFAULT TRUE,

    CONSTRAINT chk_valid_sex CHECK ( sex IN ('MALE', 'FEMALE') )
)
