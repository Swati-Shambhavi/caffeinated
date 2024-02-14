
-- Insert dummy data into the address table
INSERT INTO address (address1, address2, city, state, pin_code, country, created_at)
VALUES
  ('A/47,303', 'SP Sukhobristi', 'Kolkata', 'West Bengal', 700135, 'India', CURRENT_TIMESTAMP),
  ('A/32,303', 'SP Sukhobristi', 'Kolkata', 'West Bengal', 700135, 'India', CURRENT_TIMESTAMP),
  ('Nawab Kothi', 'Digha Ghat', 'Patna', 'Bihar', 800011, 'India', CURRENT_TIMESTAMP);

-- Insert dummy data into the users table
INSERT INTO users (email, role, name, mobile_number, address_id, created_at)
VALUES
  ('regina@example.com', 'ROLE_USER', 'Regina Phalange', '1234567890', 1, CURRENT_TIMESTAMP),
  ('swati@example.com', 'ROLE_ADMIN', 'Swati Shambhavi', '9879993210', 2, CURRENT_TIMESTAMP),
  ('rishu@example.com', 'ROLE_USER', 'Rishu', '1112223333', 3, CURRENT_TIMESTAMP);

