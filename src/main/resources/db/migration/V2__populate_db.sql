INSERT INTO developers("name",age,sex) VALUES ('Pavlo', 30,'male'),
('Max', 31,'male'),
('Julia', 31,'female'),
('Natalia', 28,'female'),
('Svitlana', 27,'female'),
('Andriy', 33,'male'),
('Oleksiy', 36,'male');

SELECT* FROM developers;

INSERT INTO skills("language","level") VALUES ('Java','Junior'),
('Java','Middle'),
('Java','Senior'),
('C++','Junior'),
('C++','Middle'),
('C++','Senior'),
('C#','Junior'),
('C#','Middle'),
('C#','Senior'),
('JS','Junior'),
('JS','Middle'),
('JS','Senior'),
('Python','Junior'),
('Python','Middle'),
('Python','Senior');

SELECT* FROM skills;

INSERT INTO companies("name",year_of_foundation) VALUES ('ELITEX',1992),
('SoftServe',1997),
('EPAM',2005),
('GlobalLogic',2000),
('Luxoft',2002),
('Ciklum',2002);

SELECT * FROM companies;

INSERT INTO customers("name",website) VALUES ('Rozetka','https://rozetka.com.ua/'),
('Foxtrot','https://www.foxtrot.com.ua/'),
('Comfy','https://comfy.ua'),
('Allo','https://allo.ua/'),
('Moyo','https://www.moyo.ua/');

INSERT INTO projects("name",description,company_id,customer_id) VALUES ('Website','create new website for customer',1,1),
('MobileApp','create mobile application',3,1),
('Game','create mobile game for customer',3,2),
('Database','create database for customer buisness logic',2,2),
('Software platform','Building a new software platform or program',2,3),
('Security ','developing a security patch for existing software',4,3),
('Data Management','Developing a backup and restoration strategy',5,3),
('Network System Upgrades','Expanding the network INTO a new area in an office building',4,4),
('Website Upgrades','Upgrades  website',6,5),
('MobileApp Upgrades','Upgrades mobile application',6,5),
('Game Upgrades','Upgrades mobile game for customer',2,5),
('Database Security','security patch for database for customer buisness logic',1,5);

SELECT * FROM projects;

INSERT INTO developers_skills(developer_id,skill_id) VALUES (1,2),
(1,10),
(2,1),
(3,1),
(3,7),
(4,10),
(5,1),
(6,13),
(7,4);

INSERT INTO developers_projects(developer_id,project_id) VALUES (1,2),
(1,3),
(1,4),
(1,6),
(1,8),
(1,10),
(2,1),
(2,5),
(2,7),
(2,9),
(2,11),
(3,12),
(3,1),
(4,2),
(4,3),
(5,2),
(5,11),
(6,1),
(6,2),
(6,3),
(6,7),
(7,4),
(7,5),
(7,6),
(7,7),
(7,8),
(1,12),
(6,10),
(5,9);
