-- public.patient definition

-- Drop table

-- DROP TABLE public.patient;

CREATE TABLE public.patient (
	id int8 NOT NULL,
	last_admission timestamp NULL,
	"name" varchar(128) NULL,
	surname varchar(128) NULL,
	email varchar(256) NULL,
	phone_number int8 NULL,
	CONSTRAINT patient_pkey PRIMARY KEY (id)
);

-- public.patient_sequence definition

-- DROP SEQUENCE public.patient_sequence;

CREATE SEQUENCE patient_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;