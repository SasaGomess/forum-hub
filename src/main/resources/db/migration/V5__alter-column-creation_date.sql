ALTER TABLE topics
CHANGE COLUMN creationDate creation_date DATETIME NOT NULL;

ALTER TABLE responses
CHANGE COLUMN creationDate creation_date DATETIME NOT NULL;