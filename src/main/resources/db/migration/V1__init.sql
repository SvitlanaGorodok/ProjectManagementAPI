CREATE TABLE IF NOT EXISTS employee_levels
(
    id UUID CONSTRAINT employee_level_pk PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS positions
(
    id UUID CONSTRAINT position_pk PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS employees
(
    id UUID CONSTRAINT employee_pk PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    UNIQUE (email),
	position_id UUID REFERENCES positions(id) ON DELETE CASCADE,
	level_id UUID REFERENCES employee_levels(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS projects
(
    id UUID CONSTRAINT projects_pk PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
    UNIQUE (name),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS projects_employees_relation
(
    project_id UUID REFERENCES projects (id) ON DELETE CASCADE,
    employee_id UUID REFERENCES employees (id) ON DELETE CASCADE,
    CONSTRAINT project_employee_pk PRIMARY KEY (project_id, employee_id)
);


