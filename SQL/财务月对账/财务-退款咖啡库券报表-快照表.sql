select
	rf.tp_serial_no,
	sum(rec.share_amounts) as �����̯���
from lucky_finance.t_refund rf 
left join lucky_coupon_order.t_coupon_order ord on rf.order_id=ord.id
left join lucky_coupon_order.t_coupon_order_detail dta on dta.order_id=ord.id and (rf.rfnd_commodity is null or find_in_set(dta.coupon_no,rf.rfnd_commodity) > 0)
left join (select * from dw_lucky_marketing.t_mkt_coffee_coupon_send_record where dt='2018-08-28') rec on rec.coupon_record_no=dta.coupon_no
left join lucky_coupon_order.t_mkt_coffee_coupon cou on cou.id=rec.coupon_id       
where rf.rfnd_object=4
and rf.status=7
and rf.pay_type_id = 2
and rec.share_amounts > 0
and rf.modified_time >= '2018-08-28 00:00:00' AND rf.modified_time <= '2018-08-28 23:59:59'
group by rf.tp_serial_no


-------------------����------------------------------
select
	substring(to_date (mcsr.created_time),0,10),
	rf.pay_type_id,
	sum(rec.share_amounts)
from lucky_finance.t_refund rf 
left join lucky_coupon_order.t_coupon_order ord on rf.order_id=ord.id
left join lucky_coupon_order.t_coupon_order_detail dta on dta.order_id=ord.id and (rf.rfnd_commodity is null or find_in_set(dta.coupon_no,rf.rfnd_commodity) > 0)
left join (select * from dw_lucky_marketing.t_mkt_coffee_coupon_send_record where dt='2018-08-31') rec on rec.coupon_record_no=dta.coupon_no
left join lucky_coupon_order.t_mkt_coffee_coupon cou on cou.id=rec.coupon_id       
where rf.rfnd_object=4
and rf.status=7
and rec.share_amounts > 0
and rf.modified_time >= '2018-08-01 00:00:00' AND rf.modified_time <= '2018-08-31 23:59:59'
group by substring(to_date (mcsr.created_time),0,10),rf.pay_type_id


-----------------ԭʼ����----------------------

select 
    substring(to_date (rf.created_time),0,10 ) AS �˿������,
    substring(to_date (rf.modified_time),0,10 ) AS �˿�ɹ�����,
		substring(to_date (ord.create_time),0,10 ) AS ��������,
	ord.order_no as �������,
	dta.coupon_no as ���ȿ�ȯ���,
	cou.coupon_name as ���ȿ�ȯ����,
	cou.coupon_value as ���ȿ�ȯ���,
	rec.share_amounts as �����̯���
from lucky_finance.t_refund rf 
left join lucky_coupon_order.t_coupon_order ord on rf.order_id=ord.id
left join lucky_coupon_order.t_coupon_order_detail dta on dta.order_id=ord.id and (rf.rfnd_commodity is null or find_in_set(dta.coupon_no,rf.rfnd_commodity) > 0)
left join (select * from dw_lucky_marketing.t_mkt_coffee_coupon_send_record where dt='$endTime$') rec on rec.coupon_record_no=dta.coupon_no
left join lucky_coupon_order.t_mkt_coffee_coupon cou on cou.id=rec.coupon_id
where rf.rfnd_object=4
and rf.status=7
and rec.share_amounts > 0
and rf.modified_time >= '$startTime$ 00:00:00' AND rf.modified_time <= '$endTime$ 23:59:59'