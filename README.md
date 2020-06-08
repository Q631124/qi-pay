# qi-pay
微信、支付宝支付
生成二维码和支付宝所需要的的jar包在WEB-INF/lib文件夹下。
所需的User表结构：
CREATE TABLE `t_user` (
  `id` char(28) NOT NULL,
  `orderNum` char(28) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `price` FLOAT DEFAULT NULL,
	`classItem` varchar(1024) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
