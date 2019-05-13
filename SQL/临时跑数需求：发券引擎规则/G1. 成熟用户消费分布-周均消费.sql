%livy.spark
// G1. 成熟用户消费分布-成熟用户
var start = Array("2018-11-30","2018-12-31","2019-01-31","2019-02-28");
var sqlmon = """
SELECT
  COUNT(*)
FROM (
       SELECT
         o.member_id,
         count(DISTINCT o.id) memcount
       FROM
         lucky_order.t_order o
         LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
         LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
       WHERE
         o.STATUS = 50
         AND o.dep_id != 4901
         AND o.dep_id != 325571
         AND oc.situation != 2
         AND dim.dt <= '2018-01-31'
       GROUP BY o.member_id
     ) t
  -- 非流失用户（历史30天下过单）
  LEFT JOIN (
              SELECT
                distinct o.member_id
              FROM
                lucky_order.t_order o
                LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
              WHERE
                o.STATUS = 50
                AND o.dep_id != 4901
                AND o.dep_id != 325571
                AND oc.situation != 2
                AND dim.dt > date_sub('2018-01-31',30 )
                AND dim.dt <= '2018-01-31'
            ) non on non.member_id = t.member_id
  LEFT JOIN lucky_crm.t_member member ON member.id = t.member_id
WHERE non.member_id IS NOT NULL
      AND member.id is not null
      AND member.status = 1
AND t.memcount>10
"""

var i = 0;
for (i <- 0.to(start.length-1)) {
    var cursql = sqlmon.replace("2018-01-31",start(i));
    // print(cursql);
     print(start(i));
     spark.sql(cursql).show();
}