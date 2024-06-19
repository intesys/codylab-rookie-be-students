CREATE TABLE doctor_patient (
    doctor_id bigint not null references doctor(id),
    patient_id bigint not null references patient (id),
    CONSTRAINT doctor_patients_pkey PRIMARY KEY (doctor_id, patient_id)
);