CREATE TABLE bird (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    size INT NOT NULL,
    photo_url VARCHAR(500),
    version INT DEFAULT 0 NOT NULL
    --creation_datetime DATETIME DEFAULT NOW() NOT NULL,
    --modification_datetime DATETIME DEFAULT NOW() NOT NULL,
);

CREATE TABLE bird_color (
    id IDENTITY PRIMARY KEY,
    bird_id INT8 NOT NULL,
    color INT NOT NULL,
    version INT DEFAULT 0 NOT NULL,
    --creation_datetime DATETIME DEFAULT NOW() NOT NULL,
    --modification_datetime DATETIME DEFAULT NOW() NOT NULL,
    FOREIGN KEY(bird_id) REFERENCES bird(id),
    UNIQUE KEY UNIQUE_BIRD_COLOR (bird_id, color)
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
    name VARCHAR(150) NOT NULL,
    region_id INT8 NOT NULL,
    version INT DEFAULT 0 NOT NULL,
    --creation_datetime DATETIME DEFAULT NOW() NOT NULL,
    --modification_datetime DATETIME DEFAULT NOW() NOT NULL,
    FOREIGN KEY(region_id) REFERENCES region(id)
);

CREATE TABLE chance (
    id IDENTITY PRIMARY KEY,
    bird_id INT8 NOT NULL,
    natural_reserve_id INT8 NOT NULL,
    month INT NOT NULL,
    probability DOUBLE NOT NULL,
    version INT DEFAULT 0 NOT NULL,
    --creation_datetime DATETIME DEFAULT NOW() NOT NULL,
    --modification_datetime DATETIME DEFAULT NOW() NOT NULL,
    FOREIGN KEY(bird_id) REFERENCES bird(id),
    FOREIGN KEY(natural_reserve_id) REFERENCES natural_reserve(id),
    UNIQUE KEY UNIQUE_CHANCE_BIRD_RESERVE_MONTH (bird_id, natural_reserve_id, month)
);
