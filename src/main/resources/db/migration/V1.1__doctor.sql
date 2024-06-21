CREATE TABLE public.doctor (
	id int8 NOT NULL,
	"name" varchar(128) NULL,
	surname varchar(128) NULL,
	phonenumber int8 NULL,
	address varchar(128) NULL,
	email varchar(128) NULL,
	avatar varchar(128) NULL,
    profession varchar(128) NULL,
	CONSTRAINT doctor_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.doctor_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;