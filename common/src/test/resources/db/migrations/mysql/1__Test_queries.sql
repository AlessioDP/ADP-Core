-- MySQL Test schema
CREATE TABLE `<prefix>table` (
	`column1`		INTEGER PRIMARY KEY AUTO_INCREMENT,
	`column2`		VARCHAR(255) NOT NULL
);

SELECT *
	FROM `<prefix>table`;

-- Comment
-- Another comment
ALTER TABLE `<prefix>table`
	ADD `column3` INTEGER DEFAULT 0 NOT NULL;