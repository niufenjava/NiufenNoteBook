%livy.spark
// var year_d = Array("2018","2018","2018","2018","2018","2018","2018","2018","2018","2018","2018","2018","2019","2019");
// var month_d = Array("01","02","03","04","05","06","07","08","09","10","11","12","01","02");
// var month = Array("2018-01-31","2018-02-28","2018-03-31","2018-04-30","2018-05-31","2018-06-30","2018-07-31","2018-08-31","2018-09-30","2018-10-31","2018-11-30","2018-12-31","2019-01-31","2019-02-28");
var year_d = Array("2018","2018");
var month_d = Array("01","02");
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
      count(DISTINCT o.id) memcount,
      min(o.dt) as dt
    FROM
      lucky_order.t_order o
    LEFT JOIN lucky_crm.t_member m ON o.member_id = m.id
    WHERE
      o.STATUS = 50
    AND m.status = 1
    AND m.id is not null
    AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
    AND o.dt <= '2018-01-31'
    GROUP BY o.member_id
    ) t
LEFT JOIN lucky_crm.t_member m ON m.id = t.member_id
WHERE 1=1
AND month(t.dt) = month_d
AND year(t.dt) = year_d
AND m.id is not null
AND m.status = 1
"""

 var i = 0;
 for (i <- 0.to(month.length-1)) {
    var cursql = sqlmon.replace("'2018-01-31'","'"+month(i)+"'").replace("year_d",year_d(i)).replace("month_d",month_d(i));
    print(month(i)); spark.sql(cursql).show()
 }