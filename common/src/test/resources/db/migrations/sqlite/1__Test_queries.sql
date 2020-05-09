-- SQLite Test schema
CREATE TABLE `<prefix>table` (
	`column1`		INTEGER PRIMARY KEY AUTOINCREMENT,
	`column2`		VARCHAR NOT NULL
);

SELECT *
	FROM `<prefix>table`;

-- Comment
-- Another comment
ALTER TABLE `<prefix>table`
	ADD `column3` INTEGER DEFAULT 0 NOT NULL;