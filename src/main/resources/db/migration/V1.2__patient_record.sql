CREATE TABLE patient_record (
    id int8 NOT NULL,
    patient_id BIGINT NOT NULL REFERENCES patient(id),
    doctor_id BIGINT NOT NULL REFERENCES doctor(id),
    date TIMESTAMP NULL,
    type_visit VARCHAR(255),
    reason_visit TEXT,
    treatment_made TEXT
);

CREATE SEQUENCE patient_record_sequence
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE