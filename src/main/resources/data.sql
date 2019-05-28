INSERT INTO bird (id, name, size, photo_url) VALUES (1, 'Falcon', 1, 'https://upload.wikimedia.org/wikipedia/commons/3/39/Brown-Falcon%2C-Vic%2C-3.1.2008.jpg');

INSERT INTO bird_color (id, bird_id, color) VALUES (1, 1, 0);
INSERT INTO bird_color (id, bird_id, color) VALUES (2, 1, 2);
INSERT INTO bird_color (id, bird_id, color) VALUES (3, 1, 4);
INSERT INTO bird_color (id, bird_id, color) VALUES (4, 1, 6);

INSERT INTO region (id, name) VALUES (1, 'Extremadura');

INSERT INTO natural_reserve (id, name, region_id) VALUES (1, 'Monfragüe', 1);

INSERT INTO chance (id, bird_id, natural_reserve_id, month, probability) VALUES (1, 1, 1, 5, .4);
INSERT INTO chance (id, bird_id, natural_reserve_id, month, probability) VALUES (2, 1, 1, 6, .7);
