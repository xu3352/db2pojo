create database mars;

use mars;

CREATE TABLE `t_student` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '姓名',
  `sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别',
  `age` int(3) NOT NULL DEFAULT '0' COMMENT '年龄',
  `hobby` tinyint(1) NOT NULL DEFAULT '0' COMMENT '业余爱好:[1.美食 2.K歌 3.逛街 4.游戏 5.旅游 6.运动]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='学生表';

