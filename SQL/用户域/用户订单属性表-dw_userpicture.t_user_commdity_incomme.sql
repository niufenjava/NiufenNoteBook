SELECT
  m.id as member_id,
  IFNULL(cm3.order_receivable_sum_seven_days,0) + IFNULL(or3.delivery_receivable_sum_seven_days,0) as order_receivable_sum_seven_days,
  cm3.order_payable_sum_seven_days,
  cm3.commodity_receivable_sum_seven_days,
  cm3.commodity_payable_sum_seven_days,
  or3.delivery_payable_sum_seven_days,
  co3.coffeestore_payable_sum_seven_days,
  cm3.counpon_deduct_sum_seven_days,
  cm3.coffeestore_share_sum_seven_days,
  cm3.commodity_count_seven_days,
  cm3.drink_count_seven_days,
  cm3.food_count_seven_days,
  cm3.coffee_count_seven_days,
  cm3.coupon_use_count_seven_days,
  cm3.coffeestore_use_count_seven_days,
  cm3.coffeestore_free_use_count_seven_days,

  IFNULL(cm3.order_receivable_sum_fourteen_days,0) + IFNULL(or3.delivery_receivable_sum_fourteen_days,0) as order_receivable_sum_fourteen_days,
  cm3.order_payable_sum_fourteen_days,
  cm3.commodity_receivable_sum_fourteen_days,
  cm3.commodity_payable_sum_fourteen_days,
  or3.delivery_payable_sum_fourteen_days,
  co3.coffeestore_payable_sum_fourteen_days,
  cm3.counpon_deduct_sum_fourteen_days,
  cm3.coffeestore_share_money_fourteen_days,
  cm3.commodity_count_fourteen_days,
  cm3.drink_count_fourteen_days,
  cm3.food_count_fourteen_days,
  cm3.coffee_count_fourteen_days,
  cm3.coupon_use_count_fourteen_days,
  cm3.coffeestore_use_count_fourteen_days,
  cm3.coffeestore_free_use_count_fourteen_days,

  IFNULL(cm3.order_receivable_sum_thirty_days,0) + IFNULL(or3.delivery_receivable_sum_thirty_days,0) as order_receivable_sum_thirty_days,
  cm3.order_payable_sum_thirty_days,
  cm3.commodity_receivable_sum_thirty_days,
  cm3.commodity_payable_sum_thirty_days,
  or3.delivery_payable_sum_thirty_days,
  co3.coffeestore_payable_sum_thirty_days,
  cm3.counpon_deduct_sum_thirty_days,
  cm3.coffeestore_share_sum_thirty_days,
  cm3.commodity_count_thirty_days,
  cm3.drink_count_thirty_days,
  cm3.food_count_thirty_days,
  cm3.coffee_count_thirty_days,
  cm3.coupon_use_count_thirty_days,
  cm3.coffeestore_use_count_thirty_days,
  cm3.coffeestore_free_use_count_thirty_days,

  IFNULL(cm3.order_receivable_sum_three_months,0) + IFNULL(or3.delivery_receivable_sum_three_months,0) as order_receivable_sum_three_months,
  cm3.order_payable_sum_three_months,
  cm3.commodity_receivable_sum_three_months,
  cm3.commodity_payable_sum_three_months,
  or3.delivery_payable_sum_three_months,
  co3.coffeestore_payable_sum_three_months,
  cm3.counpon_deduct_sum_three_months,
  cm3.coffeestore_share_sum_three_months,
  cm3.commodity_count_three_months,
  cm3.drink_count_three_months,
  cm3.food_count_three_months,
  cm3.coffee_count_three_months,
  cm3.coupon_use_count_three_months,
  cm3.coffeestore_use_count_three_months,
  cm3.coffeestore_free_use_count_three_months,

  IFNULL(cm3.order_receivable_sum,0) + IFNULL(or3.delivery_receivable_sum,0) as order_receivable_sum,
  cm3.order_payable_sum,
  cm3.commodity_receivable_sum,
  cm3.commodity_payable_sum,
  or3.delivery_payable_sum,
  co3.coffeestore_payable_sum,
  cm3.counpon_deduct_sum,
  cm3.coffeestore_share_sum,
  cm3.commodity_count,
  cm3.drink_count,
  cm3.food_count,
  cm3.coffee_count,
  cm3.coupon_use_count,
  cm3.coffeestore_use_count,
  cm3.coffeestore_free_use_count
