insert into customer (first_name, last_name, date_of_birth, email_address) values('Elon', 'Musk', PARSEDATETIME('1976-07-26','yyyy-MM-dd'), 'elon.must@testing.com');
insert into customer (first_name, last_name, date_of_birth, email_address) values('Tim', 'Cook', PARSEDATETIME('1966-10-17','yyyy-MM-dd'), 'tim.cook@testing.com');
insert into address (post_code,state, street_name, street_number, unit_number, suburb, address_type, customer_id) values (2060, 'NSW', 'Pitt Street', '20', '2G','North Ryde', 'Residential', 1);
insert into address (post_code,state, street_name, street_number, unit_number, suburb, address_type, customer_id) values (2000, 'NSW', 'Pitt Street', '100A', '1','Sydney', 'Work', 1);
insert into address (post_code,state, street_name, street_number, unit_number, suburb, address_type, customer_id) values (3002, 'VIC', 'Queen Street', '26', null,'St Albans', 'Residential', 2);
insert into address (post_code,state, street_name, street_number, unit_number, suburb, address_type, customer_id) values (3000, 'VIC', 'Philip Street', '16', '2','Melbourne', 'Work', 2);
