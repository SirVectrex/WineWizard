-- Intial database values
-- winery(id, name, address_id)
INSERT INTO winery (id, winery_name, url_identifier, winery_owner_id) VALUES (1, 'Sepps Winery', '2520ff3c-8f72-11ee-b9d1-0242ac120002', 4);
INSERT INTO winery (id, winery_name, url_identifier, winery_owner_id) VALUES (2, 'Lenas Winery', '2520ff3c-8f72-11ee-b9d1-0249ac120005', 7);
INSERT INTO winery (id, winery_name, url_identifier, winery_owner_id) VALUES (3, 'Professors Winery', '2520ff3c-8f72-11ee-b9d1-0249ac120012', 13);


-- wine(id, barcode_num, name, type, description)
INSERT INTO wine (name, type, ean, description, bookmarked, winery_id) VALUES ('Lugana', 'dry white wine', '1234567890123', 'A crisp and refreshing white wine with citrus notes.', false, 1);
INSERT INTO wine (name, type, ean, description, bookmarked, winery_id) VALUES ('Château Margaux', 'red wine', '2345678901234', 'An elegant and complex Bordeaux blend with a velvety finish.', false, 1);
INSERT INTO wine (name, type, ean, description, bookmarked, winery_id) VALUES ('Prosecco Brut', 'sparkling wine', '3456789012345', 'Bubbly and lively, this Prosecco offers floral and fruity aromas.', false, 1);
INSERT INTO wine (name, type, ean, description, bookmarked, winery_id) VALUES ('Chardonnay Reserve', 'oaked white wine', '4567890123456', 'Full-bodied Chardonnay with hints of vanilla and butter.', false, 1);
INSERT INTO wine (name, type, ean, description, bookmarked, winery_id) VALUES ('Merlot Estate', 'red wine', '5678901234567', 'Smooth and medium-bodied Merlot with plum and cherry flavors.', false, 2);
INSERT INTO wine (name, type, ean, description, bookmarked, winery_id) VALUES ('Sauvignon Blanc', 'crisp white wine', '6789012345678', 'Zesty Sauvignon Blanc with tropical fruit and herbal undertones.', false, 2);
INSERT INTO wine (name, type, ean, description, bookmarked, winery_id) VALUES ('Cabernet Sauvignon', 'full-bodied red wine', '7890123456789', 'Bold and robust Cabernet with blackberry and cassis notes.', false, 2);
INSERT INTO wine (name, type, ean, description, bookmarked, winery_id) VALUES ('Pinot Grigio', 'light white wine', '8901234567890', 'Delicate Pinot Grigio featuring citrus and pear aromas.', false, 1);
INSERT INTO wine (name, type, ean, description, bookmarked, winery_id) VALUES ('Malbec Reserva', 'intense red wine', '9012345678901', 'Rich and intense Malbec with dark fruit and chocolate nuances.', false, 1);
INSERT INTO wine (name, type, ean, description, bookmarked, winery_id) VALUES ('Rosé de Provence', 'rosé wine', '0123456789012', 'A dry and crisp Provencal Rosé with strawberry and floral hints.', false, 1);

INSERT INTO zipcodes (zip_code, country_code, city, state) VALUES (93333, 'DE', 'Neistod', 'Bayern');
INSERT INTO zipcodes (zip_code, country_code, city, state) VALUES (93499, 'DE', 'Zandt', 'Bayern');
INSERT INTO zipcodes (zip_code, country_code, city, state) VALUES (93051, 'DE', 'Regensburg', 'Bayern');


