CREATE TABLE public.patient (
	id bigint NOT NULL,
	"name" varchar(128) NULL,
	surname varchar(128) NULL,
	email varchar(256) NULL,
	phone_number int NULL,
	first_admission timestamp NULL,
	last_admission timestamp NULL,
	status int NOT NULL DEFAULT 1,
	CONSTRAINT patient_pkey PRIMARY KEY (id)
);
    CREATE SEQUENCE public.patient_sequence
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
