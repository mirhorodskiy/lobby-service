create table country (country_id  bigserial not null, name varchar(255), primary key (country_id))
create table currency (currency_id  bigserial not null, title varchar(255), primary key (currency_id))
create table game_country (game_lobby_id int8 not null, country_id int8 not null)
create table game_currency (game_lobby_id int8 not null, currency_id int8 not null)
create table game_details (game_details_id  bigserial not null, game_id varchar(255), provider_id varchar(255), primary key (game_details_id))
alter table country add constraint UK_llidyp77h6xkeokpbmoy710d4 unique (name)
alter table currency add constraint UK_a5cni1mwxl59i775uqry7jamo unique (title)
alter table game_country add constraint FKq5hu4w9qxn7fc8rkj0fq5hbfp foreign key (country_id) references country
alter table game_country add constraint FKqc8c3kihvnt4astl3ihi8h6fj foreign key (game_lobby_id) references game_details
alter table game_currency add constraint FKjr3da01yalaqyanvnipsrsfwc foreign key (currency_id) references currency
alter table game_currency add constraint FKd7q8ai3r4pj4lrxv7396bc7bv foreign key (game_lobby_id) references game_details

