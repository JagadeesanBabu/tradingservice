CREATE TABLE trading_data (
	id uuid NOT NULL,
	closed_price numeric(19,2) NULL,
	high_price numeric(19,2) NULL,
	"index" varchar(255) NULL,
	low_price numeric(19,2) NULL,
	no_of_shares_traded int4 NULL,
	open_price numeric(19,2) NULL,
	trade_date timestamp NULL,
	turnover numeric(19,2) NULL,
	CONSTRAINT trading_data_pkey PRIMARY KEY (id)
);