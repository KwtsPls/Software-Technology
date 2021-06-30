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
create table project(id int not null primary key auto_increment,title varchar(40),description varchar(200)
                    ,status int not null,date_finished date);

insert into project(title,description,status,date_finished) values('jete','just a dummy description',0,null);
insert into project(title,description,status,date_finished) values('app','just a dummy description',0,null);
insert into project(title,description,status,date_finished) values('app_new_4','just a dummy description',0,null);
insert into project(title,description,status,date_finished) values('MY NEW PROJECT','not much',0,null);


/*
-----------------------------------------------------------
Create table for developers for app and add some dummy data
-----------------------------------------------------------
*/
drop table if exists developer;
create table developer(user_id int,project_id int,role int,accepted int,
    constraint developer_pk
        primary key (user_id,project_id),
    constraint fk_developer_user_project1
        foreign key (project_id) references project (id),
    constraint fk_developer_user
        foreign key (user_id) references user (id)
);

insert into developer(user_id, project_id, role,accepted) values(2,1,1,1);
insert into developer(user_id, project_id, role,accepted) values(4,1,2,1);
insert into developer(user_id, project_id, role,accepted) values(3,2,1,1);
insert into developer(user_id, project_id, role,accepted) values(6,2,1,1);
insert into developer(user_id, project_id, role,accepted) values(1,4,1,1);


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

insert into sprint(id,project_id, title, status,dateFrom,dateTo) values(1,1,'sprint#1',1,'2021-06-10','2021-06-20');
insert into sprint(id,project_id, title, status,dateFrom,dateTo) values(2,1,'sprint#2',0,'2021-06-10','2021-06-20');
insert into sprint(id,project_id, title, status,dateFrom,dateTo) values(3,1,'sprint#3',0,'2021-06-10','2021-06-20');
insert into sprint(id,project_id, title, status,dateFrom,dateTo) values(4,1,'sprint#4',2,'2021-06-10','2021-06-20');
insert into sprint(id,project_id, title, status,dateFrom,dateTo) values(5,2,'sprint#1',1,'2021-06-10','2021-06-20');
insert into sprint(id,project_id, title, status,dateFrom,dateTo) values(6,2,'sprint#2',2,'2021-06-10','2021-06-20');
insert into sprint(id,project_id, title, status,dateFrom,dateTo) values(7,3,'sprint#1',1,'2021-06-10','2021-06-20');

/*
--------------------------------------------------------
Create table for story for app and add some dummy data
--------------------------------------------------------
*/
drop table if exists story;
create table story(id int not null,epic_id int not null,sprint_id int not null ,project_id int not null
                  ,title varchar(45) not null,description varchar(45),status int not null,
                   constraint story_pk
                       primary key (id,epic_id,sprint_id,project_id),
                   constraint fk_story_project
                       foreign key (project_id) references project (id),
                   constraint fk_story_epic
                        foreign key (epic_id) references epic (id)
);
/*
--------------------------------------------------------
Create table for story for app and add some dummy data
--------------------------------------------------------
*/
drop table if exists task;
create table task(id int not null,story_id int not null,epic_id int not null,sprint_id int not null ,project_id int not null
    ,title varchar(45) not null,description varchar(45),status int not null,
                   constraint task_pk
                       primary key (id,epic_id,sprint_id,project_id),
                   constraint fk_task_project
                       foreign key (project_id) references project (id),
                   constraint fk_task_epic
                       foreign key (epic_id) references epic (id),
                   constraint fk_task_story
                        foreign key (story_id) references story (id));



/*
--------------------------------------------------------
Create table for assignee for app and add some dummy data
--------------------------------------------------------
*/
create table assignee
(
    user_id int not null,
    project_id int not null,
    sprint_id int not null,
    epic_id int not null,
    story_id int not null,
    task_id int not null,
    constraint assignee_pk
        unique (user_id, project_id, sprint_id, story_id, task_id, epic_id)
);


/*
--------------------------------------------------------
Create table for wallet for app and add some dummy data
--------------------------------------------------------
*/
drop table if exists wallet;
create table wallet(id int not null primary key,card1 varchar(20),card2 varchar(20),card3 varchar(20)
                   ,subscription_starts date,subscription_ends date,
                   constraint fk_user_wallet
                    foreign key (id) references user (id));

/*
--------------------------------------------------------
Create table for payment for app and add some dummy data
--------------------------------------------------------
*/
drop table if exists payments;
create table payments(id int not null primary key AUTO_INCREMENT, user_id int not null,
    received_date date, received float,
    constraint fk_payments_user1
        foreign key (user_id) references user (id));
