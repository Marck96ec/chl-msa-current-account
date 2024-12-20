CREATE TABLE IF NOT EXISTS chl_account (
    account_number SERIAL PRIMARY KEY,
    person_id INT NOT NULL,
    account_type VARCHAR(255) NOT NULL,
    initial_balance DOUBLE PRECISION NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS chl_movement (
    id SERIAL PRIMARY KEY,
    account_number INT NOT NULL REFERENCES chl_account(account_number),
    date_movement DATE NOT NULL,
    movement_type VARCHAR(255) NOT NULL,
    value_movement DOUBLE PRECISION NOT NULL,
    balance DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);