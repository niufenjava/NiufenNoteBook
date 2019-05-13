%livy.spark
// D2.用户未复购率-当日活跃用户数-第n天未复购
var start = Array("2019-02-18","2019-02-19","2019-02-20","2019-02-21","2019-02-22","2019-02-23","2019-02-24","2018-12-17","2018-12-18","2018-12-19","2018-12-20","2018-12-21","2018-12-22","2018-12-23");
var sqlmon = """
SELECT
  count(DISTINCT o.member_id)
FROM
  lucky_order.t_order o
  LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
  LEFT JOIN (
              SELECT
                DISTINCT o.member_id,
                count(DISTINCT o.id) memcount
              FROM
                lucky_order.t_order o
                LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                LEFT JOIN (
                            SELECT
                              DISTINCT o.member_id
                            FROM
                              lucky_order.t_order o
                              LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
                            WHERE
                              o.STATUS = 50
                              and dim.dt = "2019-02-18"
                          ) m ON m.member_id = o.member_id
              WHERE
                o.STATUS = 50
                and dim.dt > "2019-02-18"
                and datediff(dim.dt,"2019-02-18") <= 3
              GROUP BY o.member_id
              having memcount > 0
            ) om ON om.member_id = o.member_id
WHERE
  o.STATUS = 50
  and dim.dt = "2019-02-18"
  and om.member_id is null

"""

var i = 0;
for (i <- 0.to(start.length-1)) {
    var cursql = sqlmon.replace("2019-02-18",start(i));
    // print(cursql);
     print(start(i));
     spark.sql(cursql).show();
}