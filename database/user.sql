desc user;

-- join
insert into user values(null, '관리자', 'admin@mysite.com', password('123'), 'female', current_date(), 'ADMIN');

-- login
select * from user where email = 'naruto@gmail.com' and password=password('12345');

-- test
select * from user;

-- role
alter table user add column role enum('ADMIN', 'USER') not null default 'USER';
update user set role='ADMIN' where no = 1;