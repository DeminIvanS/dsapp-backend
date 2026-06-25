ALTER TABLE users RENAME COLUMN active TO is_active;
ALTER TABLE classes RENAME COLUMN cancelled TO is_cancelled;
ALTER TABLE attendance RENAME COLUMN present TO is_present;
ALTER TABLE charges RENAME COLUMN paid TO is_paid;
ALTER TABLE schedule_templates ALTER COLUMN day_of_week TYPE VARCHAR(20) USING day_of_week::VARCHAR(20);