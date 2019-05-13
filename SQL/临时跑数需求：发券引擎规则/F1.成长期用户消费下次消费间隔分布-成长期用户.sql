%livy.spark
// F.成长期用户消费下次消费间隔分布-成长期用户
var start = Array("2018-12-17","2018-12-18","2018-12-19","2018-12-20","2018-12-21","2018-12-22","2018-12-23","2018-12-03","2018-12-04","2018-12-05","2018-12-06","2018-12-07","2018-12-08","2018-12-09","2019-02-18","2019-02-19","2019-02-20","2019-02-21","2019-02-22","2019-02-23","2019-02-24");
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
       WHERE
         o.STATUS = 50
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
              WHERE
                o.STATUS = 50
                AND dim.dt > date_sub('2018-01-31',30 )
                AND dim.dt <= '2018-01-31'
            ) non on non.member_id = t.member_id
  LEFT JOIN lucky_crm.t_member member ON member.id = t.member_id
WHERE non.member_id IS NOT NULL
      AND member.id is not null
      AND member.status = 1
      AND t.memcount>3 and t.memcount<=10
"""

var i = 0;
for (i <- 0.to(start.length-1)) {
    var cursql = sqlmon.replace("2018-01-31",start(i));
    // print(cursql);
     print(start(i));
     spark.sql(cursql).show();
}