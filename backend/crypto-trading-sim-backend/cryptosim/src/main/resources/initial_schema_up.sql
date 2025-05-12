BEGIN TRANSACTION;
CREATE TABLE  IF NOT EXISTS account (
    id UUID PRIMARY KEY,
    balance DECIMAL(20, 2) NOT NULL,
    email VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS holdings (
    id UUID PRIMARY KEY,
    crypto VARCHAR(10) NOT NULL ,
    quantity DECIMAL(20, 8) NOT NULL,
    total_value DECIMAL(20, 2) NOT NULL,
    account_id UUID NOT NULL REFERENCES account(id) ON DELETE CASCADE
);

CREATE TYPE  transaction_type AS ENUM('SELL','BUY');

CREATE TABLE IF NOT EXISTS transactions (
    id UUID PRIMARY KEY,
    type transaction_type NOT NULL,
    crypto_symbol VARCHAR(30) NOT NULL,
    quantity DECIMAL(20, 8) NOT NULL,
    unit_price DECIMAL(20, 8) NOT NULL,
    profit_loss DECIMAL(20, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    account_id UUID NOT NULL REFERENCES account(id) ON DELETE SET NULL
);

COMMIT;