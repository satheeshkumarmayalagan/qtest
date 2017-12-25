
create table customer
(
   customer_id integer auto_increment,
   first_name varchar(100) not null,
   last_name varchar(100) not null,
   date_of_birth date not null,
   email_address varchar(200) not null,
   primary key(customer_id)
);

create table address
(
   address_id integer auto_increment,
   customer_id integer null,
   post_code integer not null,
   state varchar(3) not null,
   street_name varchar(100) not null,
   street_number varchar(10),
   unit_number varchar(20),
   suburb varchar(50) not null,
   address_type varchar(20),
   primary key(address_id),
   foreign key (customer_id) references customer(customer_id)
);