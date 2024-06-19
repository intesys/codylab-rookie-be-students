CREATE TABLE public.doctor (
	id bigint NOT NULL,
	"name" varchar(128) NULL,
	surname varchar(128) NULL,
	email varchar(256) NULL,
	phone_number int NULL,
	address varchar(128) NULL,
	avatar varchar(128) NULL,
	profession varchar(128) NULL,
	CONSTRAINT doctor_pkey PRIMARY KEY (id)
);
CREATE table doctor_patients (
    doctor_id bigint not null references doctor (id),
    patient_id bigint not null references patient (id),
    CONSTRAINT doctor_patients_pkey PRIMARY KEY (doctor_id, patient_id)
);
CREATE SEQUENCE public.doctor_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
