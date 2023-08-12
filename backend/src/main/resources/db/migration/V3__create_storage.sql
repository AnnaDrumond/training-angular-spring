 CREATE TABLE storage (
	id int8 NOT NULL PRIMARY KEY,
	code VARCHAR(20) NOT NULL,
	location VARCHAR(20) NOT NULL,
	capacity INT NOT NULL,
	CONSTRAINT unique_code_location UNIQUE (code, location)
);

insert into storage (id,code,location,capacity) values (1, '20','Madureira', 25);
insert into storage (id,code,location,capacity) values (2, '28','Centro', 20);
insert into storage (id,code,location,capacity) values (3, '31','Copacabana', 25);
insert into storage (id,code,location,capacity) values (4, '25','Bonsucesso', 20);
insert into storage (id,code,location,capacity) values (5, '30','Madureira', 35);
insert into storage (id,code,location,capacity) values (6, '35','Copacabana', 35);
insert into storage (id,code,location,capacity) values (7, '40','Urca', 10);


 CREATE TABLE storage_drink (
    quantity INT NOT NULL CHECK (quantity>=1),
    storage_id int8 NOT NULL,
    drink_id int8 NOT NULL,
	CONSTRAINT pk_storage_drink PRIMARY KEY (storage_id,drink_id),
	CONSTRAINT fk_drink FOREIGN KEY (drink_id) REFERENCES drink(id),
	CONSTRAINT fk_storage FOREIGN KEY (storage_id) REFERENCES storage(id)
);

insert into storage_drink (quantity,storage_id,drink_id) values (2, 1, 4);
insert into storage_drink (quantity,storage_id,drink_id) values (1, 1, 10);
insert into storage_drink (quantity,storage_id,drink_id) values (3, 1, 8);
insert into storage_drink (quantity,storage_id,drink_id) values (1, 1, 16);
insert into storage_drink (quantity,storage_id,drink_id) values (3, 1, 6);
insert into storage_drink (quantity,storage_id,drink_id) values (2, 2, 4);
insert into storage_drink (quantity,storage_id,drink_id) values (1, 2, 12);
insert into storage_drink (quantity,storage_id,drink_id) values (3, 2, 7);
insert into storage_drink (quantity,storage_id,drink_id) values (1, 2, 15);
insert into storage_drink (quantity,storage_id,drink_id) values (3, 2, 2);
insert into storage_drink (quantity,storage_id,drink_id) values (2, 3, 4);
insert into storage_drink (quantity,storage_id,drink_id) values (1, 3, 11);
insert into storage_drink (quantity,storage_id,drink_id) values (1, 3, 8);
insert into storage_drink (quantity,storage_id,drink_id) values (1, 3, 16);
insert into storage_drink (quantity,storage_id,drink_id) values (3, 3, 13);
insert into storage_drink (quantity,storage_id,drink_id) values (1, 3, 9);
insert into storage_drink (quantity,storage_id,drink_id) values (3, 3, 2);
insert into storage_drink (quantity,storage_id,drink_id) values (2, 4, 7);
insert into storage_drink (quantity,storage_id,drink_id) values (1, 4, 11);
insert into storage_drink (quantity,storage_id,drink_id) values (1, 4, 14);
insert into storage_drink (quantity,storage_id,drink_id) values (2, 5, 7);
insert into storage_drink (quantity,storage_id,drink_id) values (8, 5, 13);
insert into storage_drink (quantity,storage_id,drink_id) values (1, 5, 11);
insert into storage_drink (quantity,storage_id,drink_id) values (4, 5, 16);
insert into storage_drink (quantity,storage_id,drink_id) values (1, 5, 9);
insert into storage_drink (quantity,storage_id,drink_id) values (2, 6, 7);
insert into storage_drink (quantity,storage_id,drink_id) values (8, 6, 14);
insert into storage_drink (quantity,storage_id,drink_id) values (4, 6, 15);
insert into storage_drink (quantity,storage_id,drink_id) values (1, 6, 8);
insert into storage_drink (quantity,storage_id,drink_id) values (2, 7, 2);
insert into storage_drink (quantity,storage_id,drink_id) values (1, 7, 12);
insert into storage_drink (quantity,storage_id,drink_id) values (4, 7, 13);
insert into storage_drink (quantity,storage_id,drink_id) values (2, 7, 15);