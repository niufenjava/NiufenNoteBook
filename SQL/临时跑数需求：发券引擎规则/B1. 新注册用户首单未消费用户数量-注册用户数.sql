%livy.spark
// B. 新注册用户首单未消费用户数量-注册用户数
var start = Array("2018-10-28","2018-12-02","2018-12-09","2018-12-16","2019-01-13","2019-02-17");
var end   = Array("2018-11-03","2018-12-08","2018-12-15","2018-12-22","2019-01-19","2019-02-23");
var sqlmon = """
SELECT count(*)
FROM lucky_crm.t_member t
  LEFT JOIN lucky_crm.t_member_profile p ON t.id = p.mem_id
WHERE t.created_time >= '2018-10-28 00:00:00'
      AND t.created_time <= '2018-11-03 23:59:59'
     -- AND t.status = 1
"""

var i = 0;
for (i <- 0.to(start.length-1)) {
    var cursql = sqlmon.replace("2018-10-28",start(i)).replace("2018-11-03",end(i));
    // print(cursql);
     print(start(i));
     spark.sql(cursql).show();
}