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

------------------����--------------------
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


-----------------ԭʼ����----------------------
select mcsr.get_time as ��������,substring(
		to_date (mcsr.get_time),
		0,
		10 
	) AS ��������,
substring(if(isnull(mcsr.send_coupon_order_no),'',mcsr.send_coupon_order_no),0,10) as �������,
mcsr.coupon_record_no as ���ȿ�ȯ���,
mc.coupon_name as ���ȿ�ȯ����,
mc.coupon_value as ���,
if(isnull(mcsr.share_amounts),'',mcsr.share_amounts) as ��̯���,
CASE mcsr.origin_canal 
	    WHEN 1 THEN '��Ա����'
        WHEN 2 THEN '�»�Աע����ȡ'
		WHEN 3 THEN '�������ѷ�ȯ'
		WHEN 4 THEN '�˹���ȯ--�»�Ա'
		WHEN 5 THEN '�˹���ȯ--�ϻ�Ա'
		WHEN 6 THEN '�ϻ�Աɨ����ȡ'
		WHEN 7 THEN '��˾Ӫ�����·���'
		WHEN 8 THEN '��Ա���½���'
        WHEN 9 THEN '�ܻ�Ա��������ע����ȡ'
		WHEN 10 THEN '��˾Ӫ������-�»�Ա' 
		WHEN 11 THEN '��˾Ӫ������-�ϻ�Ա'
		WHEN 12 THEN '��ת������ע����ȡ'
		WHEN 13 THEN '�����ⲹ������'
	    ELSE mcsr.origin_canal 
	END AS ���ȿ�ȯ��Դ,
mcsr.expire_time as ������,
CASE   o.pay_from 
		WHEN 1 THEN
		'֧����' 
		WHEN 2 THEN
		'΢��' 
		WHEN 3 THEN
		'����' 
		WHEN 0 THEN
		'��' ELSE o.pay_from 
	END AS �������ʽ	,
e.tp_serial_no as ��˾����ƽ̨��ˮ��
 from (select * from dw_lucky_marketing.t_mkt_coffee_coupon_send_record where dt='$endTime$') mcsr
left JOIN lucky_coupon_order.t_mkt_coffee_coupon mc on mcsr.coupon_id=mc.id
left join lucky_coupon_order.t_coupon_order o on o.id = mcsr.send_coupon_order_id
left join lucky_coupon_order.t_coupon_order_extend e on e.order_id = mcsr.send_coupon_order_id
where mcsr.get_time>='$startTime$ 00:00:00' and mcsr.get_time<='$endTime$ 23:59:59'  and mcsr.share_amounts>0