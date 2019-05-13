%livy.spark
// B4. 新注册用户首单未消费用户数量-20190217-20190223
var start = Array("2019-02-17");
var end   = Array("2019-02-23");
var days =  Array("1","2","3","4","5","6","7","8");
var sqlmon = """
select  count(*) from lucky_crm.t_member t
left join lucky_crm.t_member_profile p on p.mem_id = t.id
where t.created_time >='2018-10-28 00:00:00'
and t.created_time <='2018-11-03 23:59:59'
-- and t.status = 1
and (p.first_order_time is null or datediff(p.first_order_time,t.created_time) >= days)
"""

var i = 0;
for (i <- 0.to(days.length-1)) {
    var cursql = sqlmon.replace("2018-10-28",start(i)).replace("2018-11-03",end(i)).replace("days",days(i));
    // print(cursql);
     print(days(i));
     spark.sql(cursql).show();
}