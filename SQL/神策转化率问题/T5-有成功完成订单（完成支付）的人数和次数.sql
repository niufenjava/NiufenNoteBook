%sql
select count(distinct o.member_id),count(*) from lucky_order.t_order o
left join (
    select distinct om1.memId from
    -- 20170901~20190301，订单完成总次数>=1
    (
        SELECT
          distinct o.member_id as memId
        FROM
          lucky_order.t_order o
          LEFT JOIN lucky_crm.t_member t ON t.id = o.member_id
        WHERE
          o.STATUS = 50
          and t.status = 1
          and t.id is not null
          and o.dt >='2017-09-01'
          and o.dt <='2019-03-02'
    ) om1
    -- & 20190201~20190301，订单完成总次数=0
    left join
    (
        SELECT
          distinct o.member_id as memId
        FROM
          lucky_order.t_order o
          LEFT JOIN lucky_crm.t_member t ON t.id = o.member_id
        WHERE
          o.STATUS = 50
          and t.status = 1
          and t.id is not null
          and o.dt >='2019-02-01'
          and o.dt <='2019-03-02'
    ) om2 on om1.memId = om2.memId
    where om2.memId is null
) oo on oo.memId = o.member_id
where oo.memId is not null
and o.dt = '2019-03-02'
-- and o.status = 20
and o.pay_time is not null