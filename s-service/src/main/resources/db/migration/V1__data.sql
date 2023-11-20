create table students
(
    record_book_number varchar(255) not null
        primary key,
    faculty            varchar(255) not null
        constraint students_faculty_check
            check ((faculty)::text = ANY
                   ((ARRAY ['ARTS'::character varying, 'CLASSICS'::character varying, 'COMMERCE'::character varying, 'ECONOMICS'::character varying, 'EDUCATION'::character varying, 'ENGINEERING'::character varying, 'HEALTH'::character varying, 'HUMANITIES'::character varying, 'IT'::character varying, 'LAW'::character varying, 'PHILOSOPHY'::character varying])::text[])),
    file_name          varchar(255),
    first_name         varchar(40)  not null,
    last_name          varchar(60)  not null,
    middle_name        varchar(40)  not null
);

INSERT INTO students (record_book_number, faculty, file_name, first_name, last_name, middle_name) VALUES ('2023003', 'IT', 'jb.png', 'Joseph', 'Robinette', 'Biden');
INSERT INTO students (record_book_number, faculty, file_name, first_name, last_name, middle_name) VALUES ('2023004', 'IT', 'vp.png', 'Vladimir', 'Vladimirovich', 'Putin');
