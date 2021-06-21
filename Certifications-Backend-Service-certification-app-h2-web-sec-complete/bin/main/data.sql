insert into Certification(id,name,url, price) values (10000,'My certification','cert@ibm.com',200.50);
insert into certification(id,name,url, price, createdBy, createdDate, lastModifiedBy, lastModifiedDate, version) values (10001,'Java 11','https://oracle.com', 200.50, 'Thats me', sysdate, 'Thats me', sysdate, 0);
insert into certification(id,name,url, price, version) values (h2seq.nextval,'Spring','https://spring.io', 100.50,0);
insert into certification(id,name,url, price, version) values (10003,'Spring Boot','https://spring.io', null,0);
insert into certification(id,name,url, price, version) values (10004,'SAP Admin','https://spa.com', null,0);
insert into certification(id,name,url, price, version) values (10005,'Oracle 12c Admin','https://oracle.com', 200,0);

insert into user(id,name,surname,email,password) values(10011,'Elon','Musk','emusk@tesla.com','boilerplate');
insert into user(id,name,surname,email) values(10012,'Tom','Paul','tpaul@tesla.com');
insert into user(id,name,surname,email) values(10013,'Tom','Black','tblack@tesla.com');
insert into user(id,name,surname,email) values(10014,'Zac','Klop','zklop@tesla.com');
insert into user(id,name,surname,email) values(10015,'Tom','Rodgers','trodgers@tesla.com');

insert into voucher(id,state,vouchercode,validUntil,certification_id) values (10020,'NEW','VC1234',sysdate,10001);

insert into skill(id,name,desc) values (20001, 'Java 11 developer','Java 11 developer');
insert into skill(id,name,desc) values (20002, 'Java test specialist','Java test specialist');
insert into skill(id,name,desc) values (20003, 'SAP administration','SAP administration');
insert into skill(id,name,desc) values (20004, 'Oracle database 12c administrator','Oracle database 12c administrator');

insert into certification_skill(certification_id, skill_id) values (10003,20001);
insert into certification_skill(certification_id, skill_id) values (10003,20002);

insert into certification_skill(certification_id, skill_id) values (10001,20002);

update voucher set user_id=10012 where id = 10020;