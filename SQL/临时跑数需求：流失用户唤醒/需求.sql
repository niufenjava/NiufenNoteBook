
-- 优惠券-新人免费券
SELECT count(*)
FROM lucky_marketing.t_mkt_coupon_send_record c
WHERE c.origin_canal IN (2, 7, 9,  12, 17, 18, 19)  and status != 0  AND c.coupon_description = '券折扣0折'

-- 咖啡库券-新人免费券
SELECT count(*)
FROM lucky_coupon_order.t_mkt_coffee_coupon_send_record c
WHERE c.origin_canal IN (2, 7, 9,  12, 17, 18, 19) and status != 0

-- 近七日转赠领取咖啡库券的用户信息
%sql
SELECT zhuanzheng.member_id, zhuanzheng.couponCount,t.phone_no,1
FROM (
       SELECT
         m.receiver_mem_id AS member_id, count(*) AS couponCount
       FROM lucky_crm.t_coupon_transfer_record m
         LEFT JOIN lucky_coupon_order.t_mkt_coffee_coupon_send_record r
           ON r.coupon_record_no = m.coffee_coupon_no AND r.member_id = m.receiver_mem_id AND r.lot_number = m.lot_number
       WHERE r.status = 1
             AND m.receiver_time > date_sub('2019-03-08', 7)
             AND m.receiver_time <= '2019-03-08'
             AND r.id IS NOT NULL
       GROUP BY m.receiver_mem_id
     ) zhuanzheng
  LEFT JOIN lucky_crm.t_member t ON t.id = zhuanzheng.member_id
WHERE t.status = 1
      AND t.id IS NOT NULL


-- 近七日红包领取咖啡库券的用户信息
%sql
SELECT hongbao.member_id,hongbao.couponCount,t.phone_no,0 FROM (
    SELECT m.receiver_mem_id as member_id,count(*) as couponCount
        FROM lucky_crm.t_lucky_money_record m
     LEFT JOIN lucky_coupon_order.t_mkt_coffee_coupon_send_record r on r.coupon_record_no = m.coffee_coupon_no and r.member_id = m.receiver_mem_id and r.lot_number = m.lot_number
    WHERE  r.status = 1
    and m.receiver_time > date_sub('2019-03-08', 7)
    AND m.receiver_time <= '2019-03-08'
    AND r.id is not null
    group by m.receiver_mem_id
) hongbao
left join lucky_crm.t_member t on t.id = hongbao.member_id
where t.status =1
and t.id is not null
