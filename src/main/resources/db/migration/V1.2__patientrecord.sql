CREATE SEQUENCE public.patient_record_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE public.patient_record (
	id int8 NOT NULL,
	patientId int8 NULL,
	doctorId varchar(128) NULL,
	date timestamp NULL,
	typeVisit varchar(128) NULL,
	reasonVisit varchar(128) NULL,
    treatmentMade varchar(128) NULL,
	CONSTRAINT patient_record_pkey PRIMARY KEY (id)
);
