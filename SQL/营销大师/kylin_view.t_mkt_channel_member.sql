select mkt.id,
(case mkt.invitation_code is not null when true then mkt.invitation_code else history.invitation_code end) as invitation_code,
(case mkt.dt is not null when true then mkt.dt else history.report_date end) as dt,
(case mkt.register_time is not null when true then 1 else 0 end) as is_register,
(case mkt.first_login_time is not null when true then 1 else 0 end) as is_login,
(case (mkt.order_time is not null or mkt.consume_time is not null or mkt.any_order_consume_time is not null) when true then 1 else 0 end) as is_order,
(case (mkt.id = max_member.member_id or mkt.id is null) when true then history.cost else 0 end) as cost,
(case (mkt.id = max_member.member_id or mkt.id is null) when true then history.click_cnt else 0 end) as click_cnt,
(case (mkt.id = max_member.member_id or mkt.id is null) when true then history.exposure_cnt else 0 end) as exposure_cnt
from
lucky_marketing.t_mkt_invitation_member mkt left join

( select max(id) as member_id,dt,invitation_code from lucky_marketing.t_mkt_invitation_member group by dt,invitation_code
) max_member
on mkt.dt = max_member.dt and mkt.invitation_code = max_member.invitation_code
full outer join lucky_cube.t_bi_mkt_channel_history history
on mkt.dt = history.report_date and mkt.invitation_code = history.invitation_code