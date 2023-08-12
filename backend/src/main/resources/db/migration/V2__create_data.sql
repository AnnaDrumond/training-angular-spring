 CREATE TABLE brand (
	id int8 NOT NULL,
	name VARCHAR(50) NOT NULL,
	source VARCHAR(30) NOT NULL,
	deleted_at TIMESTAMP DEFAULT NULL,
	CONSTRAINT brand_pkey PRIMARY KEY (id)
);

insert into brand(id, name, source, deleted_at) values (1, 'Coca-cola',  'Estados-Unidos', null);
insert into brand(id, name, source, deleted_at) values (2, 'Lipton Ice Tea',  'Escócia', null);
insert into brand(id, name, source, deleted_at) values (3, 'Compal',  'Portugal', null);
insert into brand(id, name, source, deleted_at) values (4, 'Sagres',  'Portugal', null);
insert into brand(id, name, source, deleted_at) values (5, 'Pepsi',  'Estados-Unidos', null);
insert into brand(id, name, source, deleted_at) values (6, 'Casal Garcia', 'Portugal', null);
insert into brand(id, name, source, deleted_at) values (7, 'Maguary', 'Brasil', null);

CREATE TABLE drink (
	id int8 NOT NULL,
	name VARCHAR(30) NOT NULL,
	unit_price DECIMAL(16,2) NOT NULL,
	volume_ml INT NOT NULL,
	type int2 NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL,
    brand_id BigInt NOT NULL,
    CONSTRAINT drink_pkey PRIMARY KEY (id),
	CONSTRAINT fk_drink FOREIGN KEY (brand_id) REFERENCES brand(id)
);
insert into drink(id, name, unit_price, volume_ml, type, created_at,         updated_at, deleted_at, brand_id)
           values (1, 'Zero', 2.44,       1500,     2,   current_timestamp,   null,        null,       1);


insert into drink(id, name, unit_price, volume_ml, type, created_at, updated_at,deleted_at, brand_id)
       values (2, 'Light', 2.50, 1600, 2,  current_timestamp, null, null, 1);
insert into drink(id, name, unit_price, volume_ml, type, created_at, updated_at,deleted_at, brand_id)
       values (3, 'Stevia', 2.50, 1600, 2, current_timestamp, null, current_timestamp, 1);
insert into drink(id, name, unit_price, volume_ml, type, created_at, updated_at,deleted_at, brand_id)
       values (4, 'Lemon', 2.50, 1600, 3, current_timestamp, null, null, 4);
insert into drink(id, name, unit_price, volume_ml, type, created_at, updated_at,deleted_at, brand_id)
       values (5, 'Dark', 2.44, 1500, 2, current_timestamp, null, current_timestamp, 1);
insert into drink(id, name, unit_price, volume_ml, type, created_at, updated_at,deleted_at, brand_id)
      values (6, 'Rose', 2.50, 1600, 2, current_timestamp, null, null, 6);
insert into drink(id, name, unit_price, volume_ml, type, created_at, updated_at,deleted_at, brand_id)
      values (7, 'Citrus', 2.50, 1600, 5, current_timestamp, null, null, 1);
insert into drink(id, name, unit_price, volume_ml, type, created_at, updated_at,deleted_at, brand_id)
      values (8, 'Orange', 2.50, 1600, 2, current_timestamp, null, null, 2);
insert into drink(id, name, unit_price, volume_ml, type, created_at, updated_at,deleted_at, brand_id)
      values (9, 'Fanta', 2.59, 1800, 3, current_timestamp, null, null, 2);
insert into drink(id, name, unit_price, volume_ml, type, created_at, updated_at,deleted_at, brand_id)
      values (10, 'Sprite', 2.70, 1600, 2,  current_timestamp, null, null, 6);
insert into drink(id, name, unit_price, volume_ml, type,  created_at, updated_at,deleted_at, brand_id)
      values (11, 'Sagres', 2.70, 1600, 4, current_timestamp, null, null, 3);
insert into drink(id, name, unit_price, volume_ml, type,  created_at, updated_at,deleted_at, brand_id)
      values (12, 'Jurupinga', 2.70, 1600, 2,  current_timestamp, null, null, 2);
insert into drink(id, name, unit_price, volume_ml, type,  created_at, updated_at,deleted_at, brand_id)
      values (13, 'Cachaça', 2.70, 1600, 4,  current_timestamp, null, null, 3);
insert into drink(id, name, unit_price, volume_ml, type,  created_at, updated_at,deleted_at, brand_id)
      values (14, 'Sumol', 2.70, 1600, 2,  current_timestamp, null, null, 2);
insert into drink(id, name, unit_price, volume_ml, type,  created_at, updated_at,deleted_at, brand_id)
      values (15, 'Caju', 2.70, 1600, 2,  current_timestamp, null, null, 7);
insert into drink(id, name, unit_price, volume_ml, type,  created_at, updated_at,deleted_at, brand_id)
      values (16, 'Manga', 2.70, 1600, 2,  current_timestamp, null, null, 7);
insert into drink(id, name, unit_price, volume_ml, type,  created_at, updated_at,deleted_at, brand_id)
      values (17, 'Limonada', 4.30, 1300, 4,  current_timestamp, null, null, 7);
insert into drink(id, name, unit_price, volume_ml, type,  created_at, updated_at,deleted_at, brand_id)
      values (18, 'Maçã', 3.70, 1200, 3,  current_timestamp, null, null, 7);
insert into drink(id, name, unit_price, volume_ml, type,  created_at, updated_at,deleted_at, brand_id)
      values (19, 'Melancia', 2.90, 600, 1,  current_timestamp, null, null, 7);