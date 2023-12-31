-- Intial database values
-- wine(id, barcode_num, name, type, description)
INSERT INTO wine (name, type, ean, description, bookmarked) VALUES ('Lugana', 'dry white wine', '1234567890123', 'A crisp and refreshing white wine with citrus notes.', false);
INSERT INTO wine (name, type, ean, description, bookmarked) VALUES ('Château Margaux', 'red wine', '2345678901234', 'An elegant and complex Bordeaux blend with a velvety finish.', false);
INSERT INTO wine (name, type, ean, description, bookmarked) VALUES ('Prosecco Brut', 'sparkling wine', '3456789012345', 'Bubbly and lively, this Prosecco offers floral and fruity aromas.', false);
INSERT INTO wine (name, type, ean, description, bookmarked) VALUES ('Chardonnay Reserve', 'oaked white wine', '4567890123456', 'Full-bodied Chardonnay with hints of vanilla and butter.', false);
INSERT INTO wine (name, type, ean, description, bookmarked) VALUES ('Merlot Estate', 'red wine', '5678901234567', 'Smooth and medium-bodied Merlot with plum and cherry flavors.', false);
INSERT INTO wine (name, type, ean, description, bookmarked) VALUES ('Sauvignon Blanc', 'crisp white wine', '6789012345678', 'Zesty Sauvignon Blanc with tropical fruit and herbal undertones.', false);
INSERT INTO wine (name, type, ean, description, bookmarked) VALUES ('Cabernet Sauvignon', 'full-bodied red wine', '7890123456789', 'Bold and robust Cabernet with blackberry and cassis notes.', false);
INSERT INTO wine (name, type, ean, description, bookmarked) VALUES ('Pinot Grigio', 'light white wine', '8901234567890', 'Delicate Pinot Grigio featuring citrus and pear aromas.', false);
INSERT INTO wine (name, type, ean, description, bookmarked) VALUES ('Malbec Reserva', 'intense red wine', '9012345678901', 'Rich and intense Malbec with dark fruit and chocolate nuances.', false);
INSERT INTO wine (name, type, ean, description, bookmarked) VALUES ('Rosé de Provence', 'rosé wine', '0123456789012', 'A dry and crisp Provencal Rosé with strawberry and floral hints.', false);


-- address(id, city, house_number, postal_code, street)
INSERT INTO address (postal_code, city, street, house_number) VALUES ('93333', 'Bad Goegging', 'Winestreet', 6);

-- winery(id, name, address_id)
INSERT INTO winery (name, address_id, url_identifier) VALUES ('Schottis Winery', 1, '2520ff3c-8f72-11ee-b9d1-0242ac120002');


INSERT INTO user (login, password, email, phone, active) values ('moadl', '{bcrypt}$2a$12$dEABxyOphtMwxWZGC9RpjeoE5lSU8ASwrYT0mgt4lqUeE/e.vx7jO', 'Martinwenzl19@gmail.com', '+4915114962996',  1);
INSERT INTO user (login, password, email, phone, active) values ('flo', '{bcrypt}$2a$12$hLQA4zpjoHuJLWLrv757uuiXBlZNAjIvUFRmzFN8HXvOEX5.VbRxC', 'flo@email', '+4915114962996',  1);
INSERT INTO user (login, password, email, phone, active) values ('markus', '{bcrypt}$2a$12$OqgFS1OcCaGndtsGCSTYo.KiW3IpT9SdFDyoCZn/0YFxH/iW3ljSG', 'markus@email', '+4915114962996',  1);

INSERT INTO role (description) VALUES ( 'ADMIN');
INSERT INTO role (description) VALUES ( 'winery');
INSERT INTO role (description) VALUES ( 'wineWizard');

INSERT INTO authority (description) VALUES ( 'ADMIN_STATUS');
INSERT INTO authority (description) VALUES ( 'WINERY_STATUS');
INSERT INTO authority (description) VALUES ( 'WINEWIZARD_STATUS');


INSERT INTO userrole(iduser, idrole) VALUES (1,1);
INSERT INTO userrole(iduser, idrole) VALUES (2,1);
INSERT INTO userrole(iduser, idrole) VALUES (3,1);


INSERT INTO roleauthority(idrole, idauthority) VALUES (1,1);
INSERT INTO roleauthority(idrole, idauthority) VALUES (1,3);
INSERT INTO roleauthority(idrole, idauthority) VALUES (2,2);

INSERT INTO ratings (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (1, 1, 5, 5, 5);
INSERT INTO ratings (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (1, 2, 4, 5, 5);
INSERT INTO ratings (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (1, 3, 2, 3, 5);
INSERT INTO ratings (user_id, wine_id, design_rating, taste_rating, price_rating) VALUES (1, 4, 4, 5, 0);

INSERT INTO bookmarks (user_id, wine_id) VALUES (1,2);
INSERT INTO bookmarks (user_id, wine_id) VALUES (1,8);
INSERT INTO bookmarks (user_id, wine_id) VALUES (1,4);
INSERT INTO bookmarks (user_id, wine_id) VALUES (1,5);