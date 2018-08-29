create table cpn_company (
	id varchar(50) not null,
	address_detail varchar(50),
	address_postal_code varchar(10),
	address_street varchar(50),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	customer bit,
	email varchar(30),
	enabled bit,
	fax_number varchar(20),
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	mobile_phone_number varchar(20),
	name varchar(50),
	outsourcing bit,
	registration_number varchar(20),
	representative varchar(20),
	supplier bit,
	telephone_number varchar(20),
	primary key (id)
) engine=InnoDB;

alter table cpn_company
	add constraint CPN_COMPANY_REGISTRATION_NUMBER_IDX unique (registration_number);
