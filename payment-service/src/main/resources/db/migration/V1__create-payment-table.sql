CREATE TABLE IF NOT EXISTS t_payment
(
    id                  BIGSERIAL PRIMARY KEY,
    ticket_id           BIGINT              NOT NULL,
    idempotency_key     UUID UNIQUE         NOT NULL,
    provider            VARCHAR(20)         NOT NULL,
    provider_payment_id VARCHAR(100) UNIQUE NOT NULL,

    amount              NUMERIC(10, 2)      NOT NULL,
    currency            VARCHAR(3)          NOT NULL DEFAULT 'KZT',
    status              VARCHAR(20)         NOT NULL DEFAULT 'PENDING',
    created_at          TIMESTAMP           NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP           NOT NULL DEFAULT NOW()

    CONSTRAINT chk_payment_status CHECK (status IN ('PENDING', 'SUCCEEDED', 'FAILED', 'CANCELED', 'REFUNDED'))
    CONSTRAINT chk_payment_provider CHECK (provider IN ('HALYK', 'KASPI', 'MOCK')),
    CONSTRAINT chk_payment_amount CHECK (amount > 0)
);

CREATE OR REPLACE FUNCTION set_updated_at()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_payment_updated_at
    BEFORE UPDATE ON t_payment
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();
