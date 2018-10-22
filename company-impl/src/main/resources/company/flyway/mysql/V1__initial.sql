create table cpn_company (
	id varchar(50) not null,
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	customer bit,
	enabled bit,
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	name varchar(50),
	outsourcing bit,
	registration_number varchar(20),
	representative varchar(20),
	supplier bit,
	primary key (id)
) engine=InnoDB;

create table cpn_company_address (
	id binary(16) not null,
	address_detail varchar(50),
	address_postal_code varchar(10),
	address_street varchar(50),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	enabled bit,
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	mobile_phone_number varchar(20),
	name varchar(50),
	telephone_number varchar(20),
	company_id varchar(50),
	primary key (id)
) engine=InnoDB;

create table cpn_company_contact (
	id binary(16) not null,
	email varchar(60),
	fax_number varchar(40),
	mobile_phone_number varchar(40),
	name varchar(50),
	telephone_number varchar(40),
	created_by_id varchar(50),
	created_by_name varchar(50),
	created_date datetime,
	enabled bit,
	last_modified_by_id varchar(50),
	last_modified_by_name varchar(50),
	last_modified_date datetime,
	company_id varchar(50),
	primary key (id)
) engine=InnoDB;

alter table cpn_company
	add constraint CPN_COMPANY_REGISTRATION_NUMBER_IDX unique (registration_number);

alter table cpn_company_address
	add constraint FKod6xgi03i0q8utifd7xrnmsg7 foreign key (company_id)
	references cpn_company (id);

alter table cpn_company_contact
	add constraint FK26dx6s4m8q5eexs6fd9q7jj8s foreign key (company_id)
	references cpn_company (id);
