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
	company_id varchar(50),
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
	primary key (id)
) engine=InnoDB;

create table cpn_company_contact (
	id binary(16) not null,
	company_id varchar(50),
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
	primary key (id)
) engine=InnoDB;

alter table cpn_company
	add constraint UK55qwlocdhyr6gpwcylv2ex7w6 unique (registration_number);

create index IDX96c89dbc4ia60snyl32hjovo2
	on cpn_company_address (company_id);

create index IDX45ma03tid96ric5aiycmibty4
	on cpn_company_contact (company_id);
