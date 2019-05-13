SELECT
  member.id AS member_id,
  -- 订单数-状态&各种类型
  ifnull(orderStatusAndType.order_count,0) AS order_count,
  ifnull(orderStatusAndType.order_canceled_count,0) AS order_canceled_count,
  ifnull(orderStatusAndType.order_channel_self_count,0) AS order_channel_self_count,
  ifnull(orderStatusAndType.order_channel_meituan_count,0) AS order_channel_meituan_count,
  ifnull(orderStatusAndType.order_eattype_dine_count,0) AS order_eattype_dine_count,
  ifnull(orderStatusAndType.order_eattype_pack_count,0) AS order_eattype_pack_count,
  ifnull(orderStatusAndType.order_type_takeout_count,0) AS order_type_takeout_count,
  ifnull(orderStatusAndType.order_type_self_count,0) AS order_type_self_count,

  -- 订单数-已完成-周期统计
  ifnull(orderCycle.order_week_avg,0.00) AS order_week_avg,
  ifnull(orderCycle.order_month_avg,0.00) AS order_month_avg,
  ifnull(orderCycle.order_seven_days_count,0) AS order_seven_days_count,
  ifnull(orderCycle.order_thirty_days_count,0) AS order_thirty_days_count,

  -- 订单-已完成-消费金额
  ifnull(orderCycle.order_pay_sum,0.00) AS order_pay_sum,
  ifnull(orderCycle.order_pay_week_avg,0.00) AS order_pay_week_avg,
  ifnull(orderCycle.order_pay_month_avg,0.00) AS order_pay_month_avg,
  ifnull(orderCycle.order_pay_seven_days_sum,0.00) AS order_pay_seven_days_sum,
  ifnull(orderCycle.order_pay_thirty_days_sum,0.00) AS order_pay_thirty_days_sum,

  -- 订单-已完成-折扣率
  ifnull(orderDiscount.order_discount_rate_avg,0.00) AS order_discount_rate_avg,
  ifnull(orderDiscount.order_discount_rate_seven_days_avg,0.00) AS order_discount_rate_seven_days_avg,
  ifnull(orderDiscount.order_discount_rate_thirty_days_avg,0.00) AS order_discount_rate_thirty_days_avg,
  ifnull(orderDiscount.order_discount_rate_sixty_days_avg,0.00) AS order_discount_rate_sixty_days_avg,

  -- 订单-已完成-最近一单
  lastOrder.order_last_time AS order_last_time,
  lastOrder.order_last_city_id AS order_last_city_id,
  lastOrder.order_last_shop_id AS order_last_shop_id,

  -- 订单-已完成-城市
  orderCity.order_city_count AS order_city_count,

  -- 订单-已完成-门店
  orderShop.order_shop_count AS order_shop_count
