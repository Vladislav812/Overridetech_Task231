insert into users (name, age, email, password) values ('Name1', 20, 'email1@gmail.com', '111');
insert into users (name, age, email, password) values ('Name2', 21, 'email2@gmail.com', '222');
insert into users (name, age, email, password) values ('Name3', 22, 'email3@gmail.com', '333');
insert into users (name, age, email, password) values ('Name4', 23, 'email4@gmail.com', '444');
insert into users (name, age, email, password) values ('Psina Sutulaya', 30,	'psina@mail.ru', 'psina');

insert into roles (title) values ('ROLE_user');
insert into roles (title) values ('ROLE_admin');
insert into roles (title) values ('manager');
insert into roles (title) values ('viewer');
insert into roles (title) values ('editor');
insert into roles (title) values ('moderator');

insert into users_current_roles (user_id, current_roles_id) values (1, 1);
insert into users_current_roles (user_id, current_roles_id) values (1, 2);
insert into users_current_roles (user_id, current_roles_id) values (1, 3);
insert into users_current_roles (user_id, current_roles_id) values (1, 4);

insert into users_current_roles (user_id, current_roles_id) values (2, 1);
insert into users_current_roles (user_id, current_roles_id) values (3, 1);
insert into users_current_roles (user_id, current_roles_id) values (4, 1);
insert into users_current_roles (user_id, current_roles_id) values (5, 2);