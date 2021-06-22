/*
-------------------------------------------------------
Create table for users for app and add some dummy data
-------------------------------------------------------
*/
drop table if exists user;
create table user(id int primary key auto_increment, username varchar(100),
    password varchar(100),email varchar(100),bio varchar(200),location varchar(100),status int,pronouns varchar(20),firstname varchar(100), lastname varchar(100) );

insert into user(username,password,email,bio,location,status,pronouns,firstname,lastname) values('Adam647','aosdid1209j','adamj@test.com','Not much too say','USA,Illinois',1,'he/him','Adam','Jonhson');
insert into user(username,password,email,bio,location,status,pronouns,firstname,lastname) values('TTa6','asfafgv578','trevorm@test.com','Coding for the masses...','USA,California',0,'he/him','Trevor','Martin');
insert into user(username,password,email,bio,location,status,pronouns,firstname,lastname) values('mary_happy_j_99','asdkhf23asca','themarryjule@test.com','Happy to be here','Sweden',2,'they/them','Marry','Jules');
insert into user(username,password,email,bio,location,status,pronouns,firstname,lastname) values('JuneO760','asdav12465fs','juneosborn@test.com','Badass','Canada,Ottawa',2,'she/her','June','Osborn');


/*
----------------------------------------------------------
Create table for projects for app and add some dummy data
----------------------------------------------------------
*/
drop table if exists project;
create table project(id int not null primary key auto_increment,title varchar(40),description varchar(200));

insert into project(title,description) values('jete','just a dummy description');
insert into project(title,description) values('app','just a dummy description');
insert into project(title,description) values('app_new_4','just a dummy description');
insert into project(title,description) values('MY NEW PROJECT','not much');


/*
-----------------------------------------------------------
Create table for developers for app and add some dummy data
-----------------------------------------------------------
*/
drop table if exists developer;
create table developer(id int,project_id int,role int,
    constraint developer_pk
        primary key (id,project_id),
    constraint fk_developer_user_project1
        foreign key (project_id) references project (id)
);

insert into developer(id, project_id, role) values(2,1,1);
insert into developer(id, project_id, role) values(4,1,2);
insert into developer(id, project_id, role) values(3,2,1);
insert into developer(id, project_id, role) values(6,2,1);
insert into developer(id, project_id, role) values(1,4,1);


/*
-----------------------------------------------------------
Create table for epic for app and add some dummy data
-----------------------------------------------------------
*/
drop table if exists epic;
create table epic(id int not null auto_increment,project_id int,status int,description varchar(200),title varchar(45),
    constraint epic_pk
      primary key (id,project_id),
    constraint fk_epic_project1
      foreign key (project_id) references project (id)
);

insert into epic(project_id, status, description, title) values(1,2,'No Current Description','New Feature#1');
insert into epic(project_id, status, description, title) values(1,1,'No Current Description','New Feature#2');
insert into epic(project_id, status, description, title) values(2,1,'No Current Description','New Feature#1');
insert into epic(project_id, status, description, title) values(3,0,'No Current Description','New Feature#1');


/*
--------------------------------------------------------
Create table for sprints for app and add some dummy data
--------------------------------------------------------
*/
drop table if exists sprint;
create table sprint
(id int not null, project_id int not null, title varchar(45) not null, status int not null,dateFrom date,dateTo date,
    constraint sprint_pk
        primary key (project_id, id),
    constraint fk_sprint_project1
        foreign key (project_id) references project (id)
);

insert into sprint(id,project_id, title, status,dateFrom,dateTo) values(1,1,'sprint#1',1,'10-06-2021','20-06-2021');
insert into sprint(id,project_id, title, status,dateFrom,dateTo) values(2,1,'sprint#2',0,'10-06-2021','20-06-2021');
insert into sprint(id,project_id, title, status,dateFrom,dateTo) values(3,1,'sprint#3',0,'10-06-2021','20-06-2021');
insert into sprint(id,project_id, title, status,dateFrom,dateTo) values(4,1,'sprint#4',2,'10-06-2021','20-06-2021');
insert into sprint(id,project_id, title, status,dateFrom,dateTo) values(5,2,'sprint#1',1,'10-06-2021','20-06-2021');
insert into sprint(id,project_id, title, status,dateFrom,dateTo) values(6,2,'sprint#2',2,'10-06-2021','20-06-2021');
insert into sprint(id,project_id, title, status,dateFrom,dateTo) values(7,3,'sprint#1',1,'10-06-2021','20-06-2021');