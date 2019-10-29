SELECT
  -- 基本信息
  member.id                                      AS member_id,
  member.phone_no                                AS phone_no,
  member.name                                    AS name,
  member.birthday                                AS birthday,
  IFNULL(member.sex, 0)                          AS sex,
  member.pic_url                                 AS pic_url,

  -- 状态&类型
  member.status                                  AS status,
  member.property                                AS property,
  member.mem_type                                AS mem_type,
  member.origin_channel                          AS origin_channel,
  member.mem_origin                              AS mem_origin,

  -- 微信相关
  member_wechat_subscribe.subscribe              AS subscribe,
  member_wechat_subscribe.status                 AS wx_phone_bind,
  member.wechat_name                             AS wechat_name,
  member.wx_pub_openid                           AS wx_pub_openid,
  member.wx_auth_openid                          AS wx_auth_openid,
  member.wx_unionid                              AS wx_unionid,
  member.wx_mini_openid              AS wx_mini_openid,

  -- 活动相关
  member.invitation_code as invitation_code,
  member.marketing_code                          AS marketing_code,
  member.inviter_type                            AS inviter_type,
  member.inviter_code                            AS inviter_code,
  (CASE WHEN member.inviter_code IS NOT NULL AND member.inviter_type = '2' THEN member.inviter_code
   WHEN member.inviter_code IS NOT NULL AND member.inviter_type = '1' THEN 'MEMBERPULLNEW'
   WHEN member.inviter_code IS NOT NULL AND member.inviter_type = '3' THEN 'COMPANYPULLNEW'
   WHEN member.inviter_type IS NULL THEN 'NATURALLYNEW' ELSE NULL END ) AS mkt_invitation_code,
  mkt_channel.belong_one_level_channel   AS channel_level_one,
  mkt_channel.belong_two_level_channel   AS channel_level_two,
  mkt_channel.belong_three_level_channel AS channel_level_three,

  -- 手机号码相关
  ''                                             AS exchange_carrier,
  ''                                             AS phone_province,
  ''                                             AS phone_city,

  -- 设备相关
  member.device_no                               AS device_no,
  member.device_type                             AS device_type,

  -- 首次事件时间
  member.created_time                            AS created_time,
  from_unixtime(unix_timestamp(member.created_time),'yyyy-MM-dd') AS created_date,
  profile.first_login_time                       AS first_login_time,
  from_unixtime(unix_timestamp(profile.first_login_time),'yyyy-MM-dd') AS first_login_date,
  profile.first_order_time                       AS first_order_time,
  from_unixtime(unix_timestamp(profile.first_order_time),'yyyy-MM-dd') AS first_order_date,
  profile.first_pay_time                         AS first_pay_time,
  from_unixtime(unix_timestamp(profile.first_pay_time),'yyyy-MM-dd') AS first_pay_date,
  profile.first_any_pay_time                     AS first_any_pay_time,
  from_unixtime(unix_timestamp(profile.first_any_pay_time),'yyyy-MM-dd') AS first_any_pay_date,
  cor.first_coffeestore_order_time                     AS first_coffeestore_order_time,
  from_unixtime(unix_timestamp(cor.first_coffeestore_order_time),'yyyy-MM-dd') AS first_coffeestore_order_date,

  -- 首次登陆相关
  profile.first_login_origin                     AS first_login_origin,
  profile.first_login_deviceNo                   AS first_login_deviceno,

  -- 新人券相关
  profile.is_send_coupon                         AS is_send_coupon,
  profile.get_time                               AS get_time,
  profile.get_deviceNo                           AS get_deviceno,
  profile.get_platform                           AS get_platform,
  newcoupon.coupon_type                          AS coupon_type,
  newcoupon.coupon_status                        AS coupon_status,
  newcoupon.coupon_used_time                     AS coupon_used_time,
  from_unixtime(unix_timestamp(newcoupon.coupon_used_time),'yyyy-MM-dd') AS coupon_used_date,
  newcoupon.coupon_expire_time                   AS coupon_expire_time,
  from_unixtime(unix_timestamp(newcoupon.coupon_expire_time),'yyyy-MM-dd') AS coupon_expire_date

FROM
  lucky_crm.t_member AS member
  LEFT JOIN   (
  select mem_id,
  max(first_login_time) as first_login_time,
  max(first_order_time) as first_order_time,
  max(first_pay_time) as first_pay_time,
  max(first_any_pay_time) as first_any_pay_time,
  max(first_login_origin) as first_login_origin,
  max(first_login_deviceNo) as first_login_deviceNo,
  max(is_send_coupon) as is_send_coupon,
  max(get_time) as get_time,
  max(get_deviceNo) as get_deviceNo,
  max(get_platform) as get_platform
  from lucky_crm.t_member_profile GROUP BY mem_id
) AS profile ON profile.mem_id = member.id
  LEFT JOIN (  SELECT
    wx_unionid,
    max(subscribe) as subscribe,
    max(status) as status
  FROM lucky_crm.t_member_wechat_subscribe GROUP BY wx_unionid) AS member_wechat_subscribe
    ON member_wechat_subscribe.wx_unionid = member.wx_unionid
  LEFT JOIN ( select
 invitation_code as invitation_code,
 max(belong_one_level_channel) as belong_one_level_channel,
 max(belong_two_level_channel) as belong_two_level_channel,
 max(belong_three_level_channel) as belong_three_level_channel
 from lucky_marketing.t_mkt_invitation_code GROUP BY invitation_code ) AS mkt_channel
    ON mkt_channel.invitation_code = member.inviter_code  -- 新人券相关
  LEFT JOIN
  (
    SELECT newcoupon.member_id AS member_id,
           max(newcoupon.coupon_status) as coupon_status,
           max(newcoupon.coupon_expire_time) as coupon_expire_time,
           max(newcoupon.coupon_type) AS coupon_type,
           max(newcoupon.coupon_used_time) AS coupon_used_time
    FROM (
           SELECT
             coupon.member_id AS member_id,
             max(coupon.status) AS coupon_status,
             max(coupon.use_time) AS coupon_used_time,
             max(coupon.expire_time) AS coupon_expire_time,
             1 AS coupon_type
           FROM lucky_marketing.t_mkt_coupon_send_record coupon
             LEFT JOIN lucky_marketing.t_mkt_coupon cou ON cou.id = coupon.coupon_id
           WHERE
             coupon.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
             AND coupon.status != 0
             AND cou.discount_degree = 0
           GROUP BY coupon.member_id
           UNION
           SELECT
             coffeestore.member_id AS member_id,
             max(coffeestore.status) AS coupon_status,
             max(coffeestore.use_time) AS coupon_used_time,
             max(coffeestore.expire_time) AS coupon_expire_time,
             2 AS coupon_type
           FROM lucky_coupon_order.t_mkt_coffee_coupon_send_record coffeestore
           WHERE
             coffeestore.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
             AND coffeestore.status != 0
           GROUP BY coffeestore.member_id
         ) newcoupon GROUP BY newcoupon.member_id
  ) newcoupon ON newcoupon.member_id = member.id
  LEFT JOIN (
    SELECT
        member_id,
        min(create_time) AS first_coffeestore_order_time
     FROM lucky_coupon_order.t_coupon_order co
     GROUP BY member_id
   ) cor
    ON member.id = cor.member_id