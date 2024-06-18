CREATE TABLE public.doctor (
	id int8 NOT NULL,
	"name" varchar(128) NULL,
	surname varchar(128) NULL,
	phone_number int4 NULL,
	firstAdmission timestamp NULL,
	firstAdmission timestamp NULL,
	email varchar(256) NULL,
	status int NOT NULL DEFAULT 1,
	patients
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