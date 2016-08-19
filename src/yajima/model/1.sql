create table customers(
	id int primary key,
	name varchar(100),
	address varchar(255)
);
create table orders(
	ordernum int primary key,
	amout float(8,2),
	customerId int,
	constraint customer_id_fk foreign key(customerId) references customers(id)
);

create table teachers(
	id int primary key,
	name varchar(100),
	salary float(8,2)
);
create table students(
	id int primary key,
	name varchar(100),
	grade varchar(10),
);
create table teachers_students(
	t_id int,
	s_id int,
	primary key(t_id,s_id),
	constraint teacher_id_fk foreign key(t_id) references teachers(id),
	constraint student_id_fk foreign key(s_id) references students(id)
	);
print:heheda