%livy.spark
//var month = Array("2018-08-31","2018-09-30","2018-10-31","2018-11-30","2018-12-31","2019-01-31","2019-02-28");
var month = Array("2018-01-31","2018-02-28","2018-03-31","2018-04-30","2018-05-31","2018-06-30","2018-07-31");
var sqlmon = """
SELECT count(DISTINCT his.member_id)
FROM (
       SELECT
         o.member_id,
         count(DISTINCT o.id) memcount,
         MIN(o.create_time) AS CT
       FROM
         lucky_order.t_order o
       WHERE
         o.STATUS = 50
        AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
         AND o.dt <= '2018-01-31'
       GROUP BY
         o.member_id
           HAVING memcount > 10
     ) his
  left join (
    SELECT
      distinct o.member_id
    FROM
      lucky_order.t_order o
    WHERE
      o.STATUS = 50
      AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
      AND o.dt > date_sub('2018-01-31',30 )
      AND o.dt <= '2018-01-31'
    ) non on non.member_id = his.member_id
  left join lucky_crm.t_member t on t.id = his.member_id
WHERE non.member_id IS NOT NULL
and t.status = 1
and t.id is not null
and (his.memcount/months_between('2018-01-31 23:59:59',his.CT)) >=8
"""

var i = 0;
for (i <- 0.to(month.length-1)) {
    var cursql = sqlmon.replace("2018-01-31",month(i));
    // print(cursql);print("----------------------------");
     print(month(i));spark.sql(cursql).show();
}