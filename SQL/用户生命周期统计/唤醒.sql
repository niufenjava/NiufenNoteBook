%sql
-- 唤醒：非流失用户，但曾经为流失用户，且当月有下单
select count(distinct a1.member_id) from
-- 非流失
(
    SELECT
      distinct o.member_id
    FROM
      lucky_order.t_order o
      left join lucky_crm.t_member m on m.id = o.member_id
    WHERE
      o.STATUS = 50
    AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
    AND o.dt > date_sub('2018-01-31',30)
    AND o.dt <= '2018-01-31'
    and m.status = 1
    and m.id is not null
) a1
-- 曾经流失
left join
(
    SELECT
        distinct o.member_id
    FROM
      lucky_order.t_order o
      -- 30天之内有下单用户
      LEFT JOIN (
                  SELECT DISTINCT o.member_id
                  FROM
                    lucky_order.t_order o
                    LEFT JOIN lucky_crm.t_member m on m.id = o.member_id
                  WHERE
                    o.STATUS = 50
                    AND o.dt > date_sub('2018-01-31',60) -- 本月
                    AND o.dt <= date_sub('2018-01-31',30) -- 上月
                    AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
                    and m.status = 1
                    and m.id is not null
                ) h ON h.member_id = o.member_id
      LEFT JOIN lucky_crm.t_member m ON o.member_id = m.id
    WHERE
      o.STATUS = 50
       -- 30天前下单用户
      AND o.dt <= date_sub('2018-01-31',60)
      AND o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
      AND m.status = 1
      AND m.id is not null
      -- 不包含30天内下单的用户
      AND h.member_id is null
    GROUP BY o.member_id
    HAVING count(DISTINCT o.id) > 0
) a2 on a1.member_id = a2.member_id
-- 包含曾经流失用户
where a2.member_id is not null
