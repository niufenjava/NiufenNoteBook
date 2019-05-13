-- 20170901~20190301，订单完成总次数>=1
-- & 20190201~20190301，订单完成总次数=0
-- & 会员状态=有效
-- 上述用户，在20190301这天
-- 1）这批用户总人数   -- 11815731
-- 2）有点击过『去支付』按钮的人数
%sql
select count(distinct om1.memId) from
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
-- 2）有点击过『去支付』按钮的人数
left join
(
    select
        distinct e.device_info_family_user_id as memId
    from hbase.lucky_t_scd_app_monitor_event e where
e.dt = '2019-03-02'
and (
    e.event_event_code like '%#去结算%' or e.event_event_code = 'CoffeeCoolCheckoutActivity#commit#OnClick'
)
) m1 on m1.memId = om1.memId
where om2.memId is null
and m1.memId is not null