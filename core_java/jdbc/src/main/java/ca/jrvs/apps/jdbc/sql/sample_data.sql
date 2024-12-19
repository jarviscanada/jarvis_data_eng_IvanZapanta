INSERT INTO quote (symbol, open, high, low, price, volume, latest_trading_day, previous_close, change, change_percent, timestamp)
VALUES
    ('TSLA', 174.35, 176.89, 173.56, 175.50, 125000000, '2024-11-29', 174.80, 0.70, '0.40%', NOW()),
    ('GOOGL', 145.20, 148.00, 144.10, 146.85, 98000000, '2024-11-29', 145.60, 1.25, '0.86%', NOW());

INSERT INTO position (symbol, number_of_shares, value_paid)
VALUES
('TSLA', 100, 14500.00),
('GOOGL', 50, 137500.00);