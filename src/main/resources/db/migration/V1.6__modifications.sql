alter table public.patient
add column address varchar(256),
drop column status,
drop column first_admission;