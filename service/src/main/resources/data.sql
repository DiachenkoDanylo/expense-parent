-- Insert users
INSERT INTO t_client_user (c_username) VALUES
                                                  ('John_Arrow'),
                                                  ('Oleg_Skripka');

INSERT INTO t_category (c_name, c_description) VALUES
                                               ('Groceries', 'Expenses for groceries and food items'),
                                               ('Entertainment', 'Expenses for movies, concerts, etc.'),
                                               ('Utilities', 'Expenses for utilities like electricity, water, etc.');

-- Insert expenses
INSERT INTO t_expense (c_client_user_id,c_category_id, c_amount, c_description, c_expense_date) VALUES
                                                                                   (1, 1,  50.75, 'Weekly groceries', '2024-05-21 10:00:00'),
                                                                                   (2, 2, 15.00, 'Movie ticket', '2024-05-20 20:00:00'),
                                                                                   (2, 3,  100.00, 'Electricity bill', '2024-05-19 08:00:00');