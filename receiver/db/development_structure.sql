CREATE TABLE `cos_events` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` varchar(255) default NULL,
  `application_id` varchar(255) default NULL,
  `cos_session_id` varchar(255) default NULL,
  `ip_address` varchar(255) default NULL,
  `action` varchar(255) default NULL,
  `parameters` varchar(255) default NULL,
  `created_at` datetime default NULL,
  `updated_at` datetime default NULL,
  `return_value` varchar(255) default NULL,
  `http_user_agent` varchar(255) default NULL,
  `request_uri` varchar(255) default NULL,
  `http_referer` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `schema_migrations` (
  `version` varchar(255) NOT NULL,
  UNIQUE KEY `unique_schema_migrations` (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO schema_migrations (version) VALUES ('20081120141228');

INSERT INTO schema_migrations (version) VALUES ('20090107131242');