FROM lucky_crm.t_member member
  -- 订单数-状态&各种类型
  LEFT JOIN
  (
    SELECT
      o.member_id AS member_id,
      sum(CASE WHEN o.status = 50 THEN 1 ELSE 0 END) AS order_count,
      sum(CASE WHEN o.status = 0 THEN 1 ELSE 0 END) AS order_canceled_count,
      sum(CASE WHEN o.status = 50 AND (o.channel = 1 or o.channel is null) THEN 1 ELSE 0 END) AS order_channel_self_count,
      sum(CASE WHEN o.status = 50 AND o.channel = 2 THEN 1 ELSE 0 END) AS order_channel_meituan_count,
      sum(CASE WHEN o.status = 50 AND o.eat_type = 1 THEN 1 ELSE 0 END) AS order_eattype_dine_count,
      sum(CASE WHEN o.status = 50 AND o.eat_type = 2 THEN 1 ELSE 0 END) AS order_eattype_pack_count,
      sum(CASE WHEN o.status = 50 AND o.type = 1 THEN 1 ELSE 0 END) AS order_type_takeout_count,
      sum(CASE WHEN o.status = 50 AND o.type = 2 THEN 1 ELSE 0 END) AS order_type_self_count
    FROM lucky_order.t_order o
    WHERE
      o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
    GROUP BY o.member_id
  ) orderStatusAndType ON orderStatusAndType.member_id = member.id

  -- 订单数-已完成周期统计
  LEFT JOIN
  (
    SELECT
      o.member_id AS member_id,
      ROUND(count(*)/if(datediff('2019-03-18',(from_unixtime(unix_timestamp(min(o.create_time)),'yyyy-MM-dd')))/7 <1,1,datediff('2019-03-18',(from_unixtime(unix_timestamp(min(o.create_time)),'yyyy-MM-dd')))/7),2) AS order_week_avg,
      ROUND(count(*)/if(months_between('2019-03-18',from_unixtime(unix_timestamp(min(o.create_time)),'yyyy-MM-dd'))<1,1,months_between('2019-03-18',from_unixtime(unix_timestamp(min(o.create_time)),'yyyy-MM-dd')))) AS order_month_avg,
      SUM(CASE WHEN datediff('2019-03-18',o.dt) <7 THEN 1 ELSE 0 END ) order_seven_days_count,
      SUM(CASE WHEN datediff('2019-03-18',o.dt) <30 THEN 1 ELSE 0 END ) order_thirty_days_count,

      sum(f.invoice_money) AS order_pay_sum,
      ROUND(sum(f.invoice_money)/if(datediff('2019-03-18',(from_unixtime(unix_timestamp(min(o.create_time)),'yyyy-MM-dd')))/7 <1,1,datediff('2019-03-18',(from_unixtime(unix_timestamp(min(o.create_time)),'yyyy-MM-dd')))/7),2) AS order_pay_week_avg,
      ROUND(sum(f.invoice_money)/if(months_between('2019-03-18',from_unixtime(unix_timestamp(min(o.create_time)),'yyyy-MM-dd'))<1,1,months_between('2019-03-18',from_unixtime(unix_timestamp(min(o.create_time)),'yyyy-MM-dd')))) AS order_pay_month_avg,
      SUM(CASE WHEN datediff('2019-03-18',o.dt) <7 THEN f.invoice_money ELSE 0 END ) order_pay_seven_days_sum,
      SUM(CASE WHEN datediff('2019-03-18',o.dt) <30 THEN f.invoice_money ELSE 0 END ) order_pay_thirty_days_sum
    FROM lucky_order.t_order o
      LEFT JOIN lucky_order.t_order_finance f ON f.order_id = o.id
    WHERE
      o.status = 50 AND
      o.dep_id NOT IN (SELECT department_id FROM dw_dim.t_test_shop)
    GROUP BY o.member_id
  ) orderCycle ON orderCycle.member_id = member.id
  -- 订单数-已完成-折扣率
  LEFT JOIN
  (
    SELECT
      c.member_id AS member_id,
      ROUND((sum(case ((c.coffeestore_deal_money = 0 and c.pay_money > c.addition_money) or (c.coffeestore_share_money > 0)) when true then (c.pay_money + c.coffeestore_share_money) else 0 end)
             /sum(case ((c.coffeestore_deal_money = 0 and c.pay_money > c.addition_money) or (c.coffeestore_share_money > 0)) when true then (c.commodity_origin_money+c.addition_money) else 0 end)
            ),2) AS order_discount_rate_avg,
      ROUND((sum(case (((c.coffeestore_deal_money = 0 and c.pay_money > c.addition_money) or (c.coffeestore_share_money > 0)) and datediff('2019-03-18',o.dt) <7) when true then (c.pay_money + c.coffeestore_share_money) else 0 end)
             /sum(case (((c.coffeestore_deal_money = 0 and c.pay_money > c.addition_money) or (c.coffeestore_share_money > 0)) and datediff('2019-03-18',o.dt) <7) when true then (c.commodity_origin_money+c.addition_money) else 0 end)
            ),2) AS order_discount_rate_seven_days_avg,
      ROUND((sum(case (((c.coffeestore_deal_money = 0 and c.pay_money > c.addition_money) or (c.coffeestore_share_money > 0)) and datediff('2019-03-18',o.dt) <30) when true then (c.pay_money + c.coffeestore_share_money) else 0 end)
             /sum(case (((c.coffeestore_deal_money = 0 and c.pay_money > c.addition_money) or (c.coffeestore_share_money > 0)) and datediff('2019-03-18',o.dt) <30) when true then (c.commodity_origin_money+c.addition_money) else 0 end)
            ),2) AS order_discount_rate_thirty_days_avg,
      ROUND((sum(case (((c.coffeestore_deal_money = 0 and c.pay_money > c.addition_money) or (c.coffeestore_share_money > 0)) and datediff('2019-03-18',o.dt) <60) when true then (c.pay_money + c.coffeestore_share_money) else 0 end)
             /sum(case (((c.coffeestore_deal_money = 0 and c.pay_money > c.addition_money) or (c.coffeestore_share_money > 0)) and datediff('2019-03-18',o.dt) <60) when true then (c.commodity_origin_money+c.addition_money) else 0 end)
            ),2) AS order_discount_rate_sixty_days_avg
    FROM lucky_order.t_order_commodity c
      LEFT JOIN lucky_order.t_order o on c.order_id = o.id
    where o.status = 50 and
          c.situation != 2 and
          o.dep_id not in (select department_id from dw_dim.t_test_shop)
    GROUP BY c.member_id
  ) orderDiscount ON orderDiscount.member_id = member.id

  -- 最后一单数据
  LEFT JOIN
  (
    SELECT
      o.member_id,
      o.create_time AS order_last_time,
      o.city_id AS order_last_city_id,
      o.dep_id AS order_last_shop_id
    FROM
      (
        SELECT o.member_id, max(o.finish_time) finish_time
        FROM
          lucky_order.t_order o
        WHERE
          STATUS = 50 AND
          o.dep_id not in (select department_id from dw_dim.t_test_shop)
        GROUP BY
          o.member_id
      ) b
      LEFT JOIN lucky_order.t_order o ON b.member_id = o.member_id AND b.finish_time = o.finish_time
  ) lastOrder ON lastOrder.member_id = member.id
  -- 订单-已完成-下单城市总数
  LEFT JOIN
  (
    select distinct o.member_id as member_id,count(1) as order_city_count FROM (
        SELECT
          o.member_id AS member_id,
          o.city_id AS city_id
        FROM lucky_order.t_order o
        WHERE o.status = 50 AND
              o.type = 2 AND
              o.dep_id not in (select department_id from dw_dim.t_test_shop)
        GROUP BY o.member_id,o.city_id
    )o group by o.member_id
  ) orderCity ON orderCity.member_id = member.id
  -- 订单-已完成-门店
  LEFT JOIN
  (
    SELECT distinct o.member_id as member_id,count(1) as order_shop_count FROM (
        SELECT
          o.member_id AS member_id,
          o.dep_id AS order_shop_count
        FROM lucky_order.t_order o
        WHERE o.status = 50 AND
              o.type = 2 AND
              o.dep_id not in (select department_id from dw_dim.t_test_shop)
        GROUP BY o.member_id,o.dep_id
    )o group by o.member_id
  ) orderShop ON orderShop.member_id = member.id