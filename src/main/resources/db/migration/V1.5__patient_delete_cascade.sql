alter table patientrecord drop constraint patientrecord_patient_id_fkey;

alter table patientrecord add constraint patientrecord_patient_id_fkey FOREIGN KEY (patient_id) references patient (id) on delete cascade;

