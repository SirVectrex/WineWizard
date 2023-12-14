-- Intial database values
-- wine(id, barcode_num, name, type, description)
INSERT INTO wine (name) VALUES ('Lugana');

-- address(id, city, house_number, postal_code, street)
INSERT INTO address (postal_code, city, street, house_number) VALUES ('93333', 'Bad Goegging', 'Winestreet', 6);

-- winery(id, name, address_id)
INSERT INTO winery (name, address_id, url_identifier) VALUES ('Schottis Winery', 1, '2520ff3c-8f72-11ee-b9d1-0242ac120002');


INSERT INTO user (login, password, email, active) values ('moadl', '{bcrypt}$2a$12$dEABxyOphtMwxWZGC9RpjeoE5lSU8ASwrYT0mgt4lqUeE/e.vx7jO', 'Martinwenzl19@gmail.com', 1);
INSERT INTO user (login, password, email, active) values ('anja', '{bcrypt}$2a$12$69GBDheB9KxZ4p4Zl9BLueq.C3ONV1VMxvx/cyoIVmzkgRziB9uFa', 'anja@email', 1);

INSERT INTO role (description) VALUES ( 'ADMIN');
INSERT INTO role (description) VALUES ( 'winery');
INSERT INTO role (description) VALUES ( 'wineWizard');

INSERT INTO authority (description) VALUES ( 'ADMIN_STATUS');
INSERT INTO authority (description) VALUES ( 'WINERY_STATUS');
INSERT INTO authority (description) VALUES ( 'WINEWIZARD_STATUS');


INSERT INTO userrole(iduser, idrole) VALUES (1,1);
INSERT INTO userrole(iduser, idrole) VALUES (1,3);
INSERT INTO userrole(iduser, idrole) VALUES (2,2);


INSERT INTO roleauthority(idrole, idauthority) VALUES (1,1);
INSERT INTO roleauthority(idrole, idauthority) VALUES (1,3);
INSERT INTO roleauthority(idrole, idauthority) VALUES (2,2);