FROM  lucky_crm.t_member m
LEFT JOIN (
       SELECT
         cm2.member_id as member_id,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=7 THEN cm2.orderYs ELSE 0 END ) order_receivable_sum_seven_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=14 THEN cm2.orderYs ELSE 0 END ) order_receivable_sum_fourteen_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=30 THEN cm2.orderYs ELSE 0 END ) order_receivable_sum_thirty_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=90 THEN cm2.orderYs ELSE 0 END ) order_receivable_sum_three_months,
         SUM(cm2.orderYs ) order_receivable_sum,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=7 THEN cm2.orderSs ELSE 0 END ) order_payable_sum_seven_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=14 THEN cm2.orderSs ELSE 0 END ) order_payable_sum_fourteen_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=30 THEN cm2.orderSs ELSE 0 END ) order_payable_sum_thirty_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=90 THEN cm2.orderSs ELSE 0 END ) order_payable_sum_three_months,
         SUM(cm2.orderSs ) order_payable_sum,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=7 THEN cm2.commodityYs ELSE 0 END ) commodity_receivable_sum_seven_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=14 THEN cm2.commodityYs ELSE 0 END ) commodity_receivable_sum_fourteen_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=30 THEN cm2.commodityYs ELSE 0 END ) commodity_receivable_sum_thirty_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=90 THEN cm2.commodityYs ELSE 0 END ) commodity_receivable_sum_three_months,
         SUM(cm2.commodityYs ) commodity_receivable_sum,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=7 THEN cm2.commoditySs ELSE 0 END ) commodity_payable_sum_seven_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=14 THEN cm2.commoditySs ELSE 0 END ) commodity_payable_sum_fourteen_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=30 THEN cm2.commoditySs ELSE 0 END ) commodity_payable_sum_thirty_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=90 THEN cm2.commoditySs ELSE 0 END ) commodity_payable_sum_three_months,
         SUM(cm2.commoditySs ) commodity_payable_sum,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=7 THEN cm2.couponMoney ELSE 0 END ) counpon_deduct_sum_seven_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=14 THEN cm2.couponMoney ELSE 0 END ) counpon_deduct_sum_fourteen_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=30 THEN cm2.couponMoney ELSE 0 END ) counpon_deduct_sum_thirty_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=90 THEN cm2.couponMoney ELSE 0 END ) counpon_deduct_sum_three_months,
         SUM(cm2.couponMoney ) counpon_deduct_sum,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=7 THEN cm2.coffeestoreShare ELSE 0 END ) coffeestore_share_sum_seven_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=14 THEN cm2.coffeestoreShare ELSE 0 END ) coffeestore_share_money_fourteen_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=30 THEN cm2.coffeestoreShare ELSE 0 END ) coffeestore_share_sum_thirty_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=90 THEN cm2.coffeestoreShare ELSE 0 END ) coffeestore_share_sum_three_months,
         SUM(cm2.coffeestoreShare ) coffeestore_share_sum,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=7 THEN cm2.commodityNum ELSE 0 END ) commodity_count_seven_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=14 THEN cm2.commodityNum ELSE 0 END ) commodity_count_fourteen_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=30 THEN cm2.commodityNum ELSE 0 END ) commodity_count_thirty_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=90 THEN cm2.commodityNum ELSE 0 END ) commodity_count_three_months,
         SUM(cm2.commodityNum ) commodity_count,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=7 THEN cm2.commodityDrinkNum ELSE 0 END ) drink_count_seven_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=14 THEN cm2.commodityDrinkNum ELSE 0 END ) drink_count_fourteen_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=30 THEN cm2.commodityDrinkNum ELSE 0 END ) drink_count_thirty_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=90 THEN cm2.commodityDrinkNum ELSE 0 END ) drink_count_three_months,
         SUM(cm2.commodityDrinkNum ) drink_count,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=7 THEN cm2.commodityFoodNum ELSE 0 END ) food_count_seven_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=14 THEN cm2.commodityFoodNum ELSE 0 END ) food_count_fourteen_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=30 THEN cm2.commodityFoodNum ELSE 0 END ) food_count_thirty_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=90 THEN cm2.commodityFoodNum ELSE 0 END ) food_count_three_months,
         SUM(cm2.commodityFoodNum ) food_count,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=7 THEN cm2.commodityCoffeeNum ELSE 0 END ) coffee_count_seven_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=14 THEN cm2.commodityCoffeeNum ELSE 0 END ) coffee_count_fourteen_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=30 THEN cm2.commodityCoffeeNum ELSE 0 END ) coffee_count_thirty_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=90 THEN cm2.commodityCoffeeNum ELSE 0 END ) coffee_count_three_months,
         SUM(cm2.commodityCoffeeNum ) coffee_count,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=7 THEN cm2.useCouponNum ELSE 0 END ) coupon_use_count_seven_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=14 THEN cm2.useCouponNum ELSE 0 END ) coupon_use_count_fourteen_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=30 THEN cm2.useCouponNum ELSE 0 END ) coupon_use_count_thirty_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=90 THEN cm2.useCouponNum ELSE 0 END ) coupon_use_count_three_months,
         SUM(cm2.useCouponNum ) coupon_use_count,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=7 THEN cm2.useCoffeeStoreNum ELSE 0 END ) coffeestore_use_count_seven_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=14 THEN cm2.useCoffeeStoreNum ELSE 0 END ) coffeestore_use_count_fourteen_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=30 THEN cm2.useCoffeeStoreNum ELSE 0 END ) coffeestore_use_count_thirty_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=90 THEN cm2.useCoffeeStoreNum ELSE 0 END ) coffeestore_use_count_three_months,
         SUM(cm2.useCoffeeStoreNum ) coffeestore_use_count,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=7 THEN cm2.useFreeCoffeeStoreNum ELSE 0 END ) coffeestore_free_use_count_seven_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=14 THEN cm2.useFreeCoffeeStoreNum ELSE 0 END ) coffeestore_free_use_count_fourteen_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=30 THEN cm2.useFreeCoffeeStoreNum ELSE 0 END ) coffeestore_free_use_count_thirty_days,
         SUM(CASE WHEN datediff('2019-04-25',cm2.dt) <=90 THEN cm2.useFreeCoffeeStoreNum ELSE 0 END ) coffeestore_free_use_count_three_months,
         SUM(cm2.useFreeCoffeeStoreNum ) coffeestore_free_use_count
       FROM
         (SELECT
            cm1.member_id AS member_id,
            IFNULL(cm1.commodity_origin_money, 0) + IFNULL(cm1.addition_money, 0) AS orderYs,
            IFNULL(cm1.pay_money, 0) + IFNULL(cm1.coffeestore_share_money, 0) AS orderSs,
            CASE WHEN cm1.situation != 2 THEN (IFNULL(cm1.commodity_origin_money, 0) + IFNULL(cm1.addition_money, 0) ) ELSE 0 END AS commodityYs,
            CASE WHEN cm1.situation != 2 THEN (IFNULL(cm1.pay_money, 0) + IFNULL(cm1.coffeestore_share_money, 0) ) ELSE 0 END AS commoditySs,
            IFNULL(cm1.coupon_money, 0) AS couponMoney,
            IFNULL(cm1.coffeestore_share_money, 0) AS coffeestoreShare,
            CASE WHEN cm1.situation != 2 THEN 1 ELSE 0 END AS commodityNum,
            CASE WHEN cm1.situation != 2 AND cm1.commodity_mode_id = 1 and cm1.one_category_name='饮品' THEN 1 ELSE 0 END AS commodityDrinkNum,
            CASE WHEN cm1.situation != 2 AND cm1.commodity_mode_id = 1 and cm1.one_category_name='食品' THEN 1 ELSE 0 END AS commodityFoodNum,
            CASE WHEN cm1.situation != 2 AND cm1.commodity_mode_id = 0 THEN 1 ELSE 0 END AS commodityCoffeeNum,
            CASE WHEN couponsend.id IS NOT NULL THEN 1 ELSE 0 END AS useCouponNum,
            CASE WHEN coffeestoresend.id IS NOT NULL THEN 1 ELSE 0 END AS useCoffeeStoreNum,
            CASE WHEN coffeestoresend.id IS NOT NULL AND (cm1.coffeestore_share_money = 0 or cm1.coffeestore_share_money is null) THEN 1 ELSE 0 END AS useFreeCoffeeStoreNum,
            cm1.dt AS dt
          FROM
            dw_lucky_order.t_order_commodity cm1
            LEFT JOIN lucky_order.t_order_coupon coupon ON ( cm1.coupon_id = coupon.coupon_id AND cm1.order_id = coupon.order_id)
            LEFT JOIN lucky_marketing.t_mkt_coupon_send_record couponsend ON coupon.coupon_record_no = couponsend.coupon_record_no
            LEFT JOIN lucky_order.t_order_coffeestore coffeestore ON cm1.id = coffeestore.order_commodity_id
            LEFT JOIN lucky_coupon_order.t_mkt_coffee_coupon_send_record coffeestoresend ON coffeestore.coffeestore_no = coffeestoresend.coupon_record_no
          WHERE
            cm1.STATUS = 50 and cm1.situation != 2
            AND cm1.dep_id not in (select department_id from dw_dim.t_test_shop)
         ) cm2
       GROUP BY cm2.member_id
     ) cm3 ON cm3.member_id = m.id
  LEFT JOIN (
              SELECT
                or2.order_member_id as member_id,
                SUM(CASE WHEN datediff('2019-04-25',or2.dt) <=7 THEN or2.deliveryGmv ELSE 0 END ) delivery_receivable_sum_seven_days,
                SUM(CASE WHEN datediff('2019-04-25',or2.dt) <=14 THEN or2.deliveryGmv ELSE 0 END ) delivery_receivable_sum_fourteen_days,
                SUM(CASE WHEN datediff('2019-04-25',or2.dt) <=30 THEN or2.deliveryGmv ELSE 0 END ) delivery_receivable_sum_thirty_days,
                SUM(CASE WHEN datediff('2019-04-25',or2.dt) <=90 THEN or2.deliveryGmv ELSE 0 END ) delivery_receivable_sum_three_months,
                SUM(or2.deliveryGmv ) delivery_receivable_sum,
                SUM(CASE WHEN datediff('2019-04-25',or2.dt) <=7 THEN or2.deliveryPayMoney ELSE 0 END ) delivery_payable_sum_seven_days,
                SUM(CASE WHEN datediff('2019-04-25',or2.dt) <=14 THEN or2.deliveryPayMoney ELSE 0 END ) delivery_payable_sum_fourteen_days,
                SUM(CASE WHEN datediff('2019-04-25',or2.dt) <=30 THEN or2.deliveryPayMoney ELSE 0 END ) delivery_payable_sum_thirty_days,
                SUM(CASE WHEN datediff('2019-04-25',or2.dt) <=90 THEN or2.deliveryPayMoney ELSE 0 END ) delivery_payable_sum_three_months,
                SUM(or2.deliveryPayMoney ) delivery_payable_sum
              FROM (
                     SELECT
                       or1.order_member_id AS order_member_id,
                       IFNULL(or1.finance_delivery_money, 0) AS deliveryGmv,
                       IFNULL(or1.finance_delivery_pay_money, 0) AS deliveryPayMoney,
                       or1.dt AS dt
                     FROM
                       dw_lucky_order.t_order or1
                     WHERE
                       or1.order_status = 50
                       AND or1.order_dep_id not in (select department_id from dw_dim.t_test_shop)
                   ) or2
              GROUP BY or2.order_member_id
            ) or3 on or3.member_id = m.id
  LEFT JOIN (
              SELECT
                co2.member_id as member_id,
                SUM(CASE WHEN datediff('2019-04-25',co2.dt) <=7 THEN co2.coffeestorePayable ELSE 0 END ) coffeestore_payable_sum_seven_days,
                SUM(CASE WHEN datediff('2019-04-25',co2.dt) <=14 THEN co2.coffeestorePayable ELSE 0 END ) coffeestore_payable_sum_fourteen_days,
                SUM(CASE WHEN datediff('2019-04-25',co2.dt) <=30 THEN co2.coffeestorePayable ELSE 0 END ) coffeestore_payable_sum_thirty_days,
                SUM(CASE WHEN datediff('2019-04-25',co2.dt) <=90 THEN co2.coffeestorePayable ELSE 0 END ) coffeestore_payable_sum_three_months,
                SUM(co2.coffeestorePayable ) coffeestore_payable_sum
              FROM (
                     SELECT
                       co1.member_id AS member_id,
                       IFNULL(co1.pay_money, 0) - IFNULL(co1.already_refund_money, 0) AS coffeestorePayable,
                       from_unixtime(unix_timestamp(co1.create_time),'yyyy-MM-dd') AS dt
                     FROM
                       lucky_coupon_order.t_coupon_order co1
                   ) co2
              GROUP BY co2.member_id
            ) co3 on co3.member_id = m.id