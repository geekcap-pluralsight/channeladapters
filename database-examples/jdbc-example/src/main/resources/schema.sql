-- Create the reservation table if it does not already exist
CREATE TABLE IF NOT EXISTS reservation (
    id   INTEGER      NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    status INTEGER,
    PRIMARY KEY (id)
);

-- Delete existing rows from the reservation table
DELETE FROM reservation;
