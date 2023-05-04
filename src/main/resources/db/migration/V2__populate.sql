INSERT INTO employee_levels ("name") VALUES ('Junior'), ('Middle'), ('Senior');
INSERT INTO positions ("name") VALUES ('Project Manager'), ('Developer'), ('QA'), ('DevOps');
INSERT INTO employees (first_name, last_name, position_id, level_id) VALUES ('First name', 'Last name', 1, 1);
INSERT INTO projects ("name", start_date, end_date) VALUES ('Project', '2023-01-01', '2023-01-31');
INSERT INTO projects_employees_relation (project_id, employee_id) VALUES (1,1);
