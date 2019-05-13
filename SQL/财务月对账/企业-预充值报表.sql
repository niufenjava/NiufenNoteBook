
select 
	acc.account_name as 企业名称,
	ifnull(recc.rec_money,0) as 本期充值,
	ifnull(reff.rfnd_money,0) as 本期充值退款,
	recc.dt,
	met.method_name as 付款方式
	from lucky_ent.t_ent_payment_account acc
	left join (select rec.pay_account_code ,sum(ifnull(rec.rec_money,0)) as rec_money,sum(ifnull(refund.rfnd_money,0)) as rfnd_money,rec.rec_method_id 
		from lucky_finance.t_self_receipt rec 
		left join lucky_finance.t_refund refund on refund.recpt_no=rec.recpt_no and refund.status=7 
		where rec.rec_method_id is not null and rec.recpt_object=4 
		group by rec.pay_account_code,rec.rec_method_id) rec0 on acc.account_no=rec0.pay_account_code
	left join lucky_finance.t_receipt_method met on met.id=rec0.rec_method_id 
	left join dw_lucky_finance.t_bank_sub_account_pay sub on sub.pay_account_code=acc.account_no
	left join (select rec1.dt,rec1.pay_account_code, sum(ifnull(rec1.rec_money,0)) as rec_money,rec1.rec_method_id
		from lucky_finance.t_self_receipt rec1 
		where rec1.dt>='2018-08-01' 
		and rec1.dt<='2018-08-31'
		and rec1.recpt_object=4
		and rec1.rec_method_id is not null
		group by rec1.dt,rec1.pay_account_code, rec1.rec_method_id ) recc on recc.pay_account_code = rec0.pay_account_code and met.id =recc.rec_method_id 
	left join (select rec2.pay_account_code,sum(ifnull(refund1.rfnd_money,0)) as rfnd_money,rec2.rec_method_id 
		from lucky_finance.t_refund refund1 
		inner join lucky_finance.t_self_receipt rec2 on refund1.recpt_no=rec2.recpt_no 
		where refund1.status=7
		and refund1.created_time>='2018-08-01 00:00:00'
		and refund1.created_time<='2018-08-31 23:59:59' 
		and rec2.pay_account_code is not null
		and rec2.rec_method_id is not null
		group by rec2.pay_account_code,rec2.rec_method_id ) reff on reff.pay_account_code = rec0.pay_account_code and met.id =reff.rec_method_id 
where 
	met.method_name is not null and upper(acc.account_no) like 'RA%'