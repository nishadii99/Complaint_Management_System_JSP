CREATE DATABASE cms;
USE cms;

CREATE TABLE users (
                       user_id VARCHAR(255) PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       address VARCHAR(255),
                       mobile VARCHAR(15),
                       email VARCHAR(100),
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       department VARCHAR(100),
                       job_role VARCHAR(100)
);

CREATE TABLE complaints (
                            complaint_id VARCHAR(255) PRIMARY KEY,
                            user_id VARCHAR(255) NOT NULL,
                            title VARCHAR(100) NOT NULL,
                            description TEXT NOT NULL,
                            status ENUM('PENDING', 'IN_PROGRESS', 'RESOLVED') DEFAULT 'PENDING',
                            remarks TEXT,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
