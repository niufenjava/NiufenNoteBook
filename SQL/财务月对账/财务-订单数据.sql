---------------------------------------------------------------------

SELECT  
CONCAT(p.trade_no,'-',f.order_actually_pay_money) as ֧����ˮ�� 
FROM lucky_order.t_order o 
LEFT JOIN lucky_order.t_order_finance f ON o.id = f.order_id 
LEFT JOIN lucky_order.t_order_pay p ON o.id = p.order_id 
where o.DT >= '2018-08-30' AND o.DT <= '2018-08-30' 
and p.pay_from = 2 
and o.customer_type = 1 
and o.status = 50

---------------------------����---------------------------------------
SELECT  
 o.DT,
 p.pay_from,
sum(f.order_actually_pay_money)
FROM lucky_order.t_order o 
LEFT JOIN lucky_order.t_order_finance f ON o.id = f.order_id 
LEFT JOIN lucky_order.t_order_pay p ON o.id = p.order_id 
where o.DT >= '2018-08-01' AND o.DT <= '2018-08-31' 
and o.customer_type = 1 
and o.status = 50
group by o.DT,p.pay_from

-----------------ԭʼ����----------------------
SELECT 
CONCAT(o.order_no,' ') as ������,
e.shop_name as �ŵ�����, 
o.pay_time as ֧��ʱ��, 
o.finish_time as ���ʱ��, 
CASE f.order_actually_pay_money WHEN NULL THEN 0 ELSE f.order_actually_pay_money END as ʵ�ս��, 
CONCAT(p.trade_no,' ') as ֧����ˮ�� 
FROM lucky_order.t_order o 
LEFT JOIN lucky_order.t_order_finance f ON o.id = f.order_id 
LEFT JOIN lucky_order.t_order_extend e ON o.id = e.order_id 
LEFT JOIN lucky_order.t_order_pay p ON o.id = p.order_id 
where o.DT >= '2018-08-30' AND o.DT <= '2018-08-30' 
and p.pay_from = 2 
and o.customer_type = 1 
and o.status = 50

---------------------------
SELECT 
o.order_no as ������,
o.member_phone as �ͻ��绰, 
CASE o.type WHEN 2 THEN '��ȡ' WHEN 1 THEN '����' ELSE o.type END as ��������,
city.name �ŵ����, 
tsi.shop_no as �ŵ����,
 tsi.sequence_number  AS  �ŵ����,
e.shop_name as �ŵ�����, 
CASE f.order_actually_pay_money WHEN NULL THEN 0 ELSE f.order_actually_pay_money END as ʵ�ս��,
CASE f.delivery_pay_money WHEN NULL THEN 0 ELSE f.delivery_pay_money END as ���ͷ�ʵ�ս��,
(ifnull(f.total_origin_money,0)+ifnull(f.total_addition_money,0)+ifnull(f.delivery_money,0)) as �������,
CASE p.pay_from WHEN 1 THEN '֧����' WHEN 2 THEN '΢��'  WHEN 3 THEN '����' WHEN 0 THEN '��'  ELSE p.pay_from END as ֧����ʽ, 
CASE o.eat_type WHEN 1 THEN '��ʳ' WHEN 2 THEN '���' ELSE o.eat_type END as �Ͳ���ʽ, 
CASE o.customer_type WHEN 1 THEN '����' WHEN 2 THEN '��ҵ' ELSE o.customer_type END as ҵ������, 
CASE o.status
 WHEN 10	THEN '���½�'
 WHEN 20	THEN '�Ѹ���'
 WHEN 50	THEN '�����'
 WHEN 0 THEN '��ȡ��'
 ELSE o.status END as ����״̬,
CASE o.express_status
 WHEN 10	THEN '��������'
 WHEN 20	THEN '���ӵ�'
 WHEN 30	THEN '�ѽӵ�'
 WHEN 40	THEN '��ȡ��'
 WHEN 50	THEN '�ѳ���'
 WHEN 60 THEN '�ѵ���'
 WHEN 70 THEN '���������'
 WHEN 80 THEN '�ɵ�ʧ��'
 WHEN 90 THEN '�Ѹ���'
 WHEN 100 THEN '��ȡ��' 
 WHEN 110 THEN '�ͻ���ȡ' 
 WHEN 120 THEN 'ϵͳ����' 
 ELSE o.express_status END as ���͵�״̬,
d.accept_time  as ����ʦ�ӵ�ʱ��,
ost.expect_time  as Ԥ���������ʱ��,
d.finish_time  as ʵ���������ʱ��,
o.pay_time  as ֧��ʱ��,
o.finish_time as ���ʱ��,
CASE co.level WHEN 1 THEN '����' WHEN 0 THEN '������' ELSE  co.level END  as ���������, 
em.name as ������Ա,
ex.expect_time as Ԥ������ʱ��,
ex.arrive_time as ʵ������ʱ��,
re.reason as ȡ��ԭ��,
co.label as ������ԭ��,
d.product_user_name as ����ʦ����,
p.trade_no as ֧����ˮ��
FROM lucky_order.t_order o 
LEFT JOIN lucky_order.t_order_finance f ON o.id = f.order_id 
LEFT JOIN lucky_order.t_order_extend e ON o.id = e.order_id
LEFT JOIN lucky_order.t_order_pay p ON o.id = p.order_id
LEFT JOIN lucky_order.t_order_coffee_product d ON o.id = d.order_id
LEFT JOIN lucky_order.t_order_comment co ON o.id = co.order_id
LEFT JOIN lucky_order.t_order_express ex ON o.id = ex.order_id
LEFT JOIN lucky_order.t_ehr_employee em ON ex.express_user = em.id
LEFT JOIN lucky_order.t_order_cancel ca ON o.id=ca.order_id
LEFT JOIN lucky_order.t_order_cancel_reason re ON ca.cancel_reason_id = re.id
LEFT JOIN lucky_order.t_order_self_take ost ON o.id = ost.order_id
LEFT JOIN lucky_operation.t_shop_info tsi ON tsi.department_id = o.dep_id
LEFT JOIN lucky_admin.t_base_city city ON city.id = o.city_id
where o.DT >= '$startTime$' AND o.DT <= '$endTime$' 