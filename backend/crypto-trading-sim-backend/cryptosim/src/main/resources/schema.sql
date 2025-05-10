
CREATE TABLE  IF NOT EXISTS account (
    id UUID PRIMARY KEY,
    balance DECIMAL(20, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS holdings (
    id UUID PRIMARY KEY,
    crypto_symbol VARCHAR(10) NOT NULL ,
    quantity DECIMAL(20, 8) NOT NULL,
    unit_price DECIMAL(20, 2) NOT NULL,
    account_id UUID NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE IF NOT EXISTS transactions (
    id UUID PRIMARY KEY,
    type VARCHAR(4),
    crypto_symbol VARCHAR(30),
    quantity DECIMAL(20, 8),
    unit_price DECIMAL(20, 8),
    total_price DECIMAL(20, 2),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);