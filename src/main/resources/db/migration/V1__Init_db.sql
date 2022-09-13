CREATE TABLE developers(
 id   SERIAL PRIMARY KEY,
 name VARCHAR(100),
 age INTEGER,
 sex VARCHAR(10) , CHECK(sex = 'male' OR sex = 'female'));

CREATE TABLE skills (
 id SERIAL PRIMARY KEY,
 language VARCHAR(50),
 level VARCHAR(10)
 );

CREATE TABLE developers_skills(
 developer_id INTEGER,
 skill_id INTEGER,
 PRIMARY KEY(developer_id,skill_id),
 FOREIGN KEY (developer_id)
	REFERENCES developers(id),
 FOREIGN KEY (skill_id)
	REFERENCES skills(id)
 );

CREATE TABLE companies (
 id SERIAL PRIMARY KEY,
 Name VARCHAR(100),
 year_of_foundation INTEGER
);

CREATE TABLE customers (
 id SERIAL PRIMARY KEY,
 Name VARCHAR(100),
 Website VARCHAR(100)
);

CREATE TABLE projects (
 id SERIAL PRIMARY KEY,
 name VARCHAR(100),
 description VARCHAR(255),
 company_id INTEGER,
 customer_id INTEGER,
 FOREIGN KEY (company_id)
	REFERENCES companies(id),
 FOREIGN KEY (customer_id)
	REFERENCES customers(id)
 );

CREATE TABLE developers_projects(
 developer_id INTEGER,
 project_id INTEGER,
 PRIMARY KEY (developer_id,project_id),
 FOREIGN KEY (developer_id)
	REFERENCES developers(id),
 FOREIGN KEY (project_id)
	REFERENCES projects(id)
 );
