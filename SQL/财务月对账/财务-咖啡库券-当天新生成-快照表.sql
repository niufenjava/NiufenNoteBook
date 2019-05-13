select 
e.tp_serial_no ,
sum(mcsr.share_amounts)
 from (select * from dw_lucky_marketing.t_mkt_coffee_coupon_send_record where dt='2018-08-23') mcsr
left JOIN lucky_coupon_order.t_mkt_coffee_coupon mc on mcsr.coupon_id=mc.id
left join lucky_coupon_order.t_coupon_order o on o.id = mcsr.send_coupon_order_id
left join lucky_coupon_order.t_coupon_order_extend e on e.order_id = mcsr.send_coupon_order_id
where 
mcsr.get_time>='2018-08-23 00:00:00' and mcsr.get_time<='2018-08-23 23:59:59'  
and mcsr.share_amounts>0
and o.pay_from = 2
group by e.tp_serial_no

------------------汇总--------------------
select 
substring(to_date (mcsr.get_time),0,10),
o.pay_from,
sum(mcsr.share_amounts)
 from (select * from dw_lucky_marketing.t_mkt_coffee_coupon_send_record where dt='2018-08-31') mcsr
left JOIN lucky_coupon_order.t_mkt_coffee_coupon mc on mcsr.coupon_id=mc.id
left join lucky_coupon_order.t_coupon_order o on o.id = mcsr.send_coupon_order_id
left join lucky_coupon_order.t_coupon_order_extend e on e.order_id = mcsr.send_coupon_order_id
where 
mcsr.get_time>='2018-08-01 00:00:00' and mcsr.get_time<='2018-08-31 23:59:59'  
and mcsr.share_amounts>0
group by substring(to_date (mcsr.get_time),0,10),o.pay_from


-----------------原始报表----------------------
select mcsr.get_time as 订单日期,substring(
		to_date (mcsr.get_time),
		0,
		10 
	) AS 生成日期,
substring(if(isnull(mcsr.send_coupon_order_no),'',mcsr.send_coupon_order_no),0,10) as 订单编号,
mcsr.coupon_record_no as 咖啡库券编号,
mc.coupon_name as 咖啡库券名称,
mc.coupon_value as 面额,
if(isnull(mcsr.share_amounts),'',mcsr.share_amounts) as 分摊金额,
CASE mcsr.origin_canal 
	    WHEN 1 THEN '会员购买'
        WHEN 2 THEN '新会员注册领取'
		WHEN 3 THEN '订单消费返券'
		WHEN 4 THEN '人工发券--新会员'
		WHEN 5 THEN '人工发券--老会员'
		WHEN 6 THEN '老会员扫码领取'
		WHEN 7 THEN '公司营销拉新发放'
		WHEN 8 THEN '会员拉新奖励'
        WHEN 9 THEN '受会员邀请新人注册领取'
		WHEN 10 THEN '公司营销发放-新会员' 
		WHEN 11 THEN '公司营销发放-老会员'
		WHEN 12 THEN '被转赠新人注册领取'
		WHEN 13 THEN '慢必赔补偿发放'
	    ELSE mcsr.origin_canal 
	END AS 咖啡库券来源,
mcsr.expire_time as 到期日,
CASE   o.pay_from 
		WHEN 1 THEN
		'支付宝' 
		WHEN 2 THEN
		'微信' 
		WHEN 3 THEN
		'银联' 
		WHEN 0 THEN
		'无' ELSE o.pay_from 
	END AS 订单付款方式	,
e.tp_serial_no as 公司交易平台流水号
 from (select * from dw_lucky_marketing.t_mkt_coffee_coupon_send_record where dt='$endTime$') mcsr
left JOIN lucky_coupon_order.t_mkt_coffee_coupon mc on mcsr.coupon_id=mc.id
left join lucky_coupon_order.t_coupon_order o on o.id = mcsr.send_coupon_order_id
left join lucky_coupon_order.t_coupon_order_extend e on e.order_id = mcsr.send_coupon_order_id
where mcsr.get_time>='$startTime$ 00:00:00' and mcsr.get_time<='$endTime$ 23:59:59'  and mcsr.share_amounts>0