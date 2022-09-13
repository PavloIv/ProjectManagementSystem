ALTER TABLE projects
ADD COLUMN cost INTEGER;

UPDATE projects
 SET cost = (SELECT sum(salary)
	FROM developers_projects
	JOIN developers
	ON developers_projects.developer_id = developers.id
	GROUP BY project_id
    HAVING project_id = 1)
 WHERE id = 1;

UPDATE projects
 SET cost = (SELECT sum(salary)
	FROM developers_projects
	JOIN developers
	ON developers_projects.developer_id = developers.id
	GROUP BY project_id
    HAVING project_id = 2)
 WHERE id = 2;

UPDATE projects
 SET cost = (SELECT sum(salary)
	FROM developers_projects
	JOIN developers
	ON developers_projects.developer_id = developers.id
	GROUP BY project_id
    HAVING project_id = 3)
WHERE id = 3;

UPDATE projects
 SET cost = (SELECT sum(salary)
	FROM developers_projects
	JOIN developers
	ON developers_projects.developer_id = developers.id
	GROUP BY project_id
    HAVING project_id = 4)
 WHERE id = 4;

UPDATE projects
 SET cost = (SELECT sum(salary)
	FROM developers_projects
	JOIN developers
	ON developers_projects.developer_id = developers.id
	GROUP BY project_id
    HAVING project_id = 5)
 WHERE id = 5;

UPDATE projects
 SET cost = (SELECT sum(salary)
	FROM developers_projects
	JOIN developers
	ON developers_projects.developer_id = developers.id
	GROUP BY project_id
    HAVING project_id = 6)
 WHERE id = 6;

UPDATE projects
 SET cost = (SELECT sum(salary)
	FROM developers_projects
	JOIN developers
	ON developers_projects.developer_id = developers.id
	GROUP BY project_id
    HAVING project_id = 7)
 WHERE id = 7;

UPDATE projects
 SET cost = (SELECT sum(salary)
	FROM developers_projects
	JOIN developers
	ON developers_projects.developer_id = developers.id
	GROUP BY project_id
    HAVING project_id = 8)
 WHERE id = 8;

UPDATE projects
 SET cost = (SELECT sum(salary)
	FROM developers_projects
	JOIN developers
	ON developers_projects.developer_id = developers.id
	GROUP BY project_id
    HAVING project_id = 9)
 WHERE id = 9;

UPDATE projects
 SET cost = (SELECT sum(salary)
	FROM developers_projects
	JOIN developers
	ON developers_projects.developer_id = developers.id
	GROUP BY project_id
    HAVING project_id = 10)
 WHERE id = 10;

UPDATE projects
 SET cost = (SELECT sum(salary)
	FROM developers_projects
	JOIN developers
	ON developers_projects.developer_id = developers.id
	GROUP BY project_id
    HAVING project_id = 11)
 WHERE id = 11;

UPDATE projects
 SET cost = (SELECT sum(salary)
	FROM developers_projects
	JOIN developers
	ON developers_projects.developer_id = developers.id
	GROUP BY project_id
    HAVING project_id = 12)
 WHERE id = 12;

