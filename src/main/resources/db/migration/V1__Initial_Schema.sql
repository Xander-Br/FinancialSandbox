-- src/main/resources/db/migration/V1__Initial_Schema.sql

-- Create the stock_ticks table
CREATE TABLE stock_ticks
(
    timestamp TIMESTAMPTZ NOT NULL,
    symbol VARCHAR(10)    NOT NULL,
    price  DECIMAL(19, 4) NOT NULL,
    volume BIGINT         NOT NULL,

    PRIMARY KEY (timestamp, symbol)
);

CREATE INDEX IF NOT EXISTS stock_ticks_symbol_idx ON stock_ticks (symbol);
