
/* 支付中心相关表结构 */

CREATE TABLE `mch_info` (
  `mch_id` varchar(30) NOT NULL COMMENT '商户ID',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `type` varchar(24) NOT NULL COMMENT '类型',
  `req_key` varchar(128) NOT NULL COMMENT '请求私钥',
  `res_key` varchar(128) NOT NULL COMMENT '响应私钥',
  `state` tinyint(6) NOT NULL DEFAULT '1' COMMENT '商户状态,0-停止使用,1-使用中',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`mch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户信息表';

CREATE TABLE `pay_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '渠道主键ID',
  `channel_id` varchar(24) NOT NULL COMMENT '渠道ID',
  `channel_name` varchar(30) NOT NULL COMMENT '渠道名称,如:alipay,wechat',
  `channel_mch_id` varchar(32) NOT NULL COMMENT '渠道商户ID',
  `mch_id` varchar(30) NOT NULL COMMENT '商户ID',
  `state` tinyint(6) NOT NULL DEFAULT '1' COMMENT '渠道状态,0-停止使用,1-使用中',
  `param` varchar(4096) NOT NULL COMMENT '配置参数,json字符串',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_MchId_MchOrderNo` (`channel_id`, `mch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付渠道表';

CREATE TABLE `pay_order` (
  `pay_order_id` varchar(30) NOT NULL COMMENT '支付订单号',
  `mch_id` varchar(30) NOT NULL COMMENT '商户ID',
  `mch_order_no` varchar(30) NOT NULL COMMENT '商户订单号',
  `channel_id` varchar(24) NOT NULL COMMENT '渠道ID',
  `amount` bigint(20) NOT NULL COMMENT '支付金额,单位分',
  `currency` varchar(3) NOT NULL DEFAULT 'cny' COMMENT '三位货币代码,人民币:cny',
  `status` tinyint(6) NOT NULL DEFAULT '0' COMMENT '支付状态,0-订单生成,1-支付中(目前未使用),2-支付成功,3-业务处理完成',
  `client_ip` varchar(32) DEFAULT NULL COMMENT '客户端IP',
  `device` varchar(64) DEFAULT NULL COMMENT '设备',
  `subject` varchar(64) NOT NULL COMMENT '商品标题',
  `body` varchar(256) NOT NULL COMMENT '商品描述信息',
  `extra` varchar(512) DEFAULT NULL COMMENT '特定渠道发起时额外参数',
  `channel_mch_id` varchar(32) NOT NULL COMMENT '渠道商户ID',
  `channel_order_no` varchar(64) DEFAULT NULL COMMENT '渠道订单号',
  `err_code` varchar(64) DEFAULT NULL COMMENT '渠道支付错误码',
  `err_msg` varchar(128) DEFAULT NULL COMMENT '渠道支付错误描述',
  `param1` varchar(64) DEFAULT NULL COMMENT '扩展参数1',
  `param2` varchar(64) DEFAULT NULL COMMENT '扩展参数2',
  `notify_url` varchar(128) NOT NULL COMMENT '通知地址',
  `notify_count` tinyint(6) NOT NULL DEFAULT 0 COMMENT '通知次数',
  `last_notify_time` bigint(20) DEFAULT NULL COMMENT '最后一次通知时间',
  `expire_time` bigint(20) DEFAULT NULL COMMENT '订单失效时间',
  `pay_succ_time` bigint(20) DEFAULT NULL COMMENT '订单支付成功时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`pay_order_id`),
  UNIQUE KEY `IDX_MchId_MchOrderNo` (`mch_id`, `mch_order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付订单表';

CREATE TABLE `iap_receipt` (
  `pay_order_id` varchar(30) NOT NULL COMMENT '支付订单号',
  `mch_id` varchar(30) NOT NULL COMMENT '商户ID',
  `transaction_id` varchar(24) NOT NULL COMMENT 'IAP业务号',
  `receipt_data` TEXT NOT NULL COMMENT '渠道ID',
  `status` tinyint(6) NOT NULL DEFAULT '0' COMMENT '处理状态:0-未处理,1-处理成功,-1-处理失败',
  `handle_count` tinyint(6) NOT NULL DEFAULT 0 COMMENT '处理次数',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`pay_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='苹果支付凭据表';

CREATE TABLE `trans_order` (
  `trans_order_id` varchar(30) NOT NULL COMMENT '转账订单号',
  `mch_id` varchar(30) NOT NULL COMMENT '商户ID',
  `mch_trans_no` varchar(30) NOT NULL COMMENT '商户转账单号',
  `channel_id` varchar(24) NOT NULL COMMENT '渠道ID',
  `amount` bigint(20) NOT NULL COMMENT '转账金额,单位分',
  `currency` varchar(3) NOT NULL DEFAULT 'cny' COMMENT '三位货币代码,人民币:cny',
  `status` tinyint(6) NOT NULL DEFAULT '0' COMMENT '转账状态:0-订单生成,1-转账中,2-转账成功,3-转账失败,4-业务处理完成',
  `result` tinyint(6) NOT NULL DEFAULT '0' COMMENT '转账结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败',
  `client_ip` varchar(32) DEFAULT NULL COMMENT '客户端IP',
  `device` varchar(64) DEFAULT NULL COMMENT '设备',
  `remark_info` varchar(256) DEFAULT NULL COMMENT '备注',
  `channel_user` varchar(32) DEFAULT NULL COMMENT '渠道用户标识,如微信openId,支付宝账号',
  `user_name` varchar(24) DEFAULT NULL COMMENT '用户姓名',
  `channel_mch_id` varchar(32) NOT NULL COMMENT '渠道商户ID',
  `channel_order_no` varchar(32) DEFAULT NULL COMMENT '渠道订单号',
  `channel_err_code` varchar(128) DEFAULT NULL COMMENT '渠道错误码',
  `channel_err_msg` varchar(128) DEFAULT NULL COMMENT '渠道错误描述',
  `extra` varchar(512) DEFAULT NULL COMMENT '特定渠道发起时额外参数',
  `notify_url` varchar(128) NOT NULL COMMENT '通知地址',
  `param1` varchar(64) DEFAULT NULL COMMENT '扩展参数1',
  `param2` varchar(64) DEFAULT NULL COMMENT '扩展参数2',
  `expire_time` datetime DEFAULT NULL COMMENT '订单失效时间',
  `trans_succ_time` datetime DEFAULT NULL COMMENT '订单转账成功时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`trans_order_id`),
  UNIQUE KEY `IDX_MchId_MchOrderNo` (`mch_id`, `mch_trans_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='转账订单表';

CREATE TABLE `refund_order` (
  `refund_order_id` varchar(30) NOT NULL COMMENT '退款订单号',
  `pay_order_id` varchar(30) NOT NULL COMMENT '支付订单号',
  `channel_pay_order_no` varchar(64) DEFAULT NULL COMMENT '渠道支付单号',
  `mch_id` varchar(30) NOT NULL COMMENT '商户ID',
  `mch_refund_no` varchar(30) NOT NULL COMMENT '商户退款单号',
  `channel_id` varchar(24) NOT NULL COMMENT '渠道ID',
  `pay_amount` bigint(20) NOT NULL COMMENT '支付金额,单位分',
  `refund_amount` bigint(20) NOT NULL COMMENT '退款金额,单位分',
  `currency` varchar(3) NOT NULL DEFAULT 'cny' COMMENT '三位货币代码,人民币:cny',
  `status` tinyint(6) NOT NULL DEFAULT '0' COMMENT '退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-业务处理完成',
  `result` tinyint(6) NOT NULL DEFAULT '0' COMMENT '退款结果:0-不确认结果,1-等待手动处理,2-确认成功,3-确认失败',
  `client_ip` varchar(32) DEFAULT NULL COMMENT '客户端IP',
  `device` varchar(64) DEFAULT NULL COMMENT '设备',
  `remark_info` varchar(256) DEFAULT NULL COMMENT '备注',
  `channel_user` varchar(32) DEFAULT NULL COMMENT '渠道用户标识,如微信openId,支付宝账号',
  `user_name` varchar(24) DEFAULT NULL COMMENT '用户姓名',
  `channel_mch_id` varchar(32) NOT NULL COMMENT '渠道商户ID',
  `channel_order_no` varchar(32) DEFAULT NULL COMMENT '渠道订单号',
  `channel_err_code` varchar(128) DEFAULT NULL COMMENT '渠道错误码',
  `channel_err_msg` varchar(128) DEFAULT NULL COMMENT '渠道错误描述',
  `extra` varchar(512) DEFAULT NULL COMMENT '特定渠道发起时额外参数',
  `notify_url` varchar(128) NOT NULL COMMENT '通知地址',
  `param1` varchar(64) DEFAULT NULL COMMENT '扩展参数1',
  `param2` varchar(64) DEFAULT NULL COMMENT '扩展参数2',
  `expire_time` datetime DEFAULT NULL COMMENT '订单失效时间',
  `refund_succ_time` datetime DEFAULT NULL COMMENT '订单退款成功时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`refund_order_id`),
  UNIQUE KEY `IDX_MchId_MchOrderNo` (`mch_id`, `mch_refund_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退款订单表';

CREATE TABLE `mch_notify` (
  `order_id` varchar(24) NOT NULL COMMENT '订单ID',
  `mch_id` varchar(30) NOT NULL COMMENT '商户ID',
  `mch_order_no` varchar(30) NOT NULL COMMENT '商户订单号',
  `order_type` varchar(8) NOT NULL COMMENT '订单类型:1-支付,2-转账,3-退款',
  `notify_url` varchar(2048) NOT NULL COMMENT '通知地址',
  `notify_count` tinyint(6) NOT NULL DEFAULT 0 COMMENT '通知次数',
  `result` varchar(2048) DEFAULT NULL COMMENT '通知响应结果',
  `status` tinyint(6) NOT NULL DEFAULT '1' COMMENT '通知状态,1-通知中,2-通知成功,3-通知失败',
  `last_notify_time` datetime DEFAULT NULL COMMENT '最后一次通知时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `IDX_MchId_OrderType_MchOrderNo` (`mch_id`, `order_type`, `mch_order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户通知表';


/* 支付演示商城相关表 */

CREATE TABLE `goods_order` (
  `goods_order_id` varchar(30) NOT NULL COMMENT '商品订单ID',
  `goods_id` varchar(30) NOT NULL COMMENT '商品ID',
  `goods_name` varchar(64) NOT NULL DEFAULT '' COMMENT '商品名称',
  `amount` bigint(20) NOT NULL COMMENT '金额,单位分',
  `user_id` varchar(30) NOT NULL COMMENT '用户ID',
  `status` tinyint(6) NOT NULL DEFAULT '0' COMMENT '订单状态,订单生成(0),支付成功(1),处理完成(2),处理失败(-1)',
  `pay_order_id` varchar(30) DEFAULT NULL COMMENT '支付订单号',
  `channel_id` varchar(24) DEFAULT NULL COMMENT '渠道ID',
  `channel_user_id` varchar(64) DEFAULT NULL COMMENT '支付渠道用户ID(微信openID或支付宝账号等第三方支付账号)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`goods_order_id`),
  UNIQUE KEY `IDX_PayOrderId` (`pay_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品订单表';