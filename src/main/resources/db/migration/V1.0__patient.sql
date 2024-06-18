-- public.patient definition

-- Drop table

-- DROP TABLE public.patient;

CREATE TABLE public.patient (
	id int8 NOT NULL,
	"name" varchar(128) NULL,
	surname varchar(128) NULL,
	phone_number int4 NULL,
	ultima_visita timestamp NULL,
	email varchar(256) NULL,
	CONSTRAINT patients_pkey PRIMARY KEY (id)
);

-- public.patient_sequence definition

-- DROP SEQUENCE public.patient_sequence;

CREATE SEQUENCE public.patient_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;