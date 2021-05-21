drop table if exists categories cascade ;
create table categories (id bigserial, title varchar(255), primary key (id));
insert into categories (title) values ('ELECTRO'), ('FOOD');

drop table if exists product cascade ;
create table product (id bigserial,
                        title varchar (40),
                        category_id bigint,
                        coast integer,
                        primary key (id),
                        constraint fk_cat_id foreign key (category_id) references categories(id));



insert into product
( title, category_id, coast) values
('TV',1,50000),
('Phone',1,18000),
('PC',1,140000),
('milk',2,60),
('potato',2,20),
('bread',2,100),
('fridge',1,45000),
('mouse',1,1500),
('keyboard',1,2000),
('notebook',1,46000),
('water',2,30);


DROP TABLE IF EXISTS users;
CREATE TABLE users (
id                    bigserial,
phone                 VARCHAR(30) NOT NULL UNIQUE,
password              VARCHAR(80),
email                 VARCHAR(50) UNIQUE,
first_name            VARCHAR(50),
last_name             VARCHAR(50),
PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
id                    serial,
name                  VARCHAR(50) NOT NULL,
PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles (
user_id               INT NOT NULL,
role_id               INT NOT NULL,
PRIMARY KEY (user_id, role_id),
FOREIGN KEY (user_id)
    REFERENCES users (id),
FOREIGN KEY (role_id)
    REFERENCES roles (id)
);

INSERT INTO roles (name)
VALUES
('ROLE_CUSTOMER'), ('ROLE_MANAGER'), ('ROLE_ADMIN');

INSERT INTO users (phone, password, first_name, last_name, email)
VALUES
('11111111','$2y$12$63VUgjmV.BYfnbswWUqHpunAWzMqnb.JgrqA52GeWdR8GUhfF2Q12','Admin','Admin','admin@gmail.com'),
('22222222','$2a$10$orrC.ze.UKMJQMOqH4SgAewrgYJWC3wYXsnX8LrHIrG/s9Ao26bii', 'USER' , 'USER', 'user@gmail.com');


INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 3);

drop table if exists orders cascade ;
create table orders (id bigserial, user_id bigint, price numeric (8,2) not null, address varchar(255) not null, phone_number varchar(30) not null, primary key (id), constraint fk_user_id foreign key (user_id) references users (id));

drop table if exists orders_items cascade ;
create table order_items (id bigserial, order_id bigint, product_id bigint, quantity int,price numeric (8,2), primary key (id),
constraint fk_prod_id foreign key (product_id) references product(id),
constraint fk_order_id foreign key (order_id) references orders(id));




create table picture(id bigserial NOT NULL,
                        image_data  bytea not null,
                        image_name  VARCHAR(50) not null,
                        primary key (id));



DROP TABLE IF EXISTS product_picture;
CREATE TABLE product_picture (
product_id   INT NOT NULL,
picture_id   INT NOT NULL,
PRIMARY KEY (product_id, picture_id),
foreign key (product_id) REFERENCES product (id),
foreign key (picture_id) REFERENCES picture (id)
);




