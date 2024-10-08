-- DROP TABLE if exists t_client_user CASCADE ;
-- DROP TABLE if exists t_expense  CASCADE ;
-- DROP TABLE if exists t_category CASCADE ;

CREATE TABLE IF NOT EXISTS t_client_user (
                       c_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                       c_username VARCHAR(50) NOT NULL UNIQUE,
                       c_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS t_category (
                                          c_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                          c_user_id INT NOT NULL ,
                                          c_name VARCHAR(50) NOT NULL UNIQUE,
                                          c_description VARCHAR(255),
                                          FOREIGN KEY (c_user_id) REFERENCES t_client_user(c_id)
);

CREATE TABLE IF NOT EXISTS t_expense (
                          c_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                          c_client_user_id INT NOT NULL,
                          c_category_id INT,
                          c_amount DECIMAL(10, 2) NOT NULL,
                          c_description VARCHAR(255),
                          c_expense_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (c_client_user_id) REFERENCES t_client_user(c_id),
                          FOREIGN KEY (c_category_id) REFERENCES t_category(c_id)
);