%livy.spark
// 新人-（新人-成长-成熟）
// 成长-（新人-成长-成熟）
// 成熟-（新人-成长-成熟）
// var last_month = Array("2017-12-31","2018-01-31","2018-02-28","2018-03-31","2018-04-30","2018-05-31","2018-06-30","2018-07-31");
// var month = Array("2018-01-31","2018-02-28","2018-03-31","2018-04-30","2018-05-31","2018-06-30","2018-07-31","2018-08-31");
// var last_month = Array("2018-08-31","2018-09-30","2018-10-31","2018-11-30","2018-12-31","2019-01-31");
// var month = Array("2018-09-30","2018-10-31","2018-11-30","2018-12-31","2019-01-31","2019-02-28");
var last_month = Array("2017-12-31","2018-01-31");
var month = Array("2018-01-31","2018-02-28");
var sqlmon = """
SELECT
  sum(CASE WHEN t.memcount <= 3 THEN 1 ELSE 0 END) AS xinrenqi,
  sum(CASE WHEN t.memcount > 3 AND t.memcount <= 10 THEN 1 ELSE 0 END) AS chengzhangqi,
  sum(CASE WHEN t.memcount > 10 THEN 1 ELSE 0 END) AS chengshuqi
FROM (
       SELECT
         DISTINCT o.member_id,
         count(DISTINCT o.id) memcount
       FROM lucky_order.t_order o
         LEFT JOIN lucky_crm.t_member m ON o.member_id = m.id
         LEFT JOIN (
                     -- 上月是 新人-成长-成熟
                     SELECT
                       DISTINCT t.member_id
                     FROM (
                            SELECT
                              o.member_id,
                              count(DISTINCT o.id) memcount
                            FROM lucky_order.t_order o
                              LEFT JOIN lucky_crm.t_member m ON o.member_id = m.id
                            WHERE
                              o.STATUS = 50
                              AND m.status = 1
                              AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
                              AND o.dt <= '2017-12-31'  -- 上月
                            GROUP BY
                              o.member_id
                          ) t
                       LEFT JOIN (
                                   SELECT
                                     DISTINCT o.member_id
                                   FROM
                                     lucky_order.t_order o
                                     LEFT JOIN lucky_crm.t_member m ON o.member_id = m.id
                                   WHERE
                                     o.STATUS = 50
                                     AND m.status = 1
                                     AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
                                     AND o.dt > date_sub('2017-12-31', 30)
                                     AND o.dt <= '2017-12-31'
                                 ) non ON non.member_id = t.member_id
                     WHERE
                       non.member_id IS NOT NULL AND
                       -- 新人期
                       t.memcount <= 3
                     -- 成长期
                     -- t.memcount > 3 AND t.memcount <= 10
                     -- 成熟期
                     -- t.memcount > 10
                   ) lastmonth ON lastmonth.member_id = m.id
       WHERE
         o.STATUS = 50
         AND m.status = 1
         AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
         -- 本月
         AND o.dt <= '2018-01-31'
         AND lastmonth.member_id IS NOT NULL
       GROUP BY
         o.member_id
     ) t
  LEFT JOIN (
              SELECT
                DISTINCT o.member_id
              FROM
                lucky_order.t_order o
                LEFT JOIN lucky_crm.t_member m ON o.member_id = m.id
              WHERE
                o.STATUS = 50
                AND m.status = 1
                AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
                AND o.dt > date_sub('2018-01-31', 30)
                AND o.dt <= '2018-01-31'
            ) non ON non.member_id = t.member_id
WHERE non.member_id IS NOT NULL

"""

 var i = 0;
 for (i <- 0.to(month.length-1)) {
    var cursql = sqlmon.replace("2018-01-31",month(i)).replace("2017-12-31",last_month(i));
    // print(cursql); print("-------------------------");
     print(month(i));spark.sql(cursql).show();
 }