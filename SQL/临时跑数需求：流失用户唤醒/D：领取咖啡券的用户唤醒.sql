%livy.spark
var curDay = "2019-03-08";
-- var sqlmon = """
SELECT
  sum(CASE WHEN a.couponCount = 1 THEN 1 ELSE 0 END) AS C1,
  sum(CASE WHEN a.couponCount >= 2 AND a.couponCount <= 5 THEN 1  ELSE 0 END) AS C2,
  sum(CASE WHEN a.couponCount >= 6 AND a.couponCount <= 10 THEN 1  ELSE 0 END) AS C3,
  sum(CASE WHEN a.couponCount > 10 THEN 1  ELSE 0 END) AS C4
FROM (
       SELECT
         liushi.member_id,
         m.phone_no,
         CASE WHEN zhuanzheng.member_id is NULL AND hongbao.member_id is not NULL THEN 1
         WHEN zhuanzheng.member_id is NOT NULL AND hongbao.member_id is null THEN 0
         WHEN zhuanzheng.member_id is not null and hongbao.member_id is not null THEN 2
         END as type,
         (ifnull(zhuanzheng.zzCount,0)+ifnull(hongbao.hbCount,0)) as couponCount
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
                           AND o.dt > date_sub('2019-03-08', 30) -- 本月
                           AND o.dt <= '2019-03-08' -- 上月
                           AND o.dep_id NOT IN (SELECT department_id
                                                FROM dw_dim.t_test_shop)
                           AND m.status = 1
                           AND m.id IS NOT NULL
                       ) h ON h.member_id = o.member_id
             LEFT JOIN lucky_crm.t_member m ON o.member_id = m.id
           WHERE
             o.STATUS = 50
             -- 30天前下单用户
             AND o.dt <= date_sub('2019-03-08', 30)
             AND o.dep_id NOT IN (SELECT department_id
                                  FROM dw_dim.t_test_shop)
             AND m.status = 1
             AND m.id IS NOT NULL
             -- 不包含30天内下单的用户
             AND h.member_id IS NULL
           GROUP BY o.member_id
           HAVING count(DISTINCT o.id) > 0
         ) liushi
         LEFT JOIN
         -- 转赠
         (
           SELECT
             m.receiver_mem_id AS member_id,
             count(*)          AS zzCount
           FROM lucky_crm.t_coupon_transfer_record m
             LEFT JOIN lucky_coupon_order.t_mkt_coffee_coupon_send_record r
               ON r.coupon_record_no = m.coffee_coupon_no AND r.member_id = m.receiver_mem_id AND
                  r.lot_number = m.lot_number
           WHERE r.status = 1
                 AND m.receiver_time > date_sub('2019-03-08', 7)
                 AND m.receiver_time <= '2019-03-08'
                 AND r.id IS NOT NULL
           GROUP BY m.receiver_mem_id
         ) zhuanzheng ON zhuanzheng.member_id = liushi.member_id
         LEFT JOIN
         -- 红包
         (
           SELECT
             m.receiver_mem_id AS member_id,
             count(*)          AS hbCount
           FROM lucky_crm.t_lucky_money_record m
             LEFT JOIN lucky_coupon_order.t_mkt_coffee_coupon_send_record r
               ON r.coupon_record_no = m.coffee_coupon_no AND r.member_id = m.receiver_mem_id AND
                  r.lot_number = m.lot_number
           WHERE r.status = 1
                 AND m.receiver_time > date_sub('2019-03-08', 7)
                 AND m.receiver_time <= '2019-03-08'
                 AND r.id IS NOT NULL
           GROUP BY m.receiver_mem_id
         ) hongbao ON hongbao.member_id = liushi.member_id
         LEFT JOIN lucky_crm.t_member m ON m.id = liushi.member_id
       WHERE (hongbao.member_id is not null or zhuanzheng.member_id is not null)
             AND m.id is not null
     ) a
"""
var cursql = sqlmon.replace("2019-03-08",curDay);
print(curDay(i));
spark.sql(cursql).show();