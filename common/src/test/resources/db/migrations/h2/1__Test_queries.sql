-- H2 Test schema
CREATE TABLE `<prefix>table` (
	`column1`		INTEGER AUTO_INCREMENT PRIMARY KEY,
	`column2`		VARCHAR(25) NOT NULL
);

SELECT *
	FROM `<prefix>table`;

-- Comment
-- Another comment
ALTER TABLE `<prefix>table`
	ADD `column3` INTEGER DEFAULT 0 NOT NULL;