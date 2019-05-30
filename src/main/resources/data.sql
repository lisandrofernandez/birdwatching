INSERT INTO bird (id, name, size, photo_url) VALUES (1, 'Falcon', 1, 'https://upload.wikimedia.org/wikipedia/commons/3/39/Brown-Falcon%2C-Vic%2C-3.1.2008.jpg');
INSERT INTO bird (id, name, size, photo_url) VALUES (2, 'Spoonbill', 2, 'https://en.wikipedia.org/wiki/File:Roseate_Spoonbill_-_Myakka_River_State_Park.jpg');
INSERT INTO bird (id, name, size, photo_url) VALUES (3, 'Goose', 1, 'https://commons.wikimedia.org/wiki/File:Domestic_Goose.jpg');
INSERT INTO bird (id, name, size, photo_url) VALUES (4, 'Great bustard', 2, 'https://upload.wikimedia.org/wikipedia/commons/9/90/Drop_f%C3%BAzat%C3%BD_%28Otis_tarda%29_%282416576086%29.jpg');

-- Falcon
INSERT INTO bird_color (id, bird_id, color) VALUES (1, 1, 0);
INSERT INTO bird_color (id, bird_id, color) VALUES (2, 1, 2);
INSERT INTO bird_color (id, bird_id, color) VALUES (3, 1, 4);
INSERT INTO bird_color (id, bird_id, color) VALUES (4, 1, 6);
-- Spoonbill
INSERT INTO bird_color (id, bird_id, color) VALUES (5, 2, 0);
INSERT INTO bird_color (id, bird_id, color) VALUES (6, 2, 2);
INSERT INTO bird_color (id, bird_id, color) VALUES (7, 2, 4);
INSERT INTO bird_color (id, bird_id, color) VALUES (8, 2, 7);
-- Goose
INSERT INTO bird_color (id, bird_id, color) VALUES (9, 3, 6);
-- Great bustard
INSERT INTO bird_color (id, bird_id, color) VALUES (10, 4, 0);
INSERT INTO bird_color (id, bird_id, color) VALUES (11, 4, 6);

INSERT INTO region (id, name) VALUES (1, 'Extremadura');
INSERT INTO region (id, name) VALUES (2, 'Andalusia');

INSERT INTO natural_reserve (id, name, region_id) VALUES (1, 'Monfragüe', 1);
INSERT INTO natural_reserve (id, name, region_id) VALUES (2, 'Doñana National Park', 2);
INSERT INTO natural_reserve (id, name, region_id) VALUES (3, 'Llanos de Caceres', 1);

INSERT INTO chance (id, bird_id, natural_reserve_id, month, probability) VALUES (1, 1, 1, 5, .4);
INSERT INTO chance (id, bird_id, natural_reserve_id, month, probability) VALUES (2, 1, 1, 6, .7);
INSERT INTO chance (id, bird_id, natural_reserve_id, month, probability) VALUES (3, 2, 2, 3, .8);
INSERT INTO chance (id, bird_id, natural_reserve_id, month, probability) VALUES (4, 2, 2, 4, .9);
INSERT INTO chance (id, bird_id, natural_reserve_id, month, probability) VALUES (5, 2, 2, 5, .35);
INSERT INTO chance (id, bird_id, natural_reserve_id, month, probability) VALUES (6, 2, 2, 6, .14);
INSERT INTO chance (id, bird_id, natural_reserve_id, month, probability) VALUES (7, 3, 2, 10, .9);
INSERT INTO chance (id, bird_id, natural_reserve_id, month, probability) VALUES (8, 4, 3, 3, .7);
INSERT INTO chance (id, bird_id, natural_reserve_id, month, probability) VALUES (9, 4, 3, 4, .6);
INSERT INTO chance (id, bird_id, natural_reserve_id, month, probability) VALUES (10, 4, 3, 5, .35);
INSERT INTO chance (id, bird_id, natural_reserve_id, month, probability) VALUES (11, 4, 3, 6, .15);
