%livy.spark
// G2. 成熟用户消费分布-成熟用户
 var start = Array("2018-11-01","2018-12-01","2019-01-01","2019-02-01");
 var end = Array("2018-11-30","2018-12-31","2019-01-31","2019-02-28");
 var days = Array("30","31","31","28");
var sqlmon = """
SELECT
  -- 周均消费<1
  sum(case when dangyue.memcount<1 then 1 else 0 end) as C,
  -- 周均消费>=1&<2
  sum(case when dangyue.memcount>=1 and dangyue.memcount<2 then 1 else 0 end) as E,
  -- 周均消费>=2&<3
  sum(case when dangyue.memcount>=2 and dangyue.memcount<3 then 1 else 0 end) as G,
  -- 周均消费>=3&<4
  sum(case when dangyue.memcount>=3 and dangyue.memcount<4 then 1 else 0 end) as I,
  -- 周均消费>=4&<5
  sum(case when dangyue.memcount>=4 and dangyue.memcount<5 then 1 else 0 end) as K,
  -- 周均消费>=5
  sum(case when dangyue.memcount>=5 then 1 else 0 end) as M
FROM (
       -- 成熟用户
       SELECT
         DISTINCT t.member_id
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
                AND dim.dt <= '2018-11-30'
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
                       AND dim.dt > date_sub('2018-11-30',30)
                       AND dim.dt <= '2018-11-30'
                   ) non on non.member_id = t.member_id
         LEFT JOIN lucky_crm.t_member member ON member.id = t.member_id
       WHERE non.member_id IS NOT NULL
             AND member.id is not null
             AND member.status = 1
             AND t.memcount>10
     ) chengshu
  -- 当月消费订单数 *7/当月天数
  LEFT JOIN (
              SELECT
                o.member_id,
                count(DISTINCT o.id)*7/days as memcount
              FROM
                lucky_order.t_order o
                LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
              WHERE
                o.STATUS = 50
                AND o.dep_id != 4901
                AND o.dep_id != 325571
                AND oc.situation != 2
                AND dim.dt >= '2018-11-01'
                AND dim.dt <= '2018-11-30'
              GROUP BY o.member_id
            ) dangyue ON chengshu.member_id = dangyue.member_id
WHERE dangyue.member_id IS NOT NULL
"""
var i = 0;
for (i <- 0.to(start.length-1)) {
    var cursql = sqlmon.replace("2018-11-01",start(i)).replace("2018-11-30",end(i).replace("days",days(i));
     print(cursql);
     //print(start(i));
    // spark.sql(cursql).show();
}