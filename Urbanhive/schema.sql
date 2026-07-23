CREATE DATABASE IF NOT EXISTS urbanhive;
USE urbanhive;

CREATE TABLE IF NOT EXISTS flats (
  id INT PRIMARY KEY AUTO_INCREMENT,
  block_name VARCHAR(20) NOT NULL,
  flat_number VARCHAR(20) NOT NULL,
  owner_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  full_name VARCHAR(100) NOT NULL,
  email VARCHAR(120) NOT NULL UNIQUE,
  password VARCHAR(120) NOT NULL,
  role VARCHAR(20) NOT NULL,
  flat_id INT NULL,
  FOREIGN KEY (flat_id) REFERENCES flats(id)
);

CREATE TABLE IF NOT EXISTS bills (
  id INT PRIMARY KEY AUTO_INCREMENT,
  flat_id INT NOT NULL,
  bill_month VARCHAR(20) NOT NULL,
  amount DECIMAL(10, 2) NOT NULL,
  status VARCHAR(20) NOT NULL,
  due_date DATE NOT NULL,
  FOREIGN KEY (flat_id) REFERENCES flats(id)
);

CREATE TABLE IF NOT EXISTS complaints (
  id INT PRIMARY KEY AUTO_INCREMENT,
  resident_id INT NOT NULL,
  title VARCHAR(120) NOT NULL,
  description TEXT NOT NULL,
  status VARCHAR(20) NOT NULL,
  assigned_to VARCHAR(100),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (resident_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS notices (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(120) NOT NULL,
  content TEXT NOT NULL,
  posted_by INT NOT NULL,
  posted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (posted_by) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS visitor_logs (
  id INT PRIMARY KEY AUTO_INCREMENT,
  resident_id INT NULL,
  visitor_name VARCHAR(120) NOT NULL,
  purpose VARCHAR(120) NOT NULL,
  entry_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  exit_time TIMESTAMP NULL,
  approved_by_guard INT NULL,
  FOREIGN KEY (resident_id) REFERENCES users(id),
  FOREIGN KEY (approved_by_guard) REFERENCES users(id)
);

INSERT INTO flats (block_name, flat_number, owner_name)
SELECT 'A', '101', 'Aarav Sharma'
WHERE NOT EXISTS (SELECT 1 FROM flats WHERE block_name = 'A' AND flat_number = '101');

INSERT INTO users (full_name, email, password, role, flat_id)
SELECT 'Society Admin', 'admin@urbanhive.local', 'admin123', 'ADMIN', NULL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@urbanhive.local');

INSERT INTO users (full_name, email, password, role, flat_id)
SELECT 'Riya Resident', 'resident@urbanhive.local', 'resident123', 'RESIDENT',
  (SELECT id FROM flats WHERE block_name = 'A' AND flat_number = '101' LIMIT 1)
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'resident@urbanhive.local');

INSERT INTO users (full_name, email, password, role, flat_id)
SELECT 'Gate Guard', 'guard@urbanhive.local', 'guard123', 'GUARD', NULL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'guard@urbanhive.local');

INSERT INTO bills (flat_id, bill_month, amount, status, due_date)
SELECT
  (SELECT id FROM flats WHERE block_name = 'A' AND flat_number = '101' LIMIT 1),
  'April 2026',
  2500.00,
  'UNPAID',
  '2026-04-30'
WHERE NOT EXISTS (SELECT 1 FROM bills WHERE bill_month = 'April 2026');

INSERT INTO notices (title, content, posted_by)
SELECT
  'Water Tank Cleaning',
  'Water supply will be paused on Sunday from 10 AM to 1 PM for tank cleaning.',
  (SELECT id FROM users WHERE email = 'admin@urbanhive.local' LIMIT 1)
WHERE NOT EXISTS (SELECT 1 FROM notices WHERE title = 'Water Tank Cleaning');
