use research_production;

drop table if exists metalog;
drop table if exists friend;
drop table if exists channel_members;
drop table if exists message;
drop table if exists channel;
drop table if exists activity;
drop table if exists group_members;
drop table if exists user_group;
drop table if exists location_log;
drop table if exists event_annotation;
drop table if exists event_log;
drop table if exists cv_application;
drop table if exists cv_event_category;
drop table if exists cv_event_name;
drop table if exists user_session;
drop table if exists user;

create table metalog (
       id			int not null auto_increment primary key,
       created_at		datetime not null,
       male_count    		int not null,
       female_count  		int not null,
       unknown_sex_count	int not null,
       private_channel_count 	int not null,
       public_channel_count	int not null,
       open_group_count		int not null,
       closed_group_count	int not null
) ENGINE=INNODB;

create table user (
       id			int not null auto_increment primary key,
       user_id			varchar(255),
       user_name    		varchar(255),
       sex			int,
       when_created		datetime not null,
       time_of_birth		date
) ENGINE=INNODB;

-- assumption: friendship may be asymmetric
create table friend (
       id    	    int not null auto_increment primary key,
       user_id      int not null,
       friend_id    int not null,
       begin_at     datetime not null,
       end_at       datetime,
       foreign key (user_id) references user(id),
       foreign key (friend_id) references user(id)
) ENGINE=INNODB;

create table channel (
       id    	    int not null auto_increment primary key,
       channel_name    varchar(255) not null unique,
       channel_id    varchar(255) not null unique,
       created_by      int not null,
       when_created    datetime not null,
       is_public       int,
       foreign key (created_by) references user(id)

) ENGINE=INNODB;

create table channel_members (
       id    	    int not null auto_increment primary key,
       user_id      int not null,
       channel_id    int not null,
       begin_at     datetime not null,
       end_at       datetime,
       foreign key (user_id) references user(id),
       foreign key (channel_id) references channel(id)
) ENGINE=INNODB;

create table message (
       id    	    bigint not null auto_increment primary key,
       channel_id    int not null,
       created_by      int not null,
       when_created    timestamp,
       foreign key (created_by) references user(id),
       foreign key (channel_id) references channel(id)
) ENGINE=INNODB;


create table user_group (
       id    	    int not null auto_increment primary key,
       group_name    varchar(255) not null unique,
       created_by    int not null,
       when_created  datetime not null,
       is_open_group int,
       foreign key (created_by) references user(id)
) ENGINE=INNODB;

create table group_members (
       id    	    int not null auto_increment primary key,
       user_id      int not null,
       group_id    int not null,
       begin_at     datetime not null,
       end_at       datetime,
       foreign key (user_id) references user(id),
       foreign key (group_id) references user_group(id)
) ENGINE=INNODB;

create table activity (
       activity_id    int not null auto_increment primary key,
       activity_date    date not null,
       user_id      int not null,
       actions	    int not null,
       application_id	int not null,
       foreign key (user_id) references user(id),
       foreign key (application_id) references cv_application(application_id)
) ENGINE=INNODB;

create table location_log (
       location_id	  bigint not null auto_increment primary key,
       when_created	  datetime not null,
       user_id		  int not null,
       latitude		  double not null,
       longitude	  double not null,
       altitude		  double,
       foreign key (user_id) references user(id)
) ENGINE=INNODB;

create table cv_application (
       application_id	    int not null auto_increment primary key,
       cos_application_id   varchar(255),
       name		    varchar(255) not null unique
) ENGINE=INNODB;

create table cv_event_category (
       event_category_id	    int not null auto_increment primary key,
       event_category   	    varchar(255)
) ENGINE=INNODB;

create table cv_event_name (
       event_name_id	    int not null auto_increment primary key,
       event_name	       varchar(255)
) ENGINE=INNODB;


create table user_session (
       id	    	  bigint not null auto_increment primary key,
       cos_session_id	  varchar(255)
) ENGINE=INNODB;

create table event_log (
       id    	       bigint not null auto_increment primary key,
       session_id	bigint not null,
       transaction_id	bigint not null,
       user_id	       int not null,
       application_id	int not null,
       event_category	int not null,
       event_name	int not null,
       parent_event	bigint,
       event_parameters	varchar(1000),
       when_created	datetime not null,
       foreign key (session_id) references user_session(id),
       foreign key (user_id) references user(id),
       foreign key (application_id) references cv_application(application_id),
       foreign key (event_category) references cv_event_category(event_category_id),
       foreign key (event_name) references cv_event_name(event_name_id)
) ENGINE=INNODB;

create table event_annotation (
       id    	       bigint not null auto_increment primary key,
       description     varchar(255) not null,
       foreign key (id) references event_log(id)
) ENGINE=INNODB;

create table ressi_group (
       id    	       bigint not null auto_increment primary key,
       name	       varchar(255) not null unique
) ENGINE=INNODB;

create table ressi_group_member (
       id    	       bigint not null auto_increment primary key,
       group_id	       bigint not null,
       user_id	       int not null,
       foreign key (group_id) references ressi_group(id),
       foreign key (user_id) references user(id)
) ENGINE=INNODB;

create table ressi_cos_delta (
       id    	       bigint not null auto_increment primary key,
       prev_max_id     bigint not null
) ENGINE=INNODB;


create table user_message_count (
       id                       bigint not null auto_increment primary key,
       user_id                  int not null,
       day                      date not null,
       message_count            bigint not null,
       cumulative_count	        bigint not null, 
       foreign key (user_id) references user(id)
) ENGINE=INNODB;

commit;

exit

