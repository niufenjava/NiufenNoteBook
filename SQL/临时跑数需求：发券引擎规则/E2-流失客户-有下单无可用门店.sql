%livy.spark
// E2-流失客户-有下单无可用门店
var start = Array("2019-02-18","2019-02-19","2019-02-20","2019-02-21","2019-02-22","2019-02-23","2019-02-24","2018-12-17","2018-12-18","2018-12-19","2018-12-20","2018-12-21","2018-12-22","2018-12-23","2018-12-03","2018-12-04","2018-12-05","2018-12-06","2018-12-07","2018-12-08","2018-12-09");
var sqlmon = """
SELECT count(DISTINCT r.recorddesc_login_id) FROM  hbase.t_api_record r
LEFT JOIN
(
  -- 流失用户，最后一次下单时间
  SELECT
    lastOrder.member_id,
    from_unixtime(unix_timestamp(lastOrder.lastTime),'yyyy-MM-dd') dt
  FROM (
         -- 流失用户
         SELECT t.member_id
         FROM
           (
             SELECT
               o.member_id,
               count(DISTINCT o.id) memCount
             FROM
               lucky_order.t_order o
               LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
               LEFT JOIN (
                           SELECT DISTINCT inOrder.member_id
                           FROM
                             lucky_order.t_order inOrder
                           WHERE
                             inOrder.dt > date_sub('2018-12-03', 30) -- 上月
                             AND inOrder.dt <= '2018-12-03' -- 上月
                             AND inOrder.STATUS = 50
                         ) t1 ON o.member_id = t1.member_id
             WHERE
               o.STATUS = 50
               AND o.dt <= date_sub('2018-12-03', 30) -- 上月
               AND t1.member_id IS NULL
             GROUP BY
               o.member_id
           ) t
           LEFT JOIN lucky_crm.t_member member ON t.member_id = member.id
         WHERE
           member.STATUS = 1
         HAVING
           (t.memCount > 0)
       ) liushiOrder
    -- 最后一单时间
    LEFT JOIN (
                SELECT
                  o2.member_id,
                  MAX(o2.create_time) AS lastTime
                FROM lucky_order.t_order o2
                WHERE o2.STATUS = 50
                GROUP BY o2.member_id
              ) lastOrder ON lastOrder.member_id = liushiOrder.member_id
) oo ON r.recorddesc_login_id = oo.member_id
where r.dt > oo.dt
      and r.recorddesc_login_id is not null
      and recorddesc_aid = '/resource/m/sys/base/homeshop'
      and r.recorddesc_response_body not like '%"nearShop"%'
"""

var i = 0;
for (i <- 0.to(start.length-1)) {
    var cursql = sqlmon.replace("2018-12-03",start(i));
    // print(cursql);
     print(start(i));
     spark.sql(cursql).show();
}