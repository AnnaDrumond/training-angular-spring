CREATE TABLE developer (
	id int8 NOT NULL,
	name varchar(255) NOT NULL,
	age bigint NOT NULL,
	CONSTRAINT developer_pkey PRIMARY KEY (id)
);

insert into developer(id, name, age) values (1,  'Luís Arêde', 50);
insert into developer(id, name, age) values (2,  'Nuno Gonçalves', 18);