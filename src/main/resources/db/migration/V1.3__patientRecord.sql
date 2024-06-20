CREATE TABLE patientrecord(
    id bigint NOT NULL primary key,
    patient_id bigint NOT NULL REFERENCES patient(id),
    date TIMESTAMP NULL,
    typevisit VARCHAR(255),
    reasonvisit TEXT,
    treatmentmade TEXT
);



CREATE SEQUENCE patient_record_sequence
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;