DROP TABLE IF EXISTS user;
CREATE TABLE user(id INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(100),
    password VARCHAR(100),email VARCHAR(100),bio VARCHAR(200),location VARCHAR(100),status INT,pronouns VARCHAR(20),firstname VARCHAR(100), lastname VARCHAR(100) );

INSERT INTO user(username,password,email,bio,location,status,pronouns,firstname,lastname) VALUES('Adam647','aosdid1209j','adamj@test.com','Not much too say','USA,Illinois',1,'he/him','Adam','Jonhson');
INSERT INTO user(username,password,email,bio,location,status,pronouns,firstname,lastname) VALUES('TTa6','asfafgv578','trevorm@test.com','Coding for the masses...','USA,California',0,'he/him','Trevor','Martin');
INSERT INTO user(username,password,email,bio,location,status,pronouns,firstname,lastname) VALUES('mary_happy_j_99','asdkhf23asca','themarryjule@test.com','Happy to be here','Sweden',2,'they/them','Marry','Jules');
INSERT INTO user(username,password,email,bio,location,status,pronouns,firstname,lastname) VALUES('JuneO760','asdav12465fs','juneosborn@test.com','Badass','Canada,Ottawa',2,'she/her','June','Osborn');