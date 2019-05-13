SELECT
  d.*,
  to_date(p.first_order_time)                                                                AS first_order_date,
  to_date(p.created_time)                                                                    AS created_date,
  datediff(cast(d.order_date AS string), cast(to_date(p.first_order_time) AS string))        AS relative_day,
  (CASE w1.weekday
   WHEN 7
     THEN (w1.weeknum - 1)
   ELSE w1.weeknum END) - (CASE w.weekday
                           WHEN 7
                             THEN (w.weeknum - 1)
                           ELSE w.weeknum END) + (year(d.order_date) - w.timeflag_year) * 52 AS relative_week,
  (year(d.order_date) - w.timeflag_year) * 12 + (month(d.order_date) - w.timeflag_month)     AS relative_month,
  (year(d.order_date) - w.timeflag_year)                                                     AS relative_year,
  (CASE d.order_num
   WHEN 0
     THEN 0
   ELSE 1 END)                                                                               AS v_is_order,
  (CASE d.zeropay_num
   WHEN d.order_num
     THEN 0
   ELSE 1 END)                                                                               AS v_is_pay
FROM
  (
    SELECT
      o.member_id,
      to_date(o.create_time)                           AS order_date,
      count(*)                                         AS order_num,
      sum(o.v_is_discount)                             AS discount_num,
      sum(o.commodityNum)                              AS comdty_num,
      sum(o.v_real_money)                              AS real_money_sum,
      sum(o.v_discount_money)                          AS discount_money_sum,
      sum(o.v_need_recv_money)                         AS order_money_sum,
      sum(CASE o.delivery_money IS NULL
          WHEN TRUE
            THEN 0
          ELSE o.delivery_money END)                   AS delivery_money_sum,
      sum(o.commodityIncomme)                          AS real_cmd_money_sum,
      sum(o.commodityOriginMoney) + sum(additionMoney) AS cmd_money_sum,
      sum(o.type % 2)                                  AS takeout_num,
      sum(o.v_is_zero_pay)                             AS zeropay_num
    FROM
      (
        SELECT
          t_order_all.id,
          t_order_all.member_id,
          t_order_all.create_time,
          t_order_all.v_real_money,
          t_order_all.v_discount_money,
          t_order_all.v_is_discount,
          t_order_all.commodity_num,
          t_order_all.type,
          t_order_all.v_is_zero_pay,
          t_order_all.delivery_money,
          t_order_all.v_need_recv_money,
          commodityNum,
          commodityOriginMoney,
          additionMoney,
          commodityIncomme,
          shareMoney
        FROM
          kylin_view.t_order_all t_order_all
          LEFT JOIN (
                      SELECT
                        order_id,
                        count(1)                                commodityNum,
                        sum((CASE commodity_origin_money IS NULL
                             WHEN TRUE
                               THEN 0
                             ELSE commodity_origin_money END))  commodityOriginMoney,
                        sum((CASE addition_money IS NULL
                             WHEN TRUE
                               THEN 0
                             ELSE addition_money END))          additionMoney,
                        sum((CASE commodity_income IS NULL
                             WHEN TRUE
                               THEN ((CASE pay_money IS NULL
                                      WHEN TRUE
                                        THEN 0
                                      ELSE pay_money END) + (CASE coffeestore_share_money IS NULL
                                                             WHEN TRUE
                                                               THEN 0
                                                             ELSE coffeestore_share_money END))
                             ELSE commodity_income END))        commodityIncomme,
                        sum((CASE coffeestore_share_money IS NULL
                             WHEN TRUE
                               THEN 0
                             ELSE coffeestore_share_money END)) shareMoney
                      FROM lucky_order.t_order_commodity
                      WHERE situation != 2
                      GROUP BY order_id
                    ) a ON t_order_all.id = a.order_id
        WHERE t_order_all.status = 50
      ) o
    GROUP BY o.member_id, to_date(o.create_time)
  ) d
  LEFT JOIN lucky_crm.t_member_profile p ON d.member_id = p.mem_id
  LEFT JOIN dw_dim.date_dim w ON to_date(p.first_order_time) = w.dt
  LEFT JOIN dw_dim.date_dim w1 ON d.order_date = w1.dt
WHERE p.first_order_time IS NOT NULL
