-- Intial database values
-- wine(id, barcode_num, name, type, description)
INSERT INTO wine (name) VALUES ('Lugana');

-- address(id, city, house_number, postal_code, street)
INSERT INTO address (postal_code, city, street, house_number) VALUES ('93333', 'Bad Goegging', 'Winestreet', 6);

-- winery(id, name, address_id)
INSERT INTO winery (name, address_id) VALUES ('Schottis Winery', 1);