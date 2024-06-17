--alter TABLE doctor add latest_patients int NOT NULL default 1

CREATE TABLE doctor_patient (
    doctor_id bigint NOT NULL REFERENCES doctor (id),
    patient_id bigint NOT NULL REFERENCES patient (id),
    CONSTRAINT doctor_patient_pkey PRIMARY KEY (doctor_id, patient_id)
);