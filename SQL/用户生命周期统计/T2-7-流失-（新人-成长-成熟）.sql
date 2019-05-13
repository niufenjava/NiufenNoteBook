%livy.spark
// var last_month = Array("2017-12-31","2018-01-31","2018-02-28","2018-03-31","2018-04-30","2018-05-31","2018-06-30","2018-07-31");
// var month = Array("2018-01-31","2018-02-28","2018-03-31","2018-04-30","2018-05-31","2018-06-30","2018-07-31","2018-08-31");
// var last_month = Array("2018-08-31","2018-09-30","2018-10-31","2018-11-30","2018-12-31","2019-01-31");
// var month = Array("2018-09-30","2018-10-31","2018-11-30","2018-12-31","2019-01-31","2019-02-28");
var last_month = Array("2017-12-31","2018-01-31");
var month = Array("2018-01-31","2018-02-28");
var sqlmon = """
SELECT
  -- 新人期：非流失用户，且历史总订单数<=3
  sum(case when t.memcount<=3 then 1 else 0 end) as xinrenqi,
  -- 成长期：非流失用户，且历史总订单数<=10
  sum(case when t.memcount>3 and t.memcount<=10 then 1 else 0 end) as chengzhangqi,
  -- 成熟期：非流失用户，且历史总订单数>10
  sum(case when t.memcount>10 then 1 else 0 end) as chengshuqi
FROM (
       SELECT
         o.member_id,
         count(DISTINCT o.id) memcount
       FROM
         lucky_order.t_order o
         left join lucky_crm.t_member m on m.id = o.member_id
       WHERE
         o.STATUS = 50
         AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
         AND o.dt <= '2018-01-31'
         and m.status = 1
         and m.id is not null
       GROUP BY o.member_id
     ) t
  -- 非流失用户（历史30天下过单）
  LEFT JOIN (
              SELECT
                distinct o.member_id
              FROM
                lucky_order.t_order o
                left join lucky_crm.t_member m on m.id = o.member_id
              WHERE
                o.STATUS = 50
                AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
                AND o.dt > date_sub('2018-01-31',30)
                AND o.dt <= '2018-01-31'
                and m.status = 1
                and m.id is not null
            ) non on non.member_id = t.member_id
  -- 上个月流失
  LEFT JOIN (
        SELECT
            distinct o.member_id
        FROM
            lucky_order.t_order o
        -- 30天之内有下单用户
        LEFT JOIN (
               SELECT DISTINCT o.member_id
                          FROM
                            lucky_order.t_order o
                            LEFT JOIN lucky_crm.t_member m on m.id = o.member_id
                          WHERE
                            o.STATUS = 50
                            AND o.dt > date_sub('2017-12-31', 30) -- 本月
                            AND o.dt <= '2017-12-31' -- 上月
                            AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
                            and m.status = 1
                            and m.id is not null
                        ) h ON h.member_id = o.member_id
              LEFT JOIN lucky_crm.t_member m ON o.member_id = m.id
            WHERE
              o.STATUS = 50
               -- 30天前下单用户
              AND o.dt <= date_sub('2017-12-31', 30)
              AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
              AND m.status = 1
              AND m.id is not null
              -- 不包含30天内下单的用户
              AND h.member_id is null
            GROUP BY o.member_id
            HAVING count(DISTINCT o.id) > 0
  ) lastMonth on lastMonth.member_id = t.member_id
WHERE lastMonth.member_id IS NOT NULL
and non.member_id is not null
"""

 var i = 0;
 for (i <- 0.to(month.length-1)) {
    var cursql = sqlmon.replace("'2018-01-31'","'"+month(i)+"'").replace("'2017-12-31'","'"+last_month(i)+"'");
    // print(cursql);
     print(month(i));spark.sql(cursql).show();
 }