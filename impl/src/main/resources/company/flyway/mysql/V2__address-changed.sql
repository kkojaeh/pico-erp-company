ALTER TABLE cpn_company_address ADD represented bit(1) DEFAULT 1 NULL;
ALTER TABLE cpn_company_address CHANGE mobile_phone_number fax_number varchar(20);
