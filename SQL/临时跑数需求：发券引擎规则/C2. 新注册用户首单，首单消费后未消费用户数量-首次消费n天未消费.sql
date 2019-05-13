%livy.spark
// C2. 新注册用户首单，首单消费后未消费用户数量-首次消费n天未消费
var start = Array("2018-10-28","2018-12-02","2018-12-09","2018-12-16","2019-01-13","2019-02-17");
var end   = Array("2018-11-03","2018-12-08","2018-12-15","2018-12-22","2019-01-19","2019-02-23");
var sqlmon = """
SELECT
  count(DISTINCT t.id)
FROM lucky_crm.t_member t
  LEFT JOIN lucky_crm.t_member_profile p ON p.mem_id = t.id
  LEFT JOIN (
              SELECT
                DISTINCT o.member_id,
                count(DISTINCT o.id) memcount
              FROM
                lucky_order.t_order o
                LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                LEFT JOIN (
                            select  t.id,p.first_order_time from lucky_crm.t_member t
                              left join lucky_crm.t_member_profile p on p.mem_id = t.id
                            where 1=1
                                  and p.first_order_time >='2018-10-28 00:00:00'
                                  and p.first_order_time <='2018-11-03 23:59:59'
                                  -- and t.status = 1
                                  and p.first_order_time is not null
                          ) m ON m.id = o.member_id
              WHERE
                o.STATUS = 50
                and dim.dt > from_unixtime(unix_timestamp(m.first_order_time),'yyyy-MM-dd')
                and datediff(dim.dt,from_unixtime(unix_timestamp(m.first_order_time),'yyyy-MM-dd')) <= 1
              GROUP BY o.member_id
              having memcount > 0
            ) mo on mo.member_id = t.id
WHERE 1 = 1
      AND p.first_order_time >= '2018-10-28 00:00:00'
      AND p.first_order_time <= '2018-11-03 23:59:59'
      -- AND t.status = 1
      AND p.first_order_time IS NOT NULL
      AND mo.member_id IS NULL

"""

var i = 0;
for (i <- 0.to(start.length-1)) {
    var cursql = sqlmon.replace("2018-10-28",start(i)).replace("2018-11-03",end(i));
    // print(cursql);
     print(start(i));
     spark.sql(cursql).show();
}