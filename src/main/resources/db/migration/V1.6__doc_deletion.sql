alter table doctor_patient drop constraint doctor_patient_doctor_id_fkey;

alter table doctor_patient add constraint doctor_patient_doctor_id_fkey FOREIGN KEY (doctor_id) references doctor (id) on delete cascade;
