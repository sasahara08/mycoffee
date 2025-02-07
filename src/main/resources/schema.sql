create table if not exists users (
  id int not null primary key auto_increment,
  name varchar(50) not null,
  email varchar(100) not null unique,
  password varchar(200) not null,
  img varchar(255),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  enabled boolean not null
);

create table if not exists bean(
  id int not null primary key auto_increment,
  name varchar(50) not null
  );

create table if not exists recipe (
  id int not null primary key auto_increment,
  user_id int not null,
  bean_id int not null,
  roast varchar(10),
  grind_size varchar(10),
  bean_weight int,
  water_volume int,
  water_temp int,
  steaming_time int,
  dripper varchar(30),
  filter varchar(30), 
  memo varchar(200),
  is_view boolean not null default false,
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  foreign key (user_id) references users(id),
  foreign key (bean_id) references bean(id)
  );

create table if not exists comment (
  id int not null primary key auto_increment,
  user_id int not null,
  recipe_id int not null,
  text varchar(200) not null,
  foreign key (user_id) references users(id),
  foreign key (recipe_id) references recipe(id)
);

create table if not exists favorite (
  id int not null primary key auto_increment,
  user_id int not null,  --自分のid
  recipe_id int not null,
  foreign key (user_id) references users(id),
  foreign key (recipe_id) references recipe(id)
);

-- メール認証トークン発行用テーブル
create table if not Exists verification_tokens(
  id int not null auto_increment primary key,
  user_id int not null unique,
  token varchar(255) not null,
  foreign key (user_id) references users (id)
);




