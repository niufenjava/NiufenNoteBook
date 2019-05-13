select 
concat(e.tp_serial_no ,'-',sum(mcsr.share_amounts))
 from (select * from dw_lucky_marketing.t_mkt_coffee_coupon_send_record where dt='2018-08-23') mcsr
left JOIN lucky_coupon_order.t_mkt_coffee_coupon mc on mcsr.coupon_id=mc.id
left join lucky_coupon_order.t_coupon_order o on o.id = mcsr.send_coupon_order_id
left join lucky_coupon_order.t_coupon_order_extend e on e.order_id = mcsr.send_coupon_order_id
where 
mcsr.get_time>='2018-08-23 00:00:00' and mcsr.get_time<='2018-08-23 23:59:59'  
and mcsr.share_amounts>0
and o.pay_from = 2
group by e.tp_serial_no

----------------------
select e.tp_serial_no,count(*) 
from lucky_coupon_order.t_coupon_order o 
left join lucky_coupon_order.t_coupon_order_extend e on o.id = e.order_id 
where o.create_time>='2018-09-01 00:00:00' and o.create_time<='2018-09-11 23:59:59' 
group by e.tp_serial_no having count(*) >1