alter table patientrecord drop constraint patientrecord_doctor_id_fkey;

alter table patientrecord add constraint patientrecord_doctor_id_fkey FOREIGN KEY (doctor_id) references doctor (id) on delete cascade;
