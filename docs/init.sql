
CREATE TABLE employee (
    employee_id BIGSERIAL PRIMARY KEY,
    employee_code VARCHAR(20) UNIQUE,
    full_name VARCHAR(100),
    email VARCHAR(100),
    role VARCHAR(50),
    department VARCHAR(50)
);

CREATE TABLE project (
    project_id BIGSERIAL PRIMARY KEY,
    project_code VARCHAR(20) UNIQUE,
    project_name VARCHAR(200),
    customer VARCHAR(100),
    status VARCHAR(20)
);

ALTER TABLE project 
ADD CONSTRAINT chk_project_status CHECK (status IN ('PLANNING', 'ACTIVE', 'COMPLETED'));

CREATE TABLE allocation (
    allocation_id BIGSERIAL PRIMARY KEY,
    employee_id BIGINT,
    project_id BIGINT,
    allocation_percent INTEGER,
    role_in_project VARCHAR(100),
    start_date DATE,
    end_date DATE
);
