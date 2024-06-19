alter table doctor_patients drop constraint doctor_patients_doctor_id_fkey;

alter table doctor_patients add constraint doctor_patients_doctor_id_fkey FOREIGN KEY (doctor_id) references doctor (id) on delete cascade;