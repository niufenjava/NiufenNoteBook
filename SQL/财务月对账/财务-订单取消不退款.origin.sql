select
	o.dt AS 日期,
	pay.pay_from,
	sum((ifnull(c.pay_money,0)+ifnull(f.delivery_pay_money,0))) 退款
    from  lucky_order.t_order_commodity c
	left join lucky_order.t_order o on o.id = c.order_id and c.dt=o.dt
	left join lucky_order.t_order_finance f on o.id = f.order_id
	left join lucky_order.t_ehr_department dept on dept.id=o.dep_id
	left join lucky_order.t_order_pay pay on o.id = pay.order_id
	left join lucky_operation.t_shop_info shop on shop.department_id=o.dep_id
	left join lucky_admin.t_base_city city on city.id = shop.city_id
	left join lucky_finance.t_commodity_tax_rate_plan ctrp on ctrp.commodity_id = c.commodity_id
	left join lucky_finance.t_tax_rate_plan trp on trp.id = ctrp.plan_id
	left join lucky_finance.t_invoice_subject isj_takeout on isj_takeout.id = trp.take_out_subject
	left join lucky_finance.t_invoice_subject isj_homeeat on isj_homeeat.id = trp.home_eat_subject
	left join lucky_finance.t_invoice_subject isj_takeaway on isj_takeaway.id = take_away_subject
	left join lucky_finance.t_shop_main_body smb on smb.shop_id=o.dep_id 
	left join lucky_finance.t_invoice_main_body imb on imb.id = smb.main_body_id
where o.dt >= '2018-08-01' and o.dt <= '2018-08-31' and o.dep_id != 4901 and o.pay_time is not null
and o.status=0 and ((c.backcoffee_status=1 and c.coffeestore_share_money > 0) or (c.pay_money > 0 and c.refund_status<>30))
group by o.dt,pay.pay_from 


-----------------------------------------------
select
	substring(to_date (o.cancel_time),0,10) AS 订单取消时间,
	case
		pay.pay_from 
		when 1 then		'支付宝' 
		when 2 then		'微信' 
		when 3 then		'银联' 
		when 0 then		'无' 
		else pay.pay_from 
	end as 支付方式,
	case c.pay_money when null then 0 else c.pay_money end as 第三方实收,
	f.delivery_pay_money as 配送费应付,
	ifnull(c.coffeestore_share_money,0) as 咖啡库券分摊金额
	
    from  lucky_order.t_order_commodity c
	left join lucky_order.t_order o on o.id = c.order_id and c.dt=o.dt
	left join lucky_order.t_order_finance f on o.id = f.order_id
	left join lucky_order.t_ehr_department dept on dept.id=o.dep_id
	left join lucky_order.t_order_pay pay on o.id = pay.order_id
	left join lucky_operation.t_shop_info shop on shop.department_id=o.dep_id
	left join lucky_admin.t_base_city city on city.id = shop.city_id
	left join lucky_finance.t_commodity_tax_rate_plan ctrp on ctrp.commodity_id = c.commodity_id
	left join lucky_finance.t_tax_rate_plan trp on trp.id = ctrp.plan_id
	left join lucky_finance.t_invoice_subject isj_takeout on isj_takeout.id = trp.take_out_subject
	left join lucky_finance.t_invoice_subject isj_homeeat on isj_homeeat.id = trp.home_eat_subject
	left join lucky_finance.t_invoice_subject isj_takeaway on isj_takeaway.id = take_away_subject
	left join lucky_finance.t_shop_main_body smb on smb.shop_id=o.dep_id 
	left join lucky_finance.t_invoice_main_body imb on imb.id = smb.main_body_id
where o.dt >= '2018-08-01' and o.dt <= '2018-08-31' and o.dep_id != 4901 and o.pay_time is not null
and 
o.status=0 and ((c.backcoffee_status=1 and c.coffeestore_share_money > 0) or (c.pay_money > 0 and c.refund_status<>30))

-----------------原始报表----------------------
select
    o.order_no as 订单编号,
	o.create_time as 订单创建时间,
	o.cancel_time as 订单取消时间,
	
	case
		pay.pay_from 
		when 1 then		'支付宝' 
		when 2 then		'微信' 
		when 3 then		'银联' 
		when 0 then		'无' 
		else pay.pay_from 
	end as 支付方式,
	case c.pay_money when null then 0 else c.pay_money end as 第三方实收,
	f.delivery_pay_money as 配送费应付,
	ifnull(c.coffeestore_share_money,0) as 咖啡库券分摊金额
	
    from  lucky_order.t_order_commodity c
	left join lucky_order.t_order o on o.id = c.order_id and c.dt=o.dt
	left join lucky_order.t_order_finance f on o.id = f.order_id
	left join lucky_order.t_ehr_department dept on dept.id=o.dep_id
	left join lucky_order.t_order_pay pay on o.id = pay.order_id
	left join lucky_operation.t_shop_info shop on shop.department_id=o.dep_id
	left join lucky_admin.t_base_city city on city.id = shop.city_id
	left join lucky_finance.t_commodity_tax_rate_plan ctrp on ctrp.commodity_id = c.commodity_id
	left join lucky_finance.t_tax_rate_plan trp on trp.id = ctrp.plan_id
	left join lucky_finance.t_invoice_subject isj_takeout on isj_takeout.id = trp.take_out_subject
	left join lucky_finance.t_invoice_subject isj_homeeat on isj_homeeat.id = trp.home_eat_subject
	left join lucky_finance.t_invoice_subject isj_takeaway on isj_takeaway.id = take_away_subject
	left join lucky_finance.t_shop_main_body smb on smb.shop_id=o.dep_id 
	left join lucky_finance.t_invoice_main_body imb on imb.id = smb.main_body_id
where o.dt >= '2018-08-01' and o.dt <= '2018-08-31' and o.dep_id != 4901 and o.pay_time is not null
and o.status=0 and ((c.backcoffee_status=1 and c.coffeestore_share_money > 0) or (c.pay_money > 0 and c.refund_status<>30))