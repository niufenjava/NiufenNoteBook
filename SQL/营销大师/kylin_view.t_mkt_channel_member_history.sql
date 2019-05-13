ALTER VIEW kylin_view.t_mkt_channel_member_history
AS
select mem.id,
(case when mem.inviter_code is not null and mem.inviter_type='2' then regexp_replace(mem.inviter_code,'\r\n','') 
when mem.inviter_code is not null and mem.inviter_type='1' then 'MEMBERPULLNEW' 
when mem.inviter_code is not null and mem.inviter_type='3' then 'COMPANYPULLNEW' 
when mem.id is not null and mem.inviter_type is null then 'NATURALLYNEW' 
when (mem.inviter_type is null and mem.inviter_code is null and mem.id is null and history.invitation_code is not null) then history.invitation_code else null end) as invitation_code, 
(case mem.created_time is not null when true then from_unixtime(unix_timestamp(mem.created_time),'yyyy-MM-dd') else history.report_date end) as dt, 
(case mem.created_time is not null when true then 1 else 0 end) as is_register, 
(case when mempro.first_login_time is not null then 1 when  mempro.first_login_time is null and (mempro.first_order_time is not null or mempro.first_any_pay_time is not null)  then 1 else 0 end) as is_login,
(case (mempro.first_order_time is not null or mempro.first_any_pay_time is not null) when true then 1 else 0 end) as is_order,
(case (mem.id = max_member.member_id or mem.id is null) when true then history.cost else 0 end) as cost, 
(case (mem.id = max_member.member_id or mem.id is null) when true then history.click_cnt else 0 end) as click_cnt, 
(case (mem.id = max_member.member_id or mem.id is null) when true then history.exposure_cnt else 0 end) as exposure_cnt,
history.plan_name AS plan_name
from
lucky_crm.t_member mem left join 
( select max(id) as member_id,from_unixtime(unix_timestamp(mem.created_time),'yyyy-MM-dd') dt,inviter_code from lucky_crm.t_member mem where mem.inviter_type='2' group by from_unixtime(unix_timestamp(mem.created_time),'yyyy-MM-dd'),inviter_code 
) max_member 
on from_unixtime(unix_timestamp(mem.created_time),'yyyy-MM-dd') = max_member.dt and mem.inviter_code = max_member.inviter_code 
full outer join lucky_cube.t_bi_mkt_channel_history history
on from_unixtime(unix_timestamp(mem.created_time),'yyyy-MM-dd') = history.report_date and mem.inviter_code = history.invitation_code 
left join lucky_crm.t_member_profile mempro on mempro.mem_id=mem.id