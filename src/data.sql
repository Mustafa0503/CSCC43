USE c43;

-- Drop tables if they exist
DROP TABLE IF EXISTS NounPhrases;
DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS listings;
DROP TABLE IF EXISTS users;

-- Create the users table
CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       address VARCHAR(200),
                       date_of_birth DATE,
                       occupation VARCHAR(100),
                       social_insurance_number VARCHAR(20),
                       credit_card_number VARCHAR(20),
                       user_type ENUM('renter', 'host') NOT NULL
);

-- Create the listings table
CREATE TABLE listings (
                          listing_id INT AUTO_INCREMENT PRIMARY KEY,
                          type ENUM('full house', 'apartment', 'room') NOT NULL,
                          latitude DECIMAL(10, 6),
                          longitude DECIMAL(10, 6),
                          address VARCHAR(200),
                          postal_code VARCHAR(10),
                          city VARCHAR(100),
                          country VARCHAR(100),
                          amenities VARCHAR(200),
                          price DECIMAL(10, 2)
);

CREATE TABLE Comments (
                          comment_id INT AUTO_INCREMENT PRIMARY KEY,
                          listing_id INT NOT NULL,
                          description TEXT NOT NULL,
                          rating INT NOT NULL  CHECK (rating >= 1 AND rating <= 5),
                          timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (listing_id) REFERENCES listings(listing_id),

);

CREATE TABLE NounPhrases (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             listing_id INT NOT NULL,
                             noun_phrase TEXT NOT NULL,
                             count INT NOT NULL,
                             FOREIGN KEY (listing_id) REFERENCES listings(listing_id)
);

-- Insert sample data into the users table
INSERT INTO users (name, address, date_of_birth, occupation, social_insurance_number, credit_card_number, user_type)
VALUES
    ('John Doe', '123 Main St, Toronto', '1990-05-15', 'Software Engineer', '123456789', '1234567890123456', 'renter'),
    ('Jane Smith', '456 Oak Ave, Vancouver', '1988-09-22', 'Marketing Manager', '987654321', '9876543210987654', 'host'),
    ('Michael Lee', '789 Elm Rd, Montreal', '1995-02-10', 'Student', '654321987', '6543219876543210', 'renter');

-- Insert sample data into the listings table
INSERT INTO listings (type, latitude, longitude, address, postal_code, city, country, amenities, price)
VALUES
    ('apartment', 43.654260, -79.383190, '789 Yonge St, Toronto', 'M4W 1J7', 'Toronto', 'Canada', 'Wi-Fi, Kitchen, TV', 150.00),
    ('room', 49.282730, -123.120735, '456 Granville St, Vancouver', 'V6C 1T2', 'Vancouver', 'Canada', 'Wi-Fi, Laundry', 80.00),
    ('full house', 45.508888, -73.561668, '123 Maple Ave, Montreal', 'H3A 0A1', 'Montreal', 'Canada', 'Pool, Parking, Garden', 250.00);

INSERT INTO Comments (listing_id, description, rating)
VALUES
    (1, 'The place was amazing and had a great view.', 5),
    (1, 'Enjoyed my stay here, highly recommended!', 4),
    (2, 'Nice location and spacious rooms.', 4),
    (3, 'Not a pleasant experience, room was dirty.', 2),
    (4, 'Beautiful interior and comfortable beds.', 5);

-- Commit the changes
COMMIT;
