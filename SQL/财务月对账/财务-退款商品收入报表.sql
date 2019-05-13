SELECT 
	CONCAT(ref.tp_serial_no,'-',c.refund_money)
	FROM lucky_order.t_order_commodity c 
	LEFT JOIN lucky_order.t_order o ON c.dt=o.dt AND c.order_id=o.id 
	LEFT JOIN lucky_order.t_order_pay pay ON pay.dt=c.dt AND pay.order_id=o.id 
	LEFT JOIN lucky_finance.t_refund ref ON ref.order_no=o.order_no and (ref.rfnd_range=1 or FIND_IN_SET(c.id,ref.rfnd_commodity) > 0) 
	WHERE ref.modified_time>='2018-08-30 00:00:00' AND ref.modified_time<='2018-08-30 23:59:59'  
	and o.customer_type = 1 and  ref.STATUS = 7 AND ref.rfnd_object = 1 
	and pay.pay_from = 2
	and c.refund_status in (30,40)
	and o.STATUS = 50

---------------汇总报表----------------------------
SELECT 
	pay.pay_from,
	sum(c.refund_money)
	FROM lucky_order.t_order_commodity c 
	LEFT JOIN lucky_order.t_order o ON c.dt=o.dt AND c.order_id=o.id 
	LEFT JOIN lucky_order.t_order_pay pay ON pay.dt=c.dt AND pay.order_id=o.id 
	LEFT JOIN lucky_finance.t_refund ref ON ref.order_no=o.order_no and (ref.rfnd_range=1 or FIND_IN_SET(c.id,ref.rfnd_commodity) > 0) 
	WHERE ref.modified_time>='2018-08-30 00:00:00' AND ref.modified_time<='2018-08-30 23:59:59'  
	and o.customer_type = 1 and  ref.STATUS = 7 AND ref.rfnd_object = 1 
	and c.refund_status in (30,40)
	and o.STATUS = 50
	group by pay.pay_from

-----------------原始报表----------------------
SELECT 
substring(to_date (ref.modified_time),0,10) AS 退款日期,
c.dt AS 订单日期,
city.area_code AS 城市编号,
city.NAME AS 门店城市,
shop.sequence_number AS 门店序号,
shop.shop_no AS 门店编号,
shop.shop_name AS 门店名称,
mb.company_name AS 开票主体,
CASE shop.shop_level WHEN 5 THEN '外卖厨房'  WHEN 6 THEN '快取店'  WHEN 7 THEN '悠享店'  WHEN 8 THEN '旗舰店'  WHEN 9 THEN '展会店'  ELSE '--'   END AS 门店级别,
CASE o.eat_type WHEN 1 THEN '堂食' ELSE '打包' END AS 就餐形式,
CASE pay.pay_from WHEN 1 THEN '支付宝' WHEN 2 THEN '微信' WHEN 3 THEN '银联' ELSE '未知' END AS 收款方式,
c.one_category_name AS 一级分类,
if(c.one_category_name='饮品' and (c.commodity_name like '%巴黎水%' or c.commodity_name like '%依云%' or lower(c.commodity_name) like '%nfc%'), '外购饮品','其他') as 商品分类,
IF (mb.company_type IS NULL,0,CASE o.eat_type WHEN 1 THEN IF (mb.company_type=2,sub2.small_tax_rate,sub2.gen_tax_rate) ELSE IF (mb.company_type=2,sub1.small_tax_rate,sub1.gen_tax_rate) END) AS 税率,
CASE o.STATUS WHEN 10 THEN '已新建' WHEN 20 THEN '已付款' WHEN 50 THEN '已完成' ELSE '已取消' END AS 订单状态,
sum(ifnull(c.refund_money,0)) AS 退款金额,
CASE c.refund_status WHEN 10 THEN '可退款' WHEN 20 THEN '已新建' WHEN 30 THEN '已退款' WHEN 40 THEN '已退款' ELSE '不可退款' END AS 退款状态,
count(c.id) AS 商品数量 
FROM lucky_order.t_order_commodity c 
LEFT JOIN lucky_order.t_order o ON c.dt=o.dt AND c.order_id=o.id 
LEFT JOIN lucky_operation.t_shop_info shop ON shop.department_id=o.dep_id 
LEFT JOIN lucky_admin.t_base_city city ON city.id=shop.city_id 
LEFT JOIN lucky_finance.t_shop_main_body mBody ON mBody.shop_id=shop.department_id 
LEFT JOIN lucky_finance.t_invoice_main_body mb ON mBody.main_body_id=mb.id 
LEFT JOIN lucky_order.t_order_pay pay ON pay.dt=c.dt AND pay.order_id=o.id 
LEFT JOIN lucky_finance.t_refund ref ON ref.order_no=o.order_no and (ref.rfnd_range=1 or FIND_IN_SET(c.id,ref.rfnd_commodity) > 0) 
LEFT JOIN lucky_finance.t_commodity_tax_rate_plan commpl ON commpl.commodity_id=c.commodity_id 
LEFT JOIN lucky_finance.t_tax_rate_plan re ON commpl.plan_id=re.id 
LEFT JOIN lucky_finance.t_invoice_subject sub1 ON re.take_out_subject=sub1.id 
LEFT JOIN lucky_finance.t_invoice_subject sub2 ON re.home_eat_subject=sub2.id 
WHERE 
ref.modified_time>='$startTime$ 00:00:00' AND ref.modified_time<='$endTime$ 23:59:59'  
and o.customer_type = 1 
and  ref.STATUS = 7 
AND ref.rfnd_object = 1 
GROUP BY substring(to_date (ref.modified_time),0,10),c.dt,city.area_code,city.NAME,shop.sequence_number,shop.shop_no,shop.shop_name,mb.company_name,if(c.one_category_name='饮品' and (c.commodity_name like '%巴黎水%' or c.commodity_name like '%依云%' or lower(c.commodity_name) like '%nfc%'), '外购饮品','其他') ,CASE
		shop.shop_level
		WHEN 5 THEN
		'外卖厨房' 
		WHEN 6 THEN
		'快取店' 
		WHEN 7 THEN
		'悠享店' 
		WHEN 8 THEN
		'旗舰店' 
		WHEN 9 THEN
		'展会店' 
		ELSE '--'  
	END, CASE o.eat_type WHEN 1 THEN '堂食' ELSE '打包' END,CASE pay.pay_from WHEN 1 THEN '支付宝' WHEN 2 THEN '微信' WHEN 3 THEN '银联' ELSE '未知' END,c.one_category_name,IF (mb.company_type IS NULL,0,CASE o.eat_type WHEN 1 THEN IF (mb.company_type=2,sub2.small_tax_rate,sub2.gen_tax_rate) ELSE IF (mb.company_type=2,sub1.small_tax_rate,sub1.gen_tax_rate) END),CASE o.STATUS WHEN 10 THEN '已新建' WHEN 20 THEN '已付款' WHEN 50 THEN '已完成' ELSE '已取消' END,CASE c.refund_status WHEN 10 THEN '可退款' WHEN 20 THEN '已新建' WHEN 30 THEN '已退款' WHEN 40 THEN '已退款' ELSE '不可退款' END ORDER BY substring(to_date (ref.modified_time),0,10),city.NAME,shop.shop_name