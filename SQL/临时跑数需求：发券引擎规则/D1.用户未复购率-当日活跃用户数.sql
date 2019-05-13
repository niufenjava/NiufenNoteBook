%livy.spark
--D1.用户未复购率-当日活跃用户数
var sqlmon = """
SELECT
  dim.dt,
  count(DISTINCT o.member_id) memcount
FROM
  lucky_order.t_order o
  LEFT JOIN dw_dim.date_dim dim ON o.dt = dim.dt
WHERE
  o.STATUS = 50
  and dim.dt IN ("2019-02-18",
    "2019-02-19",
    "2019-02-20",
    "2019-02-21",
    "2019-02-22",
    "2019-02-23",
    "2019-02-24",
    "2018-12-17",
    "2018-12-18",
    "2018-12-19",
    "2018-12-20",
    "2018-12-21",
    "2018-12-22",
    "2018-12-23")
GROUP BY dim.dt
order by dim.dt desc
"""
 spark.sql(sqlmon).show();