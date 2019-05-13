CREATE TABLE `t_api_record`(
  `rowkey` string COMMENT 'HBASE ROWKEY',
  `recorddesc_start_time` string COMMENT 'APP请求API开始时间 例：2018-08-26 08:34:11:305',
  `recorddesc_server_param` string COMMENT 'APP请求API的参数，JSON格式。参考WIKI上API接口。例如：{"orderId":"6593834303432818689"}',
  `recorddesc_login_id` string COMMENT '登录ID，会员系统Member_ID 例：11024688',
  `recorddesc_response_body` string COMMENT 'API返回APP参数，JSON格式。参考WIKI上API接口。',
  `recorddesc_is_security` string COMMENT '是否验证签名，truefalse 一般HTTPS/HTTP请求都是true',
  `recorddesc_uid` string COMMENT '会话ID，其实是个Token。可以理解为SessionId。',
  `recorddesc_status` string COMMENT '状态，可以参考Lucky-Open-API项目中的MAPIStatus枚举类 1：成功 2：API不存在 3：调用频率超过限制 4：客户端的API权限不足 5：未登录或者登陆已超时 6：服务器内部错误 7：业务处理错误 8：客户端身份签名未通过 9：参数错误 10：客户端身份初始化失败 12：请求协议不支持 13：秘钥过期 15：TOKEN失效 16：当前使用版本已经下架,建议您立刻升级新版客户端 17：密钥为空 18：异步token缺失 19：过滤器拒绝了该请求 20：拦截器拒绝了该请求 21：该',
  `recorddesc_end_time` string COMMENT '请求结束时间 例：2018-08-26 08:34:11:705',
  `recorddesc_exception_desc` string COMMENT '发送异常时，记录的异常描述，一般为异常堆栈 com.zuche.baseModules.openAPI.exception.BaseServiceErrorException: 该门店已有单屏机接单',
  `recorddesc_event_id` string COMMENT '事件ID，用户操作事件唯一ID 例如：1535274120771340',
  `recorddesc_aid` string COMMENT '请求接口地址，API编号。 参考WIKI上API接口。例如：获取订单列表 /resource/s/order/list',
  `recorddesc_adapter_param` string COMMENT '请求API的适配参数，一般是recorddesc_status!=1，也就是请求失败时记录的数据，JSON格式。包含UID，CID等',
  `recorddesc_async_token` string COMMENT '异步TOKEN，异步提交事件的Token',
  `recorddesc_invoker_str` string COMMENT '调用方，渠道CID。可以在admin系统中【技术平台】-【API用户管理】页面中查询到 220101：用户端ios app 210101：用户端android app 100100：门店双屏机 200101：微信H5端 300100：配送端 230101：微信小程序 ',
  `recorddesc_ip_chain` string COMMENT 'HTTP请求IP链，从HttpServletRequest获取 例：117.136.62.35, 10.212.4.171')
  PARTITIONED BY (
  `dt` string)
ROW FORMAT SERDE
  'org.apache.hadoop.hive.ql.io.orc.OrcSerde'
STORED AS INPUTFORMAT
  'org.apache.hadoop.hive.ql.io.orc.OrcInputFormat'
  OUTPUTFORMAT
  'org.apache.hadoop.hive.ql.io.orc.OrcOutputFormat'
  LOCATION
  'hdfs://hadoop2cluster/user/hive/warehouse/hbase.db/t_api_record'
  TBLPROPERTIES (
  'last_modified_by'='hadoop',
  'last_modified_time'='1536043038',
  'transient_lastDdlTime'='1536043038')