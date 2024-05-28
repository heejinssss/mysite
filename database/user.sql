desc user;

-- join
insert into user values(null, '관리자', 'admin@mysite.com', password('1234'), 'female', current_date());

-- login
select * from user where email = 'tsunade@gmail.com' and password=password('12');

-- test
select * from user;