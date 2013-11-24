create table Users (
    id_user int not null primary key generated always as identity (start with 1, increment by 1),
    username varchar(255) unique not null,
    password varchar(255) not null
);

create table Groups (
    id_group int not null primary key generated always as identity (start with 1, increment by 1),
    name varchar(255) not null,
    active boolean not null  default true,
    id_owner int not null,
    foreign key(id_owner) references Users(id_user)
);
create table FileDB (
    hashed_name varchar(255) not null,
    id_group int not null,
    id_user int not null,
    original_name varchar(255) not null,
    type varchar(255) not null,
    primary key(hashed_name, id_group, id_user),
    foreign key(id_group) references Groups(id_group),
    foreign key(id_user) references Users(id_user)

);
create table GroupUser (
    id_group int not null,
    id_user int not null,
    active boolean not null  default true,
    primary key(id_group, id_user),
    foreign key(id_group) references Groups(id_group),
    foreign key(id_user) references Users(id_user)
);

create table Post (
    id_post int not null primary key generated always as identity (start with 1, increment by 1),
    visible boolean default true,
    date_post timestamp,
    message long varchar,
    id_group int not null,
    id_user int not null,
    foreign key(id_group) references Groups(id_group),
    foreign key(id_user) references Users(id_user)
);

create table Invite (
    id_group int not null,
    id_user int not null,
    invite_date date not null,
    primary key(id_group, id_user),
    foreign key(id_group) references Groups(id_group),
    foreign key(id_user) references Users(id_user)

);