INSERT INTO user (id, login, password, email, phone, active, zip_code, verified, personal_profile_id) values (1, 'moadl', '{bcrypt}$2a$12$dEABxyOphtMwxWZGC9RpjeoE5lSU8ASwrYT0mgt4lqUeE/e.vx7jO', 'Martinwenzl19@gmail.com', '+4915114962996',  1, 93499, true, '222cff3c-8f72-11ee-b9d1-0249ac120rw05');
INSERT INTO user (id,login, password, email, phone, active, zip_code, verified, personal_profile_id) values (2,'flo', '{bcrypt}$2a$12$hLQA4zpjoHuJLWLrv757uuiXBlZNAjIvUFRmzFN8HXvOEX5.VbRxC', 'flo@email', '+4915114962996',  1, 93333, true, '222cff3c-8f72-11ee-b9c1-0249ac180rw05');
INSERT INTO user (id,login, password, email, phone, active, zip_code, verified, personal_profile_id) values (3, 'markus', '{bcrypt}$2a$12$OqgFS1OcCaGndtsGCSTYo.KiW3IpT9SdFDyoCZn/0YFxH/iW3ljSG', 'markus@email', '+4915114962996',  1, 93333, true, '222cff3c-8f72-11ee-b9d1-0249ac320rw05');
INSERT INTO user (id,login, password, email, phone, active, zip_code, verified, personal_profile_id) values (4, 'sepp', '{bcrypt}$2a$12$dEABxyOphtMwxWZGC9RpjeoE5lSU8ASwrYT0mgt4lqUeE/e.vx7jO', 'Martinwenzl191@gmail.com', '+4915114962996',  1, 93499, true, '222cff3c-8f72-11de-b9d1-0249ac120rw05');
INSERT INTO user (id,login, password, email, phone, active, zip_code, verified, personal_profile_id) values (5, 'hans', '{bcrypt}$2a$12$hLQA4zpjoHuJLWLrv757uuiXBlZNAjIvUFRmzFN8HXvOEX5.VbRxC', 'flo3@email', '+4915114962996',  1, 93499, true, '222cff3c-8f72-11ee-b9d1-0249ac14drw05');
INSERT INTO user (id,login, password, email, phone, active, zip_code, verified, personal_profile_id) values (6, 'anna', '{bcrypt}$2a$12$OqgFS1OcCaGndtsGCSTYo.KiW3IpT9SdFDyoCZn/0YFxH/iW3ljSG', 'markus3@email', '+4915114962996',  1, 93499, true, '222cff3c-8f72-11ee-b2d1-0249ac120rw05');
INSERT INTO user (id, login, password, email, phone, active, zip_code, verified, personal_profile_id) values (7, 'lena', '{bcrypt}$2a$12$dEABxyOphtMwxWZGC9RpjeoE5lSU8ASwrYT0mgt4lqUeE/e.vx7jO', 'Martinwenzl192@gmail.com', '+4915114962996',  1, 93499, true, '222cff3c-8f82-11ee-bkd1-0249ac120rw05');
INSERT INTO user (id,login, password, email, phone, active, zip_code, verified, personal_profile_id) values (8, 'Sigi', '{bcrypt}$2a$12$hLQA4zpjoHuJLWLrv757uuiXBlZNAjIvUFRmzFN8HXvOEX5.VbRxC', 'flo2@email', '+4915114962996',  1, 93499, true, '222cff3c-8f72-11ee-b9d1-0249ac12okw05');
INSERT INTO user (id,login, password, email, phone, active, zip_code, verified, personal_profile_id) values (9, 'Thomas', '{bcrypt}$2a$12$OqgFS1OcCaGndtsGCSTYo.KiW3IpT9SdFDyoCZn/0YFxH/iW3ljSG', 'markus2@email', '+4915114962996',  1, 93499, true, '222cff3c-8fk2-11ee-b9d1-0i49ac1200w05');
INSERT INTO user (id,login, password, email, phone, active, zip_code, verified, personal_profile_id) values (10, 'PetersWinery', '{bcrypt}$2a$12$dEABxyOphtMwxWZGC9RpjeoE5lSU8ASwrYT0mgt4lqUeE/e.vx7jO', 'petermachtwein@peter.de', '+4915114962996',  1, 93499, true, '222c0f3c-8fü2-11ee-b9d1-0249ah120rw05');
INSERT INTO user (id,login, password, email, phone, active, zip_code, verified, personal_profile_id) values (11, 'Professor_Admin', '{bcrypt}$2a$10$/ar/1d1eTbdnX4UqLUGOzuw910H3beBPu62EO.vaTC.aLOl5wjwu6', 'alixandre.santana@oth-regensburg.de', '+4915114962996',  1, 93051, true, '222c0f3c-8fü2-11ee-b9d1-0249ah120rw05');
INSERT INTO user (id,login, password, email, phone, active, zip_code, verified, personal_profile_id) values (12, 'Professor_WineWizard', '{bcrypt}$2a$10$/ar/1d1eTbdnX4UqLUGOzuw910H3beBPu62EO.vaTC.aLOl5wjwu6', 'alixandre.santana@oth-regensburg.de', '+4915114962996',  1, 93051, true, '222c0f3c-8fü2-11ee-b9d1-0249ah120rw05');
INSERT INTO user (id,login, password, email, phone, active, zip_code, verified, personal_profile_id) values (13, 'Professor_Winery', '{bcrypt}$2a$10$/ar/1d1eTbdnX4UqLUGOzuw910H3beBPu62EO.vaTC.aLOl5wjwu6', 'alixandre.santana@oth-regensburg.de', '+4915114962996',  1, 93051, true, '222c0f3c-8fü2-11ee-b9d1-0249ah120rw05');


