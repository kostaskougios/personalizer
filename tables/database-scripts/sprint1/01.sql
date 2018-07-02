create table directory_status(id int primary key, path text not null, status smallint not null);

create unique index IX_directory_status_path on directory_status(path);
