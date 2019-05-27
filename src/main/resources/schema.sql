CREATE TABLE bird (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    size INT NOT NULL,
    photo_url VARCHAR(500),
    version INT DEFAULT 0 NOT NULL
    --creation_datetime DATETIME DEFAULT NOW() NOT NULL,
    --modification_datetime DATETIME DEFAULT NOW() NOT NULL,
);

CREATE TABLE region (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    version INT DEFAULT 0 NOT NULL
    --creation_datetime DATETIME DEFAULT NOW() NOT NULL,
    --modification_datetime DATETIME DEFAULT NOW() NOT NULL,
);

CREATE TABLE natural_reserve (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    region_id INT8 NOT NULL,
    version INT DEFAULT 0 NOT NULL,
    --creation_datetime DATETIME DEFAULT NOW() NOT NULL,
    --modification_datetime DATETIME DEFAULT NOW() NOT NULL,
    FOREIGN KEY(region_id) REFERENCES region(id)
);
