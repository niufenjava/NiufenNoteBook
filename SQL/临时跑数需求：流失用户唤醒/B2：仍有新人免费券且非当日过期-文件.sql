%livy.spark
val filepath = "/user/haijun.zhang/usergrowth/msg/dt=2019-03-13/B2/";
var curDay = "2019-03-13";
var lastDay = "2018-03-13";
val sqlmon = """
SELECT
  concat(mem.create_time,',',mem.id,',',mem.phone_no,',',1,',',quan.expire_time,',',get_time)
FROM
  -- 流失
  (
    SELECT
      DISTINCT o.member_id
    FROM
      lucky_order.t_order o
      -- 30天之内有下单用户
      LEFT JOIN (
                  SELECT DISTINCT o.member_id
                  FROM
                    lucky_order.t_order o
                    LEFT JOIN lucky_crm.t_member m ON m.id = o.member_id
                  WHERE
                    o.STATUS = 50
                    AND o.dt > date_sub('2019-03-13', 30) -- 本月
                    AND o.dt <= '2019-03-13' -- 上月
                    AND o.dep_id NOT IN (SELECT department_id
                                         FROM dw_dim.t_test_shop)
                    AND m.status = 1
                    AND m.id IS NOT NULL
                ) h ON h.member_id = o.member_id
      LEFT JOIN lucky_crm.t_member m ON o.member_id = m.id
    WHERE
      o.STATUS = 50
      -- 30天前下单用户
      AND o.dt <= date_sub('2019-03-13', 30)
      AND o.dep_id NOT IN (SELECT department_id
                           FROM dw_dim.t_test_shop)
      AND m.status = 1
      AND m.id IS NOT NULL
      -- 不包含30天内下单的用户
      AND h.member_id IS NULL
    GROUP BY o.member_id
    HAVING count(DISTINCT o.id) > 0
  ) liushi
  -- 注册日期距核算日期刚好一周年
  LEFT JOIN
  (
    SELECT m.id,m.phone_no,from_unixtime(unix_timestamp(m.created_time), 'yyyy-MM-dd') as create_time,from_unixtime(unix_timestamp(p.get_time), 'yyyy-MM-dd') as get_time
    FROM lucky_crm.t_member m
      LEFT JOIN lucky_crm.t_member_profile p on p.mem_id = m.id
    WHERE m.status = 1
          AND p.id is not null
          AND from_unixtime(unix_timestamp(m.created_time), 'yyyy-MM-dd') = '2018-03-13'
  ) mem ON mem.id = liushi.member_id
  -- 仍有新人免费券且非日过期
  LEFT JOIN
  (
    SELECT DISTINCT coupon.member_id,expire_time
    FROM
      (
        SELECT DISTINCT c.member_id,from_unixtime(unix_timestamp(c.expire_time), 'yyyy-MM-dd') as expire_time
        FROM lucky_marketing.t_mkt_coupon_send_record c
        WHERE
          c.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
          AND c.coupon_description = '券折扣0折'
          -- 已领取、转赠中
          AND c.status in (1,7)
          AND from_unixtime(unix_timestamp(c.expire_time), 'yyyy-MM-dd') > '2019-03-13'
        UNION
        -- 咖啡库券-新人免费券
        SELECT DISTINCT o.member_id, from_unixtime(unix_timestamp(o.expire_time), 'yyyy-MM-dd') as expire_time
        FROM lucky_coupon_order.t_mkt_coffee_coupon_send_record o
        WHERE
          o.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
          -- 已领取、转赠中
          AND o.status in (1,7)
          AND from_unixtime(unix_timestamp(o.expire_time), 'yyyy-MM-dd') > '2019-03-13'
      ) coupon
  ) quan ON quan.member_id = liushi.member_id
WHERE mem.id IS NOT NULL
      AND quan.member_id IS NOT NULL

 """
 //var cursql = sqlmon.replace("2019-03-11",curDay).replace("2018-03-11",lastDay);
 // print(cursql);
 //spark.sql(cursql).show();
spark.sql(sqlmon).repartition(1).write.mode(org.apache.spark.sql.SaveMode.Overwrite).option("header", "false").text(filepath)
