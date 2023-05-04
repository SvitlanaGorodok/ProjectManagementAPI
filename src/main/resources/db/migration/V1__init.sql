CREATE TABLE IF NOT EXISTS employee_levels
(
    id SERIAL CONSTRAINT employee_level_pk PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS positions
(
    id SERIAL CONSTRAINT position_pk PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS employees
(
    id SERIAL CONSTRAINT employee_pk PRIMARY KEY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
	position_id INT REFERENCES positions(id) ON DELETE CASCADE,
	level_id INT REFERENCES employee_levels(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS projects
(
    id SERIAL CONSTRAINT projects_pk PRIMARY KEY,
	name VARCHAR(20) NOT NULL,
    UNIQUE (name),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS projects_employees_relation
(
    project_id INT REFERENCES projects (id) ON DELETE CASCADE,
    employee_id INT REFERENCES employees (id) ON DELETE CASCADE,
    CONSTRAINT project_employee_pk PRIMARY KEY (project_id, employee_id)
);


