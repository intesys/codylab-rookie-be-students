CREATE TABLE public.patient_record(
	id bigint primary key NOT NULL,
	patient_id bigint NOT NULL references patient(id),
	doctor_id bigint NOT NULL references doctor(id),
	reason_visit varchar(128),
	type_visit varchar(128),
	treatment_made varchar(128),
    date timestamp NULL
);
    CREATE SEQUENCE public.patient_record_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
