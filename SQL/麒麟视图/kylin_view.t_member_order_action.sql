select t.*,
  (case v_interval_day when 0 then 1 else 0 end) as v_day_0,
  (case v_interval_day when 1 then 1 else 0 end) as v_day_1,
  (case v_interval_day when 2 then 1 else 0 end) as v_day_2,
  (case v_interval_day when 3 then 1 else 0 end) as v_day_3,
  (case v_interval_day when 4 then 1 else 0 end) as v_day_4,
  (case v_interval_day when 5 then 1 else 0 end) as v_day_5,
  (case v_interval_day when 6 then 1 else 0 end) as v_day_6,
  (case v_interval_day when 7 then 1 else 0 end) as v_day_7,
  (case v_interval_week when 0 then 1 else 0 end) as v_week_0,
  (case v_interval_week when 1 then 1 else 0 end) as v_week_1,
  (case v_interval_week when 2 then 1 else 0 end) as v_week_2,
  (case v_interval_week when 3 then 1 else 0 end) as v_week_3,
  (case v_interval_week when 4 then 1 else 0 end) as v_week_4,
  (case v_interval_month when 0 then 1 else 0 end) as v_month_0,
  (case v_interval_month when 1 then 1 else 0 end) as v_month_1,
  (case v_interval_month when 2 then 1 else 0 end) as v_month_2,
  (case v_interval_month when 3 then 1 else 0 end) as v_month_3,
  (case v_interval_month when 4 then 1 else 0 end) as v_month_4,
  (case v_interval_month when 5 then 1 else 0 end) as v_month_5,
  (case v_interval_month when 6 then 1 else 0 end) as v_month_6,
  (case v_interval_month > 6 when true then 1 else 0 end) as v_month_6_more,
  (case v_interval_year when 0 then 1 else 0 end) as v_year_0,
  (case v_interval_year when 1 then 1 else 0 end) as v_year_1,
  (case v_interval_year when 2 then 1 else 0 end) as v_year_2,
  (case v_interval_year when 3 then 1 else 0 end) as v_year_3,
  (case v_interval_year > 3 when true then 1 else 0 end) as v_year_3_more
from
  (
    select o.member_id,o.dt,substring(cast(p.first_order_time as string),0,10) as v_first_order_date,
                            datediff(o.dt,substring(cast(p.first_order_time as string),0,10)) as v_interval_day,
                            floor((datediff(o.dt,substring(cast(p.first_order_time as string),0,10)))/7) as v_interval_week,
                            floor((datediff(o.dt,substring(cast(p.first_order_time as string),0,10)))/30) as v_interval_month,
                            floor((datediff(o.dt,substring(cast(p.first_order_time as string),0,10)))/365) as v_interval_year
    from (select distinct member_id,dt from lucky_order.t_order where status = 50) o
      inner join lucky_crm.t_member_profile p on o.member_id = p.mem_id
    where p.first_order_time is not null
  ) t