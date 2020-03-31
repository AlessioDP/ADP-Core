-- H2 Test schema
CREATE TABLE IF NOT EXISTS ${table} (
	`column1`		INTEGER AUTO_INCREMENT PRIMARY KEY,
	`column2`		VARCHAR(25) NOT NULL
);

SELECT *
	FROM ${table};

-- Comment
-- Another comment
ALTER TABLE ${table}
	ADD `column3` INTEGER DEFAULT 0 NOT NULL;