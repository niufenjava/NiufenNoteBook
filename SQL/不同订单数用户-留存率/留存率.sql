%livy.spark
var month = Array("2019-02-24","2019-02-23","2019-02-22","2019-02-21","2019-02-20","2019-02-19","2019-02-18","2018-12-23","2018-12-22","2018-12-21","2018-12-20","2018-12-19","2018-12-18");
var sqlmon = """
SELECT
  sum(case when base.memcount=2 then 1 else 0 end) as G2,
  sum(case when base.memcount=3 then 1 else 0 end) as G3,
  sum(case when base.memcount=4 then 1 else 0 end) as G4,
  sum(case when base.memcount=5 then 1 else 0 end) as G5,
  sum(case when base.memcount=6 then 1 else 0 end) as G6,
  sum(case when base.memcount=7 then 1 else 0 end) as G7,
  sum(case when base.memcount=8 then 1 else 0 end) as G8,
  sum(case when base.memcount=9 then 1 else 0 end) as G9,
  sum(case when base.memcount=10 then 1 else 0 end) as G10,
  sum(case when base.memcount=11 then 1 else 0 end) as G11,
  sum(case when base.memcount=12 then 1 else 0 end) as G12,
  sum(case when base.memcount=13 then 1 else 0 end) as G13,
  sum(case when base.memcount=14 then 1 else 0 end) as G14,
  sum(case when base.memcount=15 then 1 else 0 end) as G15,
  sum(case when base.memcount=16 then 1 else 0 end) as G16,
  sum(case when base.memcount=17 then 1 else 0 end) as G17,
  sum(case when base.memcount=18 then 1 else 0 end) as G18,
  sum(case when base.memcount=19 then 1 else 0 end) as G19,
  sum(case when base.memcount=20 then 1 else 0 end) as G20
FROM (
       SELECT
         o.member_id,
         count(DISTINCT o.id) memcount
       FROM
         lucky_order.t_order o
         left join lucky_crm.t_member m on m.id = o.member_id
         -- 当日下单用户
         left join (
              SELECT
                distinct o.member_id
              FROM
                lucky_order.t_order o
                left join lucky_crm.t_member m on m.id = o.member_id
              WHERE
                o.STATUS = 50
                AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
                AND o.dt = '2019-02-24'
                and m.status = 1
         ) cur on cur.member_id = o.member_id
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
                AND o.dt >= date_sub('2019-02-24',30)
                AND o.dt < '2019-02-24'
                and m.status = 1
            ) non on non.member_id = o.member_id
       WHERE
         o.STATUS = 50
         AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
         AND o.dt < '2019-02-24'
         and m.status = 1
         -- 只包含当日下单用户
         and cur.member_id is not null
         -- 必须是非流失用户
         and non.member_id IS NOT NULL
       GROUP BY o.member_id
     ) base
union

SELECT
  sum(case when base.memcount=2 then 1 else 0 end) as G2,
  sum(case when base.memcount=3 then 1 else 0 end) as G3,
  sum(case when base.memcount=4 then 1 else 0 end) as G4,
  sum(case when base.memcount=5 then 1 else 0 end) as G5,
  sum(case when base.memcount=6 then 1 else 0 end) as G6,
  sum(case when base.memcount=7 then 1 else 0 end) as G7,
  sum(case when base.memcount=8 then 1 else 0 end) as G8,
  sum(case when base.memcount=9 then 1 else 0 end) as G9,
  sum(case when base.memcount=10 then 1 else 0 end) as G10,
  sum(case when base.memcount=11 then 1 else 0 end) as G11,
  sum(case when base.memcount=12 then 1 else 0 end) as G12,
  sum(case when base.memcount=13 then 1 else 0 end) as G13,
  sum(case when base.memcount=14 then 1 else 0 end) as G14,
  sum(case when base.memcount=15 then 1 else 0 end) as G15,
  sum(case when base.memcount=16 then 1 else 0 end) as G16,
  sum(case when base.memcount=17 then 1 else 0 end) as G17,
  sum(case when base.memcount=18 then 1 else 0 end) as G18,
  sum(case when base.memcount=19 then 1 else 0 end) as G19,
  sum(case when base.memcount=20 then 1 else 0 end) as G20
FROM (
       SELECT
         o.member_id,
         count(DISTINCT o.id) memcount
       FROM
         lucky_order.t_order o
         left join lucky_crm.t_member m on m.id = o.member_id
         -- 当日下单用户
         left join (
              SELECT
                distinct o.member_id
              FROM
                lucky_order.t_order o
                left join lucky_crm.t_member m on m.id = o.member_id
              WHERE
                o.STATUS = 50
                AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
                AND o.dt = '2019-02-24'
                and m.status = 1
         ) cur on cur.member_id = o.member_id
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
                AND o.dt >= date_sub('2019-02-24',30)
                AND o.dt < '2019-02-24'
                and m.status = 1
            ) non on non.member_id = o.member_id
         -- 后7日有下过单
         LEFT JOIN (
              SELECT
                distinct o.member_id
              FROM
                lucky_order.t_order o
                left join lucky_crm.t_member m on m.id = o.member_id
              WHERE
                o.STATUS = 50
                AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
                AND m.status = 1
                AND o.dt <= date_add('2019-02-24',7)
                AND o.dt > '2019-02-24'

            ) liucun on liucun.member_id = o.member_id
       WHERE
         o.STATUS = 50
         AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
         AND o.dt < '2019-02-24'
         and m.status = 1
         -- 只包含当日下单用户
         and cur.member_id is not null
         -- 必须是非流失用户
         and non.member_id IS NOT NULL
         -- 后7日下过单
         and liucun.member_id is not null
       GROUP BY o.member_id
     ) base
"""

var i = 0;
for (i <- 0.to(month.length-1)) {
    var cursql = sqlmon.replace("2019-02-24",month(i));
    // print(cursql);
     print(month(i));spark.sql(cursql).show();
}