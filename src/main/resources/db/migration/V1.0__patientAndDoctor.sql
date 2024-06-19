CREATE TABLE public.patient (
	id int8 NOT NULL,
	opd int8 NOT NULL,
	idp int8 NOT NULL,
	"name" varchar(128) NULL,
	surname varchar(128) NULL,
	phone_number int8 NULL,
	address varchar(128) NULL,
	email varchar(256) NULL,
	avatar varchar(128) NULL,
	blood_group int NOT NULL default 1,
	notes varchar(512) null,
	chronicPatient BOOLEAN DEFAULT FALSE,
	last_admission timestamp NULL,
	last_doctor_visited_id int8 NULL,
	CONSTRAINT patient_pkey PRIMARY KEY (id)
);

CREATE TABLE public.doctor (
	id int8 NOT NULL,
	"name" varchar(128) NULL,
	surname varchar(128) NULL,
	phone_number int8 NULL,
	address varchar(128) NULL,
	email varchar(256) NULL,
	avatar varchar(128) NULL,
	profession varchar(128) NULL,
	CONSTRAINT doctor_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE patient_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE doctor_sequence
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;