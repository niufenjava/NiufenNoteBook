select 
concat(sum(e.tp_serial_no) ,'-',mcsr.share_amounts)
 from (select * from dw_lucky_marketing.t_mkt_coffee_coupon_send_record where dt='2018-08-23') mcsr
left JOIN lucky_coupon_order.t_mkt_coffee_coupon mc on mcsr.coupon_id=mc.id
left join lucky_coupon_order.t_coupon_order o on o.id = mcsr.send_coupon_order_id
left join lucky_coupon_order.t_coupon_order_extend e on e.order_id = mcsr.send_coupon_order_id
where 
mcsr.get_time>='2018-08-23 00:00:00' and mcsr.get_time<='2018-08-23 23:59:59'  
and mcsr.share_amounts>0
and o.pay_from = 2
group by e.tp_serial_no
union 
SELECT  
CONCAT(p.trade_no,'-',f.order_actually_pay_money)
FROM lucky_order.t_order o 
LEFT JOIN lucky_order.t_order_finance f ON o.id = f.order_id 
LEFT JOIN lucky_order.t_order_pay p ON o.id = p.order_id 
where o.DT >= '2018-08-23' AND o.DT <= '2018-08-23' 
and p.pay_from = 2 
and o.customer_type = 1 
and o.status = 50