DROP TABLE IF EXISTS `userwx`;


CREATE TABLE `userwx` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(256) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `password` char(128) DEFAULT NULL,
  `salt` char(32) DEFAULT NULL,
  `mail` varchar(128) DEFAULT NULL,
  `phone` varchar(11) NOT NULL,
  `token` char(128) DEFAULT NULL,
  `is_locked` tinyint(1) NOT NULL,
  `hp` bigint(20) DEFAULT NULL,
  `gmt_create` datetime NOT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`),
  UNIQUE KEY `uk_open_id` (`open_id`),
  UNIQUE KEY `uk_phone` (`phone`),
  UNIQUE KEY `uk_token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
alter table userwx add index index_userwx(phone);
alter table userwx add index index_userwx1(open_id);
alter table userwx add index index_userwx2(name);