INSERT INTO role (description, id) VALUES ( 'ADMIN', 1);
INSERT INTO role (description, id) VALUES ( 'wineWizard', 2);
INSERT INTO role (description, id) VALUES ( 'winery', 3);

INSERT INTO authority (description) VALUES ( 'ADMIN_STATUS');
INSERT INTO authority (description) VALUES ( 'WINERY_STATUS');
INSERT INTO authority (description) VALUES ( 'WINEWIZARD_STATUS');


INSERT INTO userrole(iduser, idrole) VALUES (1,1);
INSERT INTO userrole(iduser, idrole) VALUES (2,1);
INSERT INTO userrole(iduser, idrole) VALUES (3,1);
INSERT INTO userrole(iduser, idrole) VALUES (4,3);
INSERT INTO userrole(iduser, idrole) VALUES (5,2);
INSERT INTO userrole(iduser, idrole) VALUES (6,2);
INSERT INTO userrole(iduser, idrole) VALUES (7,3);
INSERT INTO userrole(iduser, idrole) VALUES (8,2);
INSERT INTO userrole(iduser, idrole) VALUES (9,2);
INSERT INTO userrole(iduser, idrole) VALUES (10,2);
INSERT INTO userrole(iduser, idrole) VALUES (11,1);
INSERT INTO userrole(iduser, idrole) VALUES (12,2);
INSERT INTO userrole(iduser, idrole) VALUES (13,3);


INSERT INTO roleauthority(idrole, idauthority) VALUES (1,1);
INSERT INTO roleauthority(idrole, idauthority) VALUES (1,3);
INSERT INTO roleauthority(idrole, idauthority) VALUES (2,3);
INSERT INTO roleauthority(idrole, idauthority) VALUES (3,2);

INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (1, 1, 5, 5, 5);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (1, 2, 4, 5, 5);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (1, 3, 2, 3, 5);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (1, 4, 4, 5, 0);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (3, 2, 3, 5, 1);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (1, 1, 3, 5, 4);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (3, 9, 2, 3, 2);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (1, 6, 3, 1, 3);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (1, 2, 5, 2, 4);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (1, 4, 5, 1, 1);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (2, 5, 5, 3, 1);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (1, 9, 3, 3, 2);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (3, 8, 3, 4, 2);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (2, 7, 2, 5, 1);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (3, 10, 5, 4, 4);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (1, 3, 3, 1, 2);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (3, 1, 2, 2, 4);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (1, 7, 3, 1, 3);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (3, 1, 1, 1, 2);
-- User 12 Ratings
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (12, 5, 4, 3, 2);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (12, 8, 2, 4, 5);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (12, 3, 3, 2, 4);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (12, 6, 5, 5, 3);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (12, 10, 1, 2, 4);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (12, 4, 4, 3, 1);
-- User 11 Ratings
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (11, 7, 3, 4, 2);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (11, 2, 4, 2, 5);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (11, 9, 2, 5, 3);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (11, 1, 5, 3, 4);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (11, 8, 1, 4, 1);
INSERT INTO rating (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (11, 5, 3, 5, 2);



INSERT INTO bookmarks (user_id, wine_id) VALUES (1,2);
INSERT INTO bookmarks (user_id, wine_id) VALUES (1,8);
INSERT INTO bookmarks (user_id, wine_id) VALUES (1,4);
INSERT INTO bookmarks (user_id, wine_id) VALUES (1,5);