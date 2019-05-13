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

---------------���ܱ���----------------------------
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

-----------------ԭʼ����----------------------
SELECT 
substring(to_date (ref.modified_time),0,10) AS �˿�����,
c.dt AS ��������,
city.area_code AS ���б��,
city.NAME AS �ŵ����,
shop.sequence_number AS �ŵ����,
shop.shop_no AS �ŵ���,
shop.shop_name AS �ŵ�����,
mb.company_name AS ��Ʊ����,
CASE shop.shop_level WHEN 5 THEN '��������'  WHEN 6 THEN '��ȡ��'  WHEN 7 THEN '�����'  WHEN 8 THEN '�콢��'  WHEN 9 THEN 'չ���'  ELSE '--'   END AS �ŵ꼶��,
CASE o.eat_type WHEN 1 THEN '��ʳ' ELSE '���' END AS �Ͳ���ʽ,
CASE pay.pay_from WHEN 1 THEN '֧����' WHEN 2 THEN '΢��' WHEN 3 THEN '����' ELSE 'δ֪' END AS �տʽ,
c.one_category_name AS һ������,
if(c.one_category_name='��Ʒ' and (c.commodity_name like '%����ˮ%' or c.commodity_name like '%����%' or lower(c.commodity_name) like '%nfc%'), '�⹺��Ʒ','����') as ��Ʒ����,
IF (mb.company_type IS NULL,0,CASE o.eat_type WHEN 1 THEN IF (mb.company_type=2,sub2.small_tax_rate,sub2.gen_tax_rate) ELSE IF (mb.company_type=2,sub1.small_tax_rate,sub1.gen_tax_rate) END) AS ˰��,
CASE o.STATUS WHEN 10 THEN '���½�' WHEN 20 THEN '�Ѹ���' WHEN 50 THEN '�����' ELSE '��ȡ��' END AS ����״̬,
sum(ifnull(c.refund_money,0)) AS �˿���,
CASE c.refund_status WHEN 10 THEN '���˿�' WHEN 20 THEN '���½�' WHEN 30 THEN '���˿�' WHEN 40 THEN '���˿�' ELSE '�����˿�' END AS �˿�״̬,
count(c.id) AS ��Ʒ���� 
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
GROUP BY substring(to_date (ref.modified_time),0,10),c.dt,city.area_code,city.NAME,shop.sequence_number,shop.shop_no,shop.shop_name,mb.company_name,if(c.one_category_name='��Ʒ' and (c.commodity_name like '%����ˮ%' or c.commodity_name like '%����%' or lower(c.commodity_name) like '%nfc%'), '�⹺��Ʒ','����') ,CASE
		shop.shop_level
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
		ELSE '--'  
	END, CASE o.eat_type WHEN 1 THEN '��ʳ' ELSE '���' END,CASE pay.pay_from WHEN 1 THEN '֧����' WHEN 2 THEN '΢��' WHEN 3 THEN '����' ELSE 'δ֪' END,c.one_category_name,IF (mb.company_type IS NULL,0,CASE o.eat_type WHEN 1 THEN IF (mb.company_type=2,sub2.small_tax_rate,sub2.gen_tax_rate) ELSE IF (mb.company_type=2,sub1.small_tax_rate,sub1.gen_tax_rate) END),CASE o.STATUS WHEN 10 THEN '���½�' WHEN 20 THEN '�Ѹ���' WHEN 50 THEN '�����' ELSE '��ȡ��' END,CASE c.refund_status WHEN 10 THEN '���˿�' WHEN 20 THEN '���½�' WHEN 30 THEN '���˿�' WHEN 40 THEN '���˿�' ELSE '�����˿�' END ORDER BY substring(to_date (ref.modified_time),0,10),city.NAME,shop.shop_name