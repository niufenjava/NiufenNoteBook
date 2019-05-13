%livy.spark
// E1-流失客户-流失
var start = Array("2019-02-18","2019-02-19","2019-02-20","2019-02-21","2019-02-22","2019-02-23","2019-02-24","2018-12-17","2018-12-18","2018-12-19","2018-12-20","2018-12-21","2018-12-22","2018-12-23","2018-12-03","2018-12-04","2018-12-05","2018-12-06","2018-12-07","2018-12-08","2018-12-09");
var sqlmon = """
SELECT COUNT(1) FROM (
SELECT t.member_id
FROM
  (
    SELECT
      o.member_id,
      count(DISTINCT o.id)  memCount
    FROM
      lucky_order.t_order o
      LEFT JOIN lucky_order.t_order_commodity oc ON o.id = oc.order_id
      LEFT JOIN (
                  SELECT DISTINCT inOrder.member_id
                  FROM
                    lucky_order.t_order inOrder
                    LEFT JOIN lucky_order.t_order_commodity co ON inOrder.id = co.order_id
                  WHERE
                    inOrder.dt > date_sub('2018-01-31', 30) -- 上月
                    AND inOrder.dt <= '2018-01-31' -- 上月
                    AND inOrder.STATUS = 50
                    AND co.situation != 2
                    AND inOrder.dep_id != 4901
                    AND inOrder.dep_id != 325571
                ) t1 ON o.member_id = t1.member_id
    WHERE
      o.STATUS = 50
      AND o.dep_id != 4901
      AND o.dep_id != 325571
      AND o.dt <= date_sub('2018-01-31', 30) -- 上月
      AND oc.situation != 2
      AND t1.member_id IS NULL
    GROUP BY
      o.member_id
  ) t
  LEFT JOIN lucky_crm.t_member member ON t.member_id = member.id
WHERE
  member.STATUS = 1
HAVING
  (t.memCount > 0)
  )
"""

var i = 0;
for (i <- 0.to(start.length-1)) {
    var cursql = sqlmon.replace("2018-01-31",start(i));
    // print(cursql);
     print(start(i));
     spark.sql(cursql).show();
}