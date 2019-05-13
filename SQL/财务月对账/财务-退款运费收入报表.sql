SELECT
	substring(to_date (ref.modified_time),0,10) AS �˿�����,
	concat(ref.tp_serial_no,'NO'),
	fin.delivery_refund_money AS ���ͷ��˿���
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

-----------------���ܱ���----------------------------
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

-----------------ԭʼ����----------------------
SELECT
	substring(
		to_date (ref.modified_time),
		0,
		10
	) AS �˿�����,
	o.dt AS ��������,
	city.area_code AS ���б��,
	city. NAME AS �ŵ����,
	shop.sequence_number AS �ŵ����,
	shop.shop_no AS �ŵ���,
	shop.shop_name AS �ŵ�����,
	mb.company_name AS ��Ʊ����,
	CASE shop.shop_level
WHEN 5 THEN
	'��������'
WHEN 6 THEN
	'��ȡ��'
WHEN 7 THEN
	'�����'
WHEN 8 THEN
	'�콢��'
WHEN 9 THEN
	'չ���'
ELSE
	'--'
END AS �ŵ꼶��,
 sum(
	IFNULL(
		fin.delivery_refund_money,
		0
	)
) AS ���ͷ��˿���,
 CASE o. STATUS
WHEN 10 THEN
	'���½�'
WHEN 20 THEN
	'�Ѹ���'
WHEN 50 THEN
	'�����'
ELSE
	'��ȡ��'
END AS ����״̬,
 CASE o.refund_status
WHEN 10 THEN
	'���˿�'
WHEN 20 THEN
	'���½�'
WHEN 30 THEN
	'���˿�'
WHEN 40 THEN
	'���˿�'
ELSE
	'�����˿�'
END AS �˿�״̬,

IF (
	mb.company_type = 2,
	rate.take_out_small,
	rate.take_out_gen
) AS ˰��
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
	'��������'
WHEN 6 THEN
	'��ȡ��'
WHEN 7 THEN
	'�����'
WHEN 8 THEN
	'�콢��'
WHEN 9 THEN
	'չ���'
ELSE
	'--'
END,
 CASE o. STATUS
WHEN 10 THEN
	'���½�'
WHEN 20 THEN
	'�Ѹ���'
WHEN 50 THEN
	'�����'
ELSE
	'��ȡ��'
END,
 CASE o.refund_status
WHEN 10 THEN
	'���˿�'
WHEN 20 THEN
	'���½�'
WHEN 30 THEN
	'���˿�'
WHEN 40 THEN
	'���˿�'
ELSE
	'�����˿�'
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