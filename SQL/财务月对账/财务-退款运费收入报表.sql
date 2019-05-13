SELECT
	substring(to_date (ref.modified_time),0,10) AS 退款日期,
	concat(ref.tp_serial_no,'NO'),
	fin.delivery_refund_money AS 配送费退款金额
FROM
	lucky_order.t_order o
LEFT JOIN lucky_order.t_order_finance fin ON fin.order_id = o.id
AND o.dt = fin.dt
LEFT JOIN lucky_finance.t_refund ref ON ref.order_no = o.order_no
WHERE
	ref.modified_time >= '2018-08-01 00:00:00'
AND ref.modified_time <= '2018-08-31 23:59:59'
AND o.customer_type = 1
AND ref. STATUS = 7
AND ref.rfnd_object = 1
AND o. STATUS = 50
AND o.refund_status IN (30,40)
AND ref.pay_type_id = 2
AND fin.delivery_refund_money != 0

-----------------汇总报表----------------------------
SELECT
	substring(to_date (ref.modified_time),0,10),
	ref.pay_type_id,
	SUM(fin.delivery_refund_money)
FROM
	lucky_order.t_order o
LEFT JOIN lucky_order.t_order_finance fin ON fin.order_id = o.id
AND o.dt = fin.dt
LEFT JOIN lucky_finance.t_refund ref ON ref.order_no = o.order_no
WHERE
	ref.modified_time >= '2018-08-01 00:00:00'
AND ref.modified_time <= '2018-08-31 23:59:59'
AND o.customer_type = 1
AND ref. STATUS = 7
AND ref.rfnd_object = 1
AND o. STATUS = 50
AND o.refund_status IN (30,40)
AND fin.delivery_refund_money != 0
group by substring(to_date (ref.modified_time),0,10),ref.pay_type_id

-----------------原始报表----------------------
SELECT
	substring(
		to_date (ref.modified_time),
		0,
		10
	) AS 退款日期,
	o.dt AS 订单日期,
	city.area_code AS 城市编号,
	city. NAME AS 门店城市,
	shop.sequence_number AS 门店序号,
	shop.shop_no AS 门店编号,
	shop.shop_name AS 门店名称,
	mb.company_name AS 开票主体,
	CASE shop.shop_level
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
ELSE
	'--'
END AS 门店级别,
 sum(
	IFNULL(
		fin.delivery_refund_money,
		0
	)
) AS 配送费退款金额,
 CASE o. STATUS
WHEN 10 THEN
	'已新建'
WHEN 20 THEN
	'已付款'
WHEN 50 THEN
	'已完成'
ELSE
	'已取消'
END AS 订单状态,
 CASE o.refund_status
WHEN 10 THEN
	'可退款'
WHEN 20 THEN
	'已新建'
WHEN 30 THEN
	'已退款'
WHEN 40 THEN
	'已退款'
ELSE
	'不可退款'
END AS 退款状态,

IF (
	mb.company_type = 2,
	rate.take_out_small,
	rate.take_out_gen
) AS 税率
FROM
	lucky_order.t_order o
LEFT JOIN lucky_operation.t_shop_info shop ON shop.department_id = o.dep_id
LEFT JOIN lucky_admin.t_base_city city ON city.id = shop.city_id
LEFT JOIN lucky_finance.t_shop_main_body mBody ON mBody.shop_id = shop.department_id
LEFT JOIN lucky_finance.t_invoice_main_body mb ON mBody.main_body_id = mb.id
LEFT JOIN lucky_order.t_order_finance fin ON fin.order_id = o.id
AND o.dt = fin.dt
LEFT JOIN lucky_finance.t_refund ref ON ref.order_no = o.order_no
LEFT JOIN (
	SELECT DISTINCT
		sub1.gen_tax_rate take_out_gen,
		sub1.small_tax_rate take_out_small
	FROM
		lucky_finance.t_tax_rate_plan re
	LEFT JOIN lucky_finance.t_invoice_subject sub1 ON re.take_out_subject = sub1.id
	WHERE
		re.plan_type = 2
) rate
WHERE
	ref.modified_time >= '$startTime$ 00:00:00'
AND ref.modified_time <= '$endTime$ 23:59:59'
AND o.customer_type = 1
AND ref. STATUS = 7
AND ref.rfnd_object = 1
GROUP BY
	substring(
		to_date (ref.modified_time),
		0,
		10
	),
	o.dt,
	city.area_code,
	city. NAME,
	shop.sequence_number,
	shop.shop_no,
	shop.shop_name,
	mb.company_name,
	CASE shop.shop_level
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
ELSE
	'--'
END,
 CASE o. STATUS
WHEN 10 THEN
	'已新建'
WHEN 20 THEN
	'已付款'
WHEN 50 THEN
	'已完成'
ELSE
	'已取消'
END,
 CASE o.refund_status
WHEN 10 THEN
	'可退款'
WHEN 20 THEN
	'已新建'
WHEN 30 THEN
	'已退款'
WHEN 40 THEN
	'已退款'
ELSE
	'不可退款'
END,

IF (
	mb.company_type = 2,
	rate.take_out_small,
	rate.take_out_gen
)
ORDER BY
	substring(
		to_date (ref.modified_time),
		0,
		10
	),
	city. NAME,
	shop.shop_name