%livy.spark
// var curDay = Array("2019-03-11","2019-03-12","2019-03-13","2019-03-14","2019-03-15");
// var registDay = Array("2018-03-11","2018-03-12","2018-03-13","2018-03-14","2018-03-15");
var curDay = Array("2019-03-13");
var registDay = Array("2018-03-13");
-- var sqlmon = """
SELECT * FROM (

-- 仍有新人免费券且当日过期
SELECT
  count(DISTINCT liushi.member_id),4 AS inx
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
                    AND o.dt > date_sub('2019-03-11', 30) -- 本月
                    AND o.dt <= '2019-03-11' -- 上月
                    AND o.dep_id NOT IN (SELECT department_id
                                         FROM dw_dim.t_test_shop)
                    AND m.status = 1
                    AND m.id IS NOT NULL
                ) h ON h.member_id = o.member_id
      LEFT JOIN lucky_crm.t_member m ON o.member_id = m.id
    WHERE
      o.STATUS = 50
      -- 30天前下单用户
      AND o.dt <= date_sub('2019-03-11', 30)
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
    SELECT m.id
    FROM lucky_crm.t_member m
    WHERE m.status = 1
          AND from_unixtime(unix_timestamp(m.created_time), 'yyyy-MM-dd') = '2018-03-11'
  ) mem ON mem.id = liushi.member_id
  -- 仍有新人免费券且当日过期
  LEFT JOIN
  (
    SELECT DISTINCT coupon.member_id
    FROM
      (
        SELECT DISTINCT c.member_id
        FROM lucky_marketing.t_mkt_coupon_send_record c
        WHERE
          c.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
          AND c.coupon_description = '券折扣0折'
          -- 已领取、转赠中
          AND c.status in (1,7)
          AND from_unixtime(unix_timestamp(c.expire_time), 'yyyy-MM-dd') = '2019-03-11'
        UNION
        -- 咖啡库券-新人免费券
        SELECT DISTINCT o.member_id
        FROM lucky_coupon_order.t_mkt_coffee_coupon_send_record o
        WHERE
          o.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
          -- 已领取、转赠中
          AND o.status in (1,7)
           AND from_unixtime(unix_timestamp(o.expire_time), 'yyyy-MM-dd') = '2019-03-11'
      ) coupon
  ) quan ON quan.member_id = liushi.member_id
WHERE mem.id IS NOT NULL
      AND quan.member_id IS NOT NULL

UNION

-- 仍有新人免费券且非当日过期
SELECT
  count(DISTINCT liushi.member_id),5 AS inx
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
                    AND o.dt > date_sub('2019-03-11', 30) -- 本月
                    AND o.dt <= '2019-03-11' -- 上月
                    AND o.dep_id NOT IN (SELECT department_id
                                         FROM dw_dim.t_test_shop)
                    AND m.status = 1
                    AND m.id IS NOT NULL
                ) h ON h.member_id = o.member_id
      LEFT JOIN lucky_crm.t_member m ON o.member_id = m.id
    WHERE
      o.STATUS = 50
      -- 30天前下单用户
      AND o.dt <= date_sub('2019-03-11', 30)
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
    SELECT m.id
    FROM lucky_crm.t_member m
    WHERE m.status = 1
          AND from_unixtime(unix_timestamp(m.created_time), 'yyyy-MM-dd') = '2018-03-11'
  ) mem ON mem.id = liushi.member_id
  -- 仍有新人免费券且当日过期
  LEFT JOIN
  (
    SELECT DISTINCT coupon.member_id
    FROM
      (
        SELECT DISTINCT c.member_id
        FROM lucky_marketing.t_mkt_coupon_send_record c
        WHERE
          c.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
          AND c.status != 0
          AND c.coupon_description = '券折扣0折'
          -- 已领取、转赠中
          AND c.status in (1,7)
          AND from_unixtime(unix_timestamp(c.expire_time), 'yyyy-MM-dd') > '2019-03-11'
        UNION
        -- 咖啡库券-新人免费券
        SELECT DISTINCT o.member_id
        FROM lucky_coupon_order.t_mkt_coffee_coupon_send_record o
        WHERE
          o.origin_canal IN (2, 7, 9, 12, 17, 18, 19)
          -- 已领取、转赠中
          AND o.status in (1,7)
          AND from_unixtime(unix_timestamp(o.expire_time), 'yyyy-MM-dd') > '2019-03-11'
      ) coupon
  ) quan ON quan.member_id = liushi.member_id
WHERE mem.id IS NOT NULL
      AND quan.member_id IS NOT NULL

UNION

-- 无新人免费券
SELECT
  count(DISTINCT liushi.member_id),6 AS inx
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
                    AND o.dt > date_sub('2019-03-11', 30) -- 本月
                    AND o.dt <= '2019-03-11' -- 上月
                    AND o.dep_id NOT IN (SELECT department_id
                                         FROM dw_dim.t_test_shop)
                    AND m.status = 1
                    AND m.id IS NOT NULL
                ) h ON h.member_id = o.member_id
      LEFT JOIN lucky_crm.t_member m ON o.member_id = m.id
    WHERE
      o.STATUS = 50
      -- 30天前下单用户
      AND o.dt <= date_sub('2019-03-11', 30)
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
    SELECT m.id
    FROM lucky_crm.t_member m
      LEFT JOIN lucky_crm.t_member_profile p ON p.mem_id = m.id
    WHERE m.status = 1
          AND from_unixtime(unix_timestamp(m.created_time), 'yyyy-MM-dd') = '2018-03-11'
          AND p.get_time IS NULL
  ) mem ON mem.id = liushi.member_id
WHERE mem.id IS NOT NULL
) ttt
order by ttt.inx
"""

 var i = 0;
 for (i <- 0.to(curDay.length-1)) {
    var cursql = sqlmon.replace("2019-03-11",curDay(i)).replace("2018-03-11",registDay(i));
     print(curDay(i));
     //print(cursql);
     spark.sql(cursql).show();
 }