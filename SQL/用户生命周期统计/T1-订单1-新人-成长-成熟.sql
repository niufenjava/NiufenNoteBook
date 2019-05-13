%livy.spark
//var startMonth = Array("2018-08-01","2018-09-01","2018-10-01","2018-11-01","2018-12-01","2019-01-01","2019-02-01");
//ar month = Array("2018-08-31","2018-09-30","2018-10-31","2018-11-30","2018-12-31","2019-01-31","2019-02-28");
var startMonth = Array("2018-01-01","2018-02-01","2018-03-01","2018-04-01","2018-05-01","2018-06-01","2018-07-01");
var month = Array("2018-01-31","2018-02-28","2018-03-31","2018-04-30","2018-05-31","2018-06-30","2018-07-31");
var sqlmon = """
SELECT count(DISTINCT o.id)
    FROM
  lucky_order.t_order o
  LEFT JOIN lucky_crm.t_member t on t.id = o.member_id
  -- 新人期
  LEFT JOIN (
    SELECT
        distinct t.member_id
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
    WHERE non.member_id IS NOT NULL
          -- 新人期
           t.memcount <= 3
          -- 成长期
          -- t.memcount > 3 AND t.memcount <= 10
          -- 成熟期
          -- t.memcount > 10
)cs ON cs.member_id = o.member_id
WHERE
  o.STATUS = 50
  AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
  and o.dt >= '2018-01-01'
  and o.dt <= '2018-01-31'
  AND cs.member_id IS NOT NULL
"""

var i = 0;
for (i <- 0.to(month.length-1)) {
    var cursql = sqlmon.replace("2018-01-31",month(i)).replace("2018-01-01",startMonth(i));
   // print(cursql);
   // print("----------------------------");
    print(month(i)); spark.sql(cursql).show()
}