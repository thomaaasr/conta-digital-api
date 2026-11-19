CREATE TABLE contas (
    id         BIGSERIAL PRIMARY KEY,
    titular    VARCHAR(120)   NOT NULL,
    saldo      NUMERIC(19, 2) NOT NULL DEFAULT 0 CHECK (saldo >= 0),
    criada_em  TIMESTAMPTZ    NOT NULL DEFAULT now()
);
