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