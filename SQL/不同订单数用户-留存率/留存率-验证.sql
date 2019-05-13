select count(1) from
-- 当日下单的人
(
    SELECT
        distinct o.member_id
     FROM
        lucky_order.t_order o
    left join lucky_crm.t_member m on m.id = o.member_id
    WHERE
        o.STATUS = 50
        AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
        AND m.status = 1
        AND o.dt = '2019-02-24'
) cur
-- 人群2
LEFT JOIN
(
    SELECT
      distinct t.member_id
    FROM (
           SELECT
             o.member_id
           FROM
             lucky_order.t_order o
             left join lucky_crm.t_member m on m.id = o.member_id
           WHERE
             o.STATUS = 50
             AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
             AND o.dt < '2019-02-24'
             and m.status = 1
             and m.id is not null
           GROUP BY o.member_id
           having count(DISTINCT o.id) = 2
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
                    AND o.dt >= date_sub('2019-02-24',30)
                    AND o.dt < '2019-02-24'
                    and m.status = 1
                    and m.id is not null
                ) non on non.member_id = t.member_id
    WHERE non.member_id IS NOT NULL
) G2 ON G2.member_id = cur.member_id
where G2.member_id is not null
