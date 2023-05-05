INSERT INTO employee_levels (id, "name") VALUES ('4e3c27be-76de-496a-bed2-fb2dcb71ab7a', 'Junior'),
												('072f378d-a7a8-44db-856a-4044668dfbe3','Middle'),
												('e9072bc9-9402-45eb-8e9a-cc088692879c','Senior');


INSERT INTO positions (id, "name") VALUES ('e814e6c8-64f4-4c3a-97be-cde232852686','Project Manager'),
											('ead31a24-efb5-4ceb-a3be-89e2c8ac9d02','Developer'),
											('bd7e6382-8fb1-4fd7-9eeb-8c2a051c53ff','QA'),
											('4e720329-ce31-49bb-b713-0da9e6becab0','DevOps');


INSERT INTO employees (id, first_name, last_name, email, position_id, level_id)
VALUES ('a8dd994d-c7c4-4bfb-8ed8-3e1a7a691ee1', 'Taras', 'Shevchenko', 'tshevchenko@gmail.com',
		'ead31a24-efb5-4ceb-a3be-89e2c8ac9d02', '4e3c27be-76de-496a-bed2-fb2dcb71ab7a');

INSERT INTO projects (id, "name", start_date, end_date)
VALUES ('72cf247a-ed44-4c92-bcaa-457bb4d7c011', 'Kobzar', '2023-01-01', '2023-01-31');

INSERT INTO projects_employees_relation (project_id, employee_id)
VALUES ('72cf247a-ed44-4c92-bcaa-457bb4d7c011','a8dd994d-c7c4-4bfb-8ed8-3e1a7a691ee1');
