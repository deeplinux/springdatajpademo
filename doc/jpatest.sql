# Host: 127.0.0.1  (Version 5.0.96-community)
# Date: 2017-04-01 01:11:59
# Generator: MySQL-Front 5.4  (Build 4.26)
# Internet: http://www.mysqlfront.de/

/*!40101 SET NAMES utf8 */;
CREATE DATABASE `jpatest` /*!40100 DEFAULT CHARACTER SET utf8 */;

#
# Structure for table "obj_user"
#

CREATE TABLE `obj_user` (
  `user_id` int(11) NOT NULL auto_increment,
  `name` varchar(32) NOT NULL default '' COMMENT '名字',
  `age` tinyint(3) NOT NULL default '0' COMMENT '年龄',
  `high` int(11) NOT NULL default '0' COMMENT '身高',
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Data for table "obj_user"
#

INSERT INTO `obj_user` VALUES (1,'张三',16,165),(2,'张三',16,170),(3,'张三',17,192);
