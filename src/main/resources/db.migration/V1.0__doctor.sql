CREATE TABLE public.doctor (
	id int8 NOT NULL,
	"name" varchar(128) NULL,
	surname varchar(128) NULL,
	email varchar(256) NULL,
	phonenumber varchar NULL,
	profession varchar(256) NULL,
	avatar varchar(128) NULL,
	address varchar(128) NULL,
	CONSTRAINT doctor_pkey PRIMARY KEY (id)
);
CREATE SEQUENCE public.doctor_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;