ALTER TABLE projects
ADD COLUMN creation_date DATE;

UPDATE projects
SET creation_date = '2022-06-14'
WHERE id = 1;

UPDATE projects
SET creation_date = '2022-06-16'
WHERE id = 2;

UPDATE projects
SET creation_date = '2022-06-18'
WHERE id = 3;

UPDATE projects
SET creation_date = '2022-06-22'
WHERE id = 4;

UPDATE projects
SET creation_date = '2022-06-24'
WHERE id = 5;

UPDATE projects
SET creation_date = '2022-06-30'
WHERE id = 6;

UPDATE projects
SET creation_date = '2022-07-06'
WHERE id = 7;

UPDATE projects
SET creation_date = '2022-07-14'
WHERE id = 8;

UPDATE projects
SET creation_date = '2022-07-15'
WHERE id = 9;

UPDATE projects
SET creation_date = '2022-07-19'
WHERE id = 10;

UPDATE projects
SET creation_date = '2022-07-20'
WHERE id = 11;

UPDATE projects
SET creation_date = '2022-07-24'
WHERE id = 